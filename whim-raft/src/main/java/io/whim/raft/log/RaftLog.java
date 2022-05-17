package io.whim.raft.log;

import java.util.List;

public class RaftLog {

    public long getNextIndex() {
        // todo
        return 0;
    }

    public static class TermIndex implements Comparable<TermIndex> {
        final long term;
        final long logIndex;

        public TermIndex(long term, long logIndex) {
            this.term = term;
            this.logIndex = logIndex;
        }

        @Override
        public int compareTo(TermIndex that) {
            final int diff = Long.compare(this.term, that.term);
            return diff != 0 ? diff : Long.compare(this.logIndex, that.logIndex);
        }
    }

    public abstract static class Entry extends TermIndex {

        public Entry(long term, long logIndex) {
            super(term, logIndex);
        }
    }

    private List<Entry> entries;
    private int lastCommitted = -1;

    public TermIndex getLastCommitted() {
        return lastCommitted > 0 ?
                entries.get(lastCommitted) : new TermIndex(-1, -1);
    }

}
