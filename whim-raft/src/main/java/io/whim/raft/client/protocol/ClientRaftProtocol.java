package io.whim.raft.client.protocol;

import java.io.IOException;

public interface ClientRaftProtocol {

    void submit(Message m) throws IOException;
}
