package io.whim.raft.common;

import java.util.Random;

public class RaftConstants {
    public static final int ELECTION_TIMEOUT_MIN_MS = 150;
    public static final int ELECTION_TIMEOUT_MAX_MS = 300;

    public static final int RPC_TIMEOUT_MIN_MS = 150;
    public static final int RPC_TIMEOUT_MAX_MS = 300;
    public static final int RPC_SLEEP_TIME_MS = 50;

    public static final int ELECTION_TIMEOUT_MS_WIDTH
            = ELECTION_TIMEOUT_MAX_MS - ELECTION_TIMEOUT_MIN_MS;

    public static final Random RANDOM = new Random();

    public static int getRandomElectionWaitTime() {
        return RANDOM.nextInt(ELECTION_TIMEOUT_MS_WIDTH) + ELECTION_TIMEOUT_MIN_MS;
    }
}
