package io.whim.raft.exception;

import io.whim.raft.server.RaftPeer;

public class NotLeaderException extends RaftException {
  private final RaftPeer leader;

  public NotLeaderException(RaftPeer leader) {
    super("Send the request to leader");
    this.leader = leader;
  }
}