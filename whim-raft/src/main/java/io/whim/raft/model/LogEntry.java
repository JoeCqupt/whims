package io.whim.raft.model;

public class LogEntry<T> {

    private long term;
    private T entry;

}
