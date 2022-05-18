package io.whim.raft.log;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class RaftLog {

    private final List<Entry> entries = new ArrayList<>();
    private long lastCommitted = -1;




    public static class TermIndex implements Comparable<TermIndex> {
        final long term;
        final long logIndex;

        public TermIndex(long term, long logIndex) {
            this.term = term;
            this.logIndex = logIndex;
        }

        public long getTerm() {
            return term;
        }

        public long getIndex() {
            return logIndex;
        }

        @Override
        public int compareTo(TermIndex that) {
            final int diff = Long.compare(this.term, that.term);
            return diff != 0 ? diff : Long.compare(this.logIndex, that.logIndex);
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


    public long getNextIndex() {
        // todo
        return 0;
    }

    public TermIndex get(long index) {
        // todo
        return null;
    }

    public Entry[] getEntries(long startIndex) {
        // todo
        return null;
    }

    public void setLastCommitted(long index) {
        // todo
    }

    public TermIndex getLastCommitted() {
        return null;
    }

    public TermIndex getLastEntry() {
        // todo

        return null;
    }

}
