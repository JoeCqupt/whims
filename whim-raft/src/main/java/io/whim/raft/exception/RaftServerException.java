package io.whim.raft.exception;

public class RaftServerException extends RaftException {
    private static final long serialVersionUID = 1L;

    private final String serverId;

    public RaftServerException(String serverId, Exception cause) {
        super(cause.getClass().getName() + " from Server " + serverId, cause);
        this.serverId = serverId;
    }

}
