package io.whim.raft.server;

import io.whim.raft.common.RaftConstants;
import io.whim.raft.log.RaftLog;
import io.whim.raft.protocol.RaftProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RaftServer implements RaftProtocol {

    private static final Logger LOG = LoggerFactory.getLogger(RaftServer.class);


    abstract static class Role {
        void idleRpcTimeout() throws InterruptedException, IOException {

        }
    }


    class Leader extends Role {
        class FollowerInfo {
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

                private void checkAndSendAppendEntries()
                        throws InterruptedException, InterruptedIOException {
                    // todo
                }
            }

            private final RaftServer server;
            private final AtomicLong lastRpcTime;
            private long nextIndex;
            private final AtomicLong matchIndex = new AtomicLong();
            private final Thread rpcSender;

            public FollowerInfo(RaftServer server, long lastRpcTime, long nextIndex) {
                this.server = server;
                this.lastRpcTime = new AtomicLong(lastRpcTime);
                this.nextIndex = nextIndex;
                this.rpcSender = new RpcSender();
            }


        }

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
        assert state.isCandidate();
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

    @Override
    public Response requestVote(String candidateId, long candidateTerm,
                                RaftLog.TermIndex candidateLastEntry) throws IOException {
        return null;
    }

    @Override
    public Response appendEntries(String leaderId, long term, RaftLog.TermIndex previous,
                                  long leaderCommit, RaftLog.Entry... entries) throws IOException {
        return null;
    }


}
