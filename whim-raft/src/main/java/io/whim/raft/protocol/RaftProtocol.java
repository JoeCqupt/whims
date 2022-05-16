package io.whim.raft.protocol;


import io.whim.raft.log.RaftLog;

import java.io.IOException;

/**
 * raft protocol
 */
public interface RaftProtocol {

    class Response {
        final boolean success;
        final long term;
        final int id;

        public Response(boolean success, long term, int id) {
            this.success = success;
            this.term = term;
            this.id = id;
        }

        @Override
        public String toString() {
            return "s" + id + "-t" + term + ":" + success;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    /**
     * vote request
     */
    Response requestVote(int candidateId, long term, RaftLog.TermIndex lastCommited) throws IOException;

    /**
     * append entry
     */
    Response appendEntries(int leaderId, long term, RaftLog.TermIndex previous,
                           long leaderCommit, RaftLog.Entry... entries) throws IOException;

}
