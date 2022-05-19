package io.whim.raft;

import org.junit.Test;

public class TestRaft {

    @Test
    public void testBasicRaft() throws Exception {
        final MiniRaftCluster cluster = new MiniRaftCluster(5);
        cluster.init();
        for(int i = 0; i < 5; i++) {
            System.out.print("i" + i);
            cluster.printServers(System.out);
            Thread.sleep(150);
        }
    }
}
