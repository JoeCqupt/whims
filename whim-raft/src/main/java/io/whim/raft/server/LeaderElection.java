package io.whim.raft.server;

public class LeaderElection {


    enum Result {ELECTED, REJECTED, TIMEOUT, NEWTERM}


    private final RaftServer candidate;
    private final long electionTerm;

    public LeaderElection(RaftServer candidate, long electionTerm) {
        this.candidate = candidate;
        this.electionTerm = electionTerm;
    }

    public Result begin() {
        // todo
        return Result.ELECTED;
    }

}
