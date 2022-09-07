package io.whim.raft.server;

import java.util.Objects;

public class TermIndex implements Comparable<TermIndex> {
    private final long term;
    private final long index; //log index; first index is 1.

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof TermIndex) {
            final TermIndex ti = (TermIndex) o;
            return this == ti || (this.term == ti.getTerm() && this.index == ti.getIndex());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, index);
    }

    private static String toString(long n) {
        return n < 0 ? "~" : "" + n;
    }

    @Override
    public String toString() {
        return "t" + toString(term) + "i" + toString(index);
    }
}
