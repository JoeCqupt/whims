package io.whim.raft;

import io.whim.raft.model.LogEntry;

public class RaftNode<T> {
    // persistent state
    private long currentTerm;

    private int votedFor;

    private LogEntry<T>[] log;

    // volatile state
    private long commitIndex;

    private long lastApplied;


    // volatile state (only on leader)
    private long nextIndex[];

    private long matchIndex[];



}
