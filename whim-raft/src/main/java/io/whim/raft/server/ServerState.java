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


    public synchronized boolean recognizeCandidate(String candidateId, long candidateTerm) {
        if (candidateTerm < currentTerm) {
            return false;
        } else if (candidateTerm == currentTerm && !isFollower()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean vote(String candidateId, long candidateTerm) {
        Preconditions.checkState(isFollower());

        boolean voteGranted = true;
        if (currentTerm > candidateTerm) {
            voteGranted = false;
        } else if (currentTerm < candidateTerm) {
            currentTerm = candidateTerm;
        } else {
            if (leaderId != null && leaderId != candidateId) {
                voteGranted = false;
            }
        }

        if (voteGranted) {
            leaderId = candidateId;
        }
        return voteGranted;
    }
}
