package io.whim.raft.log;

import com.google.common.base.Preconditions;
import io.whim.raft.server.Entry;
import io.whim.raft.server.TermIndex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaftLog {


    private final List<Entry> entries = new ArrayList<>();
    private long lastCommitted = -1;

    public TermIndex getLastCommitted() {
        return lastCommitted > 0 ? get(lastCommitted) : new TermIndex(-1, -1);
    }

    public synchronized void setLastCommitted(long lastCommitted) {
        Preconditions.checkState(this.lastCommitted <= lastCommitted);
        this.lastCommitted = lastCommitted;
    }

    private int findIndex(long index) {
        return (int) index;
    }


    public synchronized TermIndex get(long index) {
        final int i = findIndex(index);
        return i < entries.size() ? entries.get(i) : null;
    }

    public synchronized Entry[] getEntries(long startIndex) {
        final int i = findIndex(startIndex);
        final int size = entries.size();
        return i < size ? entries.subList(i, size).toArray(Entry.EMPTY_ARRAY) : null;
    }

    public synchronized Entry getLastEntry() {
        final int size = entries.size();
        return size == 0 ? null : entries.get(size - 1);
    }

    public long getNextIndex() {
        final Entry last = getLastEntry();
        return last == null ? 1 : last.getIndex() + 1;
    }


    public boolean contains(TermIndex ti) {
        return TermIndex.equal(ti, get(ti.getIndex()));
    }

    public synchronized void apply(Entry[] entries) {
        if (entries == null || entries.length == 0) {
            return;
        }
        truncate(entries[0].getIndex());
        Preconditions.checkState(this.entries.addAll(Arrays.asList(entries)));
    }

    private void truncate(long index) {
        final int truncateIndex = findIndex(index);
        for (int i = entries.size() - 1; i >= truncateIndex; i--) {
            entries.remove(i);
        }
    }

    @Override
    public synchronized String toString() {
        return getLastEntry() + "_" + getLastCommitted();
    }
}
