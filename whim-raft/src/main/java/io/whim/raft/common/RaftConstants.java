package io.whim.raft.common;

import java.util.Random;

public class RaftConstants {
    static final int ELECTION_TIMEOUT_MIN_MS = 150;
    static final int ELECTION_TIMEOUT_MAX_MS = 300;

    static final int ELECTION_TIMEOUT_MS_WIDTH
            = ELECTION_TIMEOUT_MAX_MS - ELECTION_TIMEOUT_MIN_MS;

    static final Random RANDOM = new Random();

    public static int getRandomElectionWaitTime() {
        return RANDOM.nextInt(ELECTION_TIMEOUT_MS_WIDTH) + ELECTION_TIMEOUT_MIN_MS;
    }
}
