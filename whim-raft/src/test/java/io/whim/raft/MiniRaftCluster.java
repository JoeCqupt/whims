package io.whim.raft;

import io.whim.raft.server.RaftServer2;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class MiniRaftCluster {

    final List<RaftServer2> servers;

    MiniRaftCluster(int numServers) {
        this.servers = new ArrayList<>();
        for (int i = 0; i < numServers; i++) {
            servers.add(new RaftServer2("s" + i));
        }
    }

    void init() {
        final List<RaftServer2> otherServers = new ArrayList<>(servers);
        for (RaftServer2 s : servers) {
            otherServers.remove(s);
            s.init(otherServers);
            otherServers.add(s);
        }
    }

    public void printServers(PrintStream out) {
        out.println("#servers = " + servers.size());
        for (RaftServer2 s : servers) {
            out.print("  ");
            out.println(s);
        }
    }
}
