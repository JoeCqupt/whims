package io.whim.raft.exception;

import java.io.IOException;

public class RaftException extends IOException {
    private static final long serialVersionUID = 1L;

    public RaftException(String message) {
        super(message);
    }

    public RaftException(Exception cause) {
        super(cause);
    }

    public RaftException(String message, Exception cause) {
        super(message, cause);
    }
}