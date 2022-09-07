package io.whim.raft.server.protocol;


import io.whim.raft.server.Entry;
import io.whim.raft.server.TermIndex;

import java.io.IOException;

/**
 * raft protocol
 */
public interface RaftServerProtocol {

    /**
     * vote request
     */
    RaftServerResponse requestVote(String candidateId, long candidateTerm,
                                   TermIndex candidateLastEntry) throws IOException;

    /**
     * append entry
     */
    RaftServerResponse appendEntries(String leaderId, long leaderTerm, TermIndex previous,
                                     long leaderCommit, Entry... entries) throws IOException;

}
