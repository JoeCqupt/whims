package io.whim.raft.protocol;

import io.whim.raft.model.*;

/**
 * elect protocol
 */
public interface RaftProtocol {

    /**
     * elect
     *
     * @param request
     * @return
     */
    ElectResponse elect(ElectRequest request);


    /**
     * append entry
     *
     * @param request
     * @return
     */
    AppendEntriesResponse appendEntries(AppendEntriesRequest request);

    /**
     * install snapshot
     *
     * @param request
     * @return
     */
    InstallSnapshotResponse installSnapshot(InstallSnapshotRequest request);
}
