package io.whim.raft.server;

import io.whim.raft.log.RaftLog;
import org.slf4j.Logger;

public class LeaderState {

    private static final Logger LOG = RaftServer.LOG;


    private final RaftServer server;
    private final RaftLog raftLog;
    // todo
//    private final List<RpcSender> senders;

    LeaderState(RaftServer server) {
        this.server = server;
        this.raftLog = server.getState().getLog();

        // todo
    }

}
