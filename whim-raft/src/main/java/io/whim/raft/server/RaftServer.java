package io.whim.raft.server;

import io.whim.raft.client.protocol.ClientRaftProtocol;
import io.whim.raft.client.protocol.Message;
import io.whim.raft.client.protocol.Response;
import io.whim.raft.common.RaftConstants;
import io.whim.raft.server.protocol.RaftServerProtocol;
import io.whim.raft.server.protocol.RaftServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

public class RaftServer implements RaftServerProtocol, ClientRaftProtocol {

    public static final Logger LOG = LoggerFactory.getLogger(RaftServer.class);

    private class HeartbeatMonitor extends Thread {
        private final AtomicLong lastRpcTime = new AtomicLong(System.currentTimeMillis());
        private final long electionTimeout = RaftConstants.getRandomElectionWaitTime();

        void updateLastRpcTime(long now) {
            lastRpcTime.set(now);
        }

        @Override
        public void run() {
            while (isFollower()) {

            }
        }
    }

    private final ServerState state;

    private final RaftConfiguration raftConf;

    private volatile Role role = Role.FOLLOWER;

    private final NavigableMap<Long, Response> pendingRequests;

    /**
     * used when the peer is follower, to monitor election timeout
     */
    private HeartbeatMonitor heartbeatMonitor;

    /**
     * used when the peer is leader
     */
    private LeaderState leaderState;


    public RaftServer(String id, RaftConfiguration raftConf) {
        this.raftConf = raftConf; // TODO: how to init raft conf
        this.state = new ServerState(id);
        this.pendingRequests = new ConcurrentSkipListMap<>();
        changeToFollower();
    }


    @Override
    public void submit(Message m) throws IOException {

    }

    @Override
    public RaftServerResponse requestVote(String candidateId,
                                          long candidateTerm,
                                          TermIndex candidateLastEntry) throws IOException {
        return null;
    }

    @Override
    public RaftServerResponse appendEntries(String leaderId,
                                            long leaderTerm,
                                            TermIndex previous,
                                            long leaderCommit,
                                            Entry... entries) throws IOException {
        return null;
    }

    synchronized void changeToFollower() {
        // todo
    }

    public ServerState getState() {
        return state;
    }

    private boolean isFollower() {
        return role == Role.FOLLOWER;
    }
}
