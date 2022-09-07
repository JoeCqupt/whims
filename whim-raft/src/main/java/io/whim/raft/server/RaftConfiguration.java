package io.whim.raft.server;

import java.util.Arrays;

public class RaftConfiguration {

    private RaftPeer[] peers;

    public RaftConfiguration(RaftPeer[] peers) {
        this.peers = peers;
    }

    public RaftPeer[] getPeers() {
        return this.peers;
    }

    public int getSize() {
        return peers.length;
    }

    public RaftPeer getPeer(String id) {
        for (RaftPeer p : peers) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "RaftConfiguration: " + Arrays.asList(peers);
    }
}
