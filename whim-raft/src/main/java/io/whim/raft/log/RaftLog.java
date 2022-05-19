package io.whim.raft.log;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaftLog {


    public static class TermIndex implements Comparable<TermIndex> {
        final long term;
        final long index;

        public TermIndex(long term, long logIndex) {
            this.term = term;
            this.index = logIndex;
        }

        public long getTerm() {
            return term;
        }

        public long getIndex() {
            return index;
        }

        @Override
        public int compareTo(TermIndex that) {
            final int diff = Long.compare(this.term, that.term);
            return diff != 0 ? diff : Long.compare(this.index, that.index);
        }

        static boolean equal(TermIndex a, TermIndex b) {
            return a == b || (a != null && b != null && a.compareTo(b) == 0);
        }

        private static String toString(long n) {
            return n < 0 ? "~" : "" + n;
        }

        @Override
        public String toString() {
            return "t" + toString(term) + "i" + toString(index);
        }

    }

    public interface Message {
    }

    public static class Entry extends TermIndex {
        private final Message message;
        public static final Entry[] EMPTY_ARRAY = {};

        public Entry(long term, long logIndex, Message message) {
            super(term, logIndex);
            this.message = message;
        }

        public static void checkEntries(long leaderTerm, Entry... entries) {
            if (entries != null && entries.length > 0) {
                final long index0 = entries[0].getIndex();
                for (int i = 0; i < entries.length; i++) {
                    final long term = entries[i].getTerm();
                    Preconditions.checkArgument(leaderTerm >= term,
                            "Unexpected Term: entries[{}].getTerm()={} but leaderTerm={}",
                            i, term, leaderTerm);

                    final long indexI = entries[i].getIndex();
                    Preconditions.checkArgument(indexI == index0 + i, "Unexpected Index: "
                                    + "entries[{}].getIndex()={} but entries[0].getIndex()={}",
                            i, indexI, index0);
                }
            }
        }

    }


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
