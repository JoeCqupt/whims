package io.whim.raft.server;


public class ServerState {
    private RaftServer.Role role;
    private long currentTerm = 0;
    private int votedFor = -1;

    public synchronized long getTerm() {
        return currentTerm;
    }

    public synchronized <T extends RaftServer.Role> T setRole(T newRole) {
        role = newRole;
        return newRole;
    }

    public synchronized long initElection(int id) {
        votedFor = id;
        return ++currentTerm;
    }

    public synchronized boolean isFollower() {
        return role instanceof RaftServer.Follower;
    }

    public synchronized boolean isCandidate() {
        return role instanceof RaftServer.Candidate;
    }

    public synchronized boolean isLeader() {
        return role instanceof RaftServer.Leader;
    }

    public synchronized RaftServer.Role getRole(){
        return role;
    }

}
