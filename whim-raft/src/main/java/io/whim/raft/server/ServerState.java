package io.whim.raft.server;


import com.google.common.base.Preconditions;

public class ServerState {
    private RaftServer.Role role;
    private long currentTerm = 0;
    private String leaderId;

    public ServerState(RaftServer.Role role) {
        this.role = role;
    }


    public synchronized RaftServer.Role changeRole(RaftServer.Role newRole) {
        Preconditions.checkState(role != newRole);
        role = newRole;
        return newRole;
    }

    public synchronized long initElection(String id) {
        Preconditions.checkState(isFollower() || isCandidate());
        leaderId = id;
        return ++currentTerm;
    }

    public synchronized long getCurrentTerm() {
        return currentTerm;
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

    public synchronized RaftServer.Role getRole() {
        return role;
    }


}
