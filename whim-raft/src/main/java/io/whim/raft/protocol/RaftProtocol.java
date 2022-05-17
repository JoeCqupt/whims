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
        final String id;

        public Response(boolean success, long term, String id) {
            this.success = success;
            this.term = term;
            this.id = id;
        }

        public boolean success() {
            return success;
        }

        public long term() {
            return term;
        }

        public String id() {
            return id;
        }

        @Override
        public String toString() {
            return "s" + id + "-t" + term + ":" + success;
        }
    }

    /**
     * vote request
     */
    Response requestVote(String candidateId, long candidateTerm,
                         RaftLog.TermIndex candidateLastEntry) throws IOException;

    /**
     * append entry
     */
    Response appendEntries(String leaderId, long term, RaftLog.TermIndex previous,
                           long leaderCommit, RaftLog.Entry... entries) throws IOException;

}
