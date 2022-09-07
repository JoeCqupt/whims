package io.whim.raft.server;

import com.google.common.base.Preconditions;
import io.whim.raft.client.protocol.Message;

public class Entry extends TermIndex {
    public static final Entry[] EMPTY_ARRAY = {};

    public static void assertEntries(long expectedTerm, Entry... entries) {
        if (entries != null && entries.length > 0) {
            final long index0 = entries[0].getIndex();
            for (int i = 0; i < entries.length; i++) {
                final long t = entries[i].getTerm();
                Preconditions.checkArgument(expectedTerm == t, "Unexpected Term: entries[{}].getTerm()={} but expectedTerm={}", i, t, expectedTerm);

                final long indexi = entries[i].getIndex();
                Preconditions.checkArgument(indexi == index0 + i, "Unexpected Index: " + "entries[{}].getIndex()={} but entries[0].getIndex()={}", i, indexi, index0);
            }
        }
    }

    private final Message message;

    public Entry(long term, long logIndex, Message message) {
        super(term, logIndex);
        this.message = message;
    }
}