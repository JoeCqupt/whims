package io.whim.raft.server;

import io.whim.raft.common.RaftConstants;
import io.whim.raft.exception.RaftException;
import io.whim.raft.protocol.RaftProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class LeaderElection {
    private static Logger LOG = LoggerFactory.getLogger(LeaderElection.class);

    enum Result {ELECTED, REJECTED, TIMEOUT, NEWTERM}


    private final RaftServer candidate;
    private final long electionTerm;

    public LeaderElection(RaftServer candidate, long electionTerm) {
        this.candidate = candidate;
        this.electionTerm = electionTerm;
    }

    public Result begin() throws RaftException, InterruptedException {
        final int size = candidate.getEnsemable().size();
        final ExecutorService executor = Executors.newFixedThreadPool(size);
        try {
            return election(executor);
        } finally {
            executor.shutdownNow();
        }
    }

    private Result election(ExecutorService executor)
            throws InterruptedException, RaftException {

        final long startTime = System.currentTimeMillis();
        final long timeout = startTime + RaftConstants.getRandomElectionWaitTime();

        final ExecutorCompletionService<RaftProtocol.Response> completion = new ExecutorCompletionService<>(executor);

        final int submitted = submitRequestVoteTasks(completion);


        List<RaftProtocol.Response> responses = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();
        int granted = 1; // 获取投票数目


        while (true) {
            final long waitTime = timeout - System.currentTimeMillis();
            if (waitTime <= 0) {
                LOG.info("Election timeout: " + string(responses, exceptions));
                return Result.TIMEOUT;
            }

            try {
                RaftProtocol.Response r = completion.poll(waitTime, TimeUnit.MILLISECONDS).get();
                if (r.term() > electionTerm) {
                    return Result.NEWTERM;
                }
                responses.add(r);
                if (r.success()) {
                    granted++;
                    if (granted > candidate.getEnsemable().size() / 2) {
                        LOG.info("Election passed: " + string(responses, exceptions));
                        return Result.ELECTED;
                    }
                }
            } catch (ExecutionException e) {
                LOG.warn("", e);
                exceptions.add(e);
            }

            if (responses.size() + exceptions.size() == submitted) {
                // received all the responses
                LOG.info("Election rejected: " + string(responses, exceptions));
                return Result.REJECTED;
            }
        }
    }

    private int submitRequestVoteTasks(ExecutorCompletionService<RaftProtocol.Response> completion)
            throws InterruptedException, RaftException {
        int submitted = 0;
        Collection<RaftServer> otherServers = candidate.getEnsemable().getOtherServers();
        for (RaftServer s : otherServers) {
            submitted++;
            completion.submit(new Callable<RaftProtocol.Response>() {
                @Override
                public RaftProtocol.Response call() throws Exception {
                    return candidate.sendRequestVote(electionTerm, s);
                }
            });
        }
        return submitted;
    }

    static String string(List<RaftProtocol.Response> responses, List<Exception> exceptions) {
        return "received " + responses.size() + " response(s) and "
                + exceptions.size() + " exception(s); "
                + responses + "; " + exceptions;
    }

}
