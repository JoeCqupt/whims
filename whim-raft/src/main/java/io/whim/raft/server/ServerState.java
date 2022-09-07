package io.whim.raft.server;


import com.google.common.base.Preconditions;
import io.whim.raft.log.RaftLog;

public class ServerState {

    private final String selfId;
    /**
     * Latest term server has seen. initialized to 0 on first boot, increases
     * monotonically.
     */
    private long currentTerm;
    /**
     * The server ID of the leader for this term. Null means either there is
     * no leader for this term yet or this server does not know who it is yet.
     */
    private String leaderId;
    /**
     * Candidate that received this peer's grantVote in current term (or null if none).
     */
    private String votedFor;
    /**
     * Raft log
     */
    private final RaftLog log;
    /**
     * Index of highest log entry applied to state machine
     */
    private long lastApplied;

    public ServerState(String id) {
        this.selfId = id;
        // TODO load log/currentTerm/votedFor/leaderId from persistent storage
        log = new RaftLog();
        currentTerm = 0;
        votedFor = null;
        leaderId = null;
    }

    public RaftLog getLog() {
        return log;
    }
}
