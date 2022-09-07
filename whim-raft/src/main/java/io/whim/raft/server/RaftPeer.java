package io.whim.raft.server;

public class RaftPeer {
  private final String id;

  public RaftPeer(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}