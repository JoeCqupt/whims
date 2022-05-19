package io.whim.raft.server;

import com.google.common.base.Preconditions;
import io.whim.raft.common.RaftConstants;
import io.whim.raft.exception.RaftServerException;
import io.whim.raft.log.RaftLog;
import io.whim.raft.protocol.RaftProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RaftServer implements RaftProtocol {

    private static final Logger LOG = LoggerFactory.getLogger(RaftServer.class);


    abstract static class Role {
        void idleRpcTimeout() throws InterruptedException, IOException {

        }
    }


    class Leader extends Role {


        private final List<FollowerInfo> followers = new ArrayList<>(ensemable.size());

        public Leader() {
            // set an expired rpc time so that heartbeats are sent to followers immediately.
            final long nextIndex = raftLog.getNextIndex();
            final long t = System.currentTimeMillis() - RaftConstants.RPC_TIMEOUT_MAX_MS;
            for (RaftServer s : ensemable.getOtherServers()) {
                followers.add(new FollowerInfo(s, t, nextIndex));
            }
        }

        public void interruptRpcSenders() {
            for (FollowerInfo f : followers) {
                f.rpcSender.interrupt();
            }
        }

        public void startRpcSenders() {
            for (FollowerInfo f : followers) {
                f.rpcSender.start();
            }
        }

        private void checkResponseTerm(long term) {
            synchronized (state) {
                if (term > state.getCurrentTerm()) {
                    changeToFollower();
                }
            }
        }

        private void updateLastCommitted() {
            // 把所有follower的matchIndex放进去
            final long[] indices = new long[followers.size() + 1];
            for (int i = 0; i < followers.size(); i++) {
                indices[i] = followers.get(i).matchIndex.get();
            }
            // 把leader的最后日志index加入
            indices[followers.size()] = raftLog.getNextIndex() - 1;

            Arrays.sort(indices);

            // 半数以上确认之后就可以commit了
            raftLog.setLastCommitted(indices[(indices.length - 1) / 2]);
        }

        class FollowerInfo {
            private final RaftServer server;
            private final AtomicLong lastRpcTime;
            private long nextIndex;
            private final AtomicLong matchIndex = new AtomicLong(-1);
            private final Thread rpcSender;

            public FollowerInfo(RaftServer server, long lastRpcTime, long nextIndex) {
                this.server = server;
                this.lastRpcTime = new AtomicLong(lastRpcTime);
                this.nextIndex = nextIndex;
                this.rpcSender = new RpcSender();
            }

            private boolean shouldSend() {
                return raftLog.get(nextIndex) != null || getHeartbeatRemainingTime() <= 0;
            }

            private long getHeartbeatRemainingTime() {
                // 选择最小超时的一半 当作心跳时间
                return (lastRpcTime.get() + RaftConstants.RPC_TIMEOUT_MIN_MS / 2) - System.currentTimeMillis();
            }

            private void checkAndSendAppendEntries()
                    throws InterruptedException, InterruptedIOException {
                for (; ; ) {
                    if (shouldSend()) {
                        Response r = sendAppendEntriesWithRetries();
                        lastRpcTime.set(System.currentTimeMillis());
                        checkResponseTerm(r.term());
                        if (!r.success()) {
                            nextIndex--; // may implements the optimization in Section 5.3
                        }
                    }

                    synchronized (this) {
                        wait(getHeartbeatRemainingTime());
                    }
                }
            }

            private Response sendAppendEntriesWithRetries()
                    throws InterruptedException, InterruptedIOException {
                RaftLog.Entry[] entries = null;
                for (int retry = 0; ; retry++) {
                    try {
                        if (entries == null) {
                            entries = raftLog.getEntries(nextIndex);
                        }
                        final RaftLog.TermIndex previous = raftLog.get(nextIndex - 1);
                        // 响应中断
                        if (Thread.interrupted()) {
                            throw new InterruptedIOException();
                        }
                        final Response r = server.appendEntries(id, state.getCurrentTerm(),
                                previous, raftLog.getLastCommitted().getIndex(), entries);
                        if (r.success()) {
                            if (entries != null || entries.length > 0) {
                                final long mi = entries[entries.length - 1].getIndex();
                                updateMatchIndex(mi);
                                nextIndex = mi + 1;
                            }
                        }
                        return r;
                    } catch (InterruptedIOException iioe) {
                        throw iioe;
                    } catch (IOException ioe) {
                        // 这个表示可重试的错误
                        LOG.warn(id + ": Failed to send appendEntries to " + server
                                + "; retry " + retry, ioe);
                    }

                    TimeUnit.MILLISECONDS.sleep(RaftConstants.RPC_SLEEP_TIME_MS);
                }
            }

            private void updateMatchIndex(long mi) {
                matchIndex.set(mi);
                updateLastCommitted();
            }

            class RpcSender extends Thread {
                @Override
                public String toString() {
                    return getClass().getSimpleName() + server.id;
                }

                @Override
                public void run() {
                    try {
                        checkAndSendAppendEntries();
                    } catch (InterruptedException | InterruptedIOException e) {
                        LOG.info(id + ": " + this + " is interrupted.", e);
                    }
                }
            }
        }
    }

    class Follower extends Role {
        @Override
        void idleRpcTimeout() throws InterruptedException, IOException {
            changeRole(new Candidate()).idleRpcTimeout();
        }
    }


    class Candidate extends Role {
        @Override
        void idleRpcTimeout() throws InterruptedException, IOException {
            for (; ; ) {
                final long electionTerm = state.initElection(id);

                // 选举结果
                final LeaderElection.Result r = new LeaderElection(
                        RaftServer.this, electionTerm).begin();

                synchronized (state) {
                    if (electionTerm != state.getCurrentTerm() || !state.isCandidate()) {
                        return; // term already passed or no longer a candidate.
                    }
                    switch (r) {
                        case ELECTED:
                            changeToLeader();
                            return;
                        case REJECTED:
                        case NEWTERM:
                            changeToFollower();
                            return;
                        case TIMEOUT:
                            // should start another election
                    }
                }
            }
        }
    }

    private void changeToFollower() {
        if (state.isLeader()) {
            ((Leader) state.getRole()).interruptRpcSenders();
        }
        if (!state.isFollower()) {
            changeRole(new Follower());
        }
    }

    private void changeToLeader() {
        Preconditions.checkState(state.isCandidate());
        changeRole(new Leader()).startRpcSenders();
    }

    private <T extends Role> T changeRole(T newRole) {
        state.changeRole(newRole);
        return newRole;
    }

    class Monitor extends Thread {
        private final AtomicLong lastRpcTime = new AtomicLong(System.currentTimeMillis());

        public void updateLastRpcTime(long now) {
            lastRpcTime.set(now);
        }

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
                        state.getRole().idleRpcTimeout();
                    }
                } catch (InterruptedException ie) {
                    // 响应中断
                    LOG.info(getClass().getSimpleName() + " interrupted.");
                    return;
                } catch (Exception e) {
                    LOG.warn(getClass().getSimpleName(), e);
                }
            }
        }
    }

    public class Ensemable {
        private final Map<String, RaftServer> otherServers = new HashMap<>();

        void setOtherServers(List<RaftServer> otherServers) {
            for (RaftServer s : otherServers) {
                this.otherServers.put(s.id, s);
            }
        }

        public Collection<RaftServer> getOtherServers() {
            return otherServers.values();
        }

        public int size() {
            return otherServers.size() + 1;
        }

    }


    private final String id;
    private final ServerState state = new ServerState(new Follower());
    private RaftLog raftLog = new RaftLog();
    private final Ensemable ensemable = new Ensemable();

    private Monitor monitor = new Monitor();

    public RaftServer(String id) {
        this.id = id;
    }

    public void init(List<RaftServer> otherServers) {
        ensemable.setOtherServers(otherServers);
        monitor.start();
    }

    public Ensemable getEnsemable() {
        return ensemable;
    }

    public Response sendRequestVote(long electionTerm, RaftServer s) throws RaftServerException {
        while (true) {
            try {
                return s.requestVote(id, electionTerm, raftLog.getLastEntry());
            } catch (IOException e) {
                throw new RaftServerException(s.id, e);
            }
        }
    }

    @Override
    public Response requestVote(String candidateId, long candidateTerm,
                                RaftLog.TermIndex candidateLastEntry) throws IOException {
        LOG.trace("{}: receive requestVote({}, {}, {})",
                id, candidateId, candidateTerm, candidateLastEntry);
        final long now = System.currentTimeMillis();
        boolean voteGranted = false;
        synchronized (state) {
            if (state.recognizeCandidate(candidateId, candidateTerm)) {
                changeToFollower();
                monitor.updateLastRpcTime(now);
                if (candidateLastEntry == null
                        || raftLog.getLastCommitted().compareTo(candidateLastEntry) <= 0) { // fixme为什么是lastcommitted
                    voteGranted = state.vote(candidateId, candidateTerm);
                }
            }
            return new Response(voteGranted, state.getCurrentTerm(), id);
        }

    }

    @Override
    public Response appendEntries(String leaderId, long term, RaftLog.TermIndex previous,
                                  long leaderCommit, RaftLog.Entry... entries) throws IOException {
        return null;
    }


}
