package io.whim.raft.server;

import io.whim.raft.common.RaftConstants;
import io.whim.raft.exception.RaftServerException;
import io.whim.raft.log.RaftLog;
import io.whim.raft.protocol.RaftProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RaftServer implements RaftProtocol {

    private static final Logger LOG = LoggerFactory.getLogger(RaftServer.class);


    abstract static class Role {
        abstract void rpcTimeout() throws InterruptedException, IOException;
    }


    class Leader extends Role {
        private long commitIndex = -1;

        @Override
        void rpcTimeout() throws InterruptedException, IOException {
            // send heartbeets
            for (Iterator<RaftServer> i = servers.iterator(); i.hasNext(); ) {
                RaftServer s = i.next();
                if (s != RaftServer.this) {
                    s.appendEntries(id, state.getTerm(),
                            null, commitIndex);
                }
            }
        }

        class FollowerInfo {
            private long lastRpcTime;
            private long nextIndex;
            private long matchIndex;
        }
    }

    class Follower extends Role {
        @Override
        void rpcTimeout() throws InterruptedException, IOException {
            state.setRole(new Candidate()).rpcTimeout();
        }
    }

    class Candidate extends Role {
        @Override
        void rpcTimeout() throws InterruptedException, IOException {
            final long newTerm = state.initElection(id);
            if (beginElection(newTerm)) {
                state.setRole(new Leader()).rpcTimeout();
            }
        }

        private boolean beginElection(final long newTerm) {
            final long startTime = System.currentTimeMillis();
            final long timeout = startTime + RaftConstants.getRandomElectionWaitTime();
            final ExecutorCompletionService<Response> completion
                    = new ExecutorCompletionService<>(executor);

            int submitted = 0;
            for (final Iterator<RaftServer> i = servers.iterator(); i.hasNext(); ) {
                final RaftServer s = i.next();
                if (s != RaftServer.this) {
                    submitted++;
                    completion.submit(new Callable<Response>() {
                        @Override
                        public Response call() throws Exception {
                            try {
                                return s.requestVote(id, newTerm, raftLog.getLastCommitted());
                            } catch (IOException e) {
                                throw new RaftServerException(s.id, e);
                            }
                        }
                    });
                }
            }
            final List<Response> responses = new ArrayList<>();
            final List<Exception> exceptions = new ArrayList<>();
            int granted = 0;
            for (; ; ) {
                final long waitTime = timeout - System.currentTimeMillis();
                if (waitTime <= 0) {
                    LOG.info("Election timeout: " + string(responses, exceptions));
                    return false;
                }

                Response r = null;
                try {
                    r = completion.poll(waitTime, TimeUnit.MILLISECONDS).get();
                    responses.add(r);
                    if (r.isSuccess()) {
                        granted++;
                        if (granted > servers.size() / 2) {
                            LOG.info("Election passed: " + string(responses, exceptions));
                            return true;
                        }
                    }
                } catch (Exception e) {
                    LOG.warn("", e);
                    exceptions.add(e);
                }
                if (responses.size() + exceptions.size() == submitted) {
                    // received all the responses
                    LOG.info("Election denied: " + string(responses, exceptions));
                    return false;
                }
            }
        }
    }

    class RpcMonitor extends Thread {
        private final AtomicLong lastRpcTime = new AtomicLong(System.currentTimeMillis());

        @Override
        public void run() {
            for (; ; ) {
                final long waitTime = RaftConstants.getRandomElectionWaitTime();
                try {
                    if (waitTime > 0) {
                        synchronized (this) {
                            wait(waitTime);
                        }
                    }
                    final long now = System.currentTimeMillis();
                    if (now >= lastRpcTime.get() + waitTime) {
                        lastRpcTime.set(now);
                        state.getRole().rpcTimeout();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static String string(List<Response> responses, List<Exception> exceptions) {
        return "received " + responses.size() + " response(s) and "
                + exceptions.size() + " exception(s); "
                + responses + "; " + exceptions;
    }


    private final ServerState state = new ServerState();
    private final List<RaftServer> servers = new ArrayList<>();
    private final int id;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private RaftLog raftLog;

    public RaftServer(int id) {
        this.id = id;
    }

    @Override
    public Response requestVote(int candidateId, long term, RaftLog.TermIndex lastCommited) throws IOException {
        return null;
    }

    @Override
    public Response appendEntries(int leaderId, long term, RaftLog.TermIndex previous,
                                  long leaderCommit, RaftLog.Entry... entries) throws IOException {
        return null;
    }


}
