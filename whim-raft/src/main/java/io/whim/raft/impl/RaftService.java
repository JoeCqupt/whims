package io.whim.raft.impl;

import io.whim.raft.model.*;
import io.whim.raft.protocol.RaftProtocol;

public class RaftService implements RaftProtocol {
    @Override
    public ElectResponse elect(ElectRequest request) {
        return null;
    }

    @Override
    public AppendEntriesResponse appendEntries(AppendEntriesRequest request) {
        return null;
    }

    @Override
    public InstallSnapshotResponse installSnapshot(InstallSnapshotRequest request) {
        return null;
    }
}
