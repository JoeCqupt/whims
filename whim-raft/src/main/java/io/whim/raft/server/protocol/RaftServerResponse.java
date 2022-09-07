package io.whim.raft.server.protocol;

public class RaftServerResponse {
  private final String peerId;
  private final long term;
  private final boolean success;
  // final long lastIndexInTerm; TODO

  public RaftServerResponse(String peerId, long term, boolean success) {
    this.peerId = peerId;
    this.term = term;
    this.success = success;
  }

  @Override
  public String toString() {
    return "Response from: " + peerId + ", term: " + term + ", result: "
        + success;
  }

  public String getPeerId() {
    return peerId;
  }

  public long getTerm() {
    return term;
  }

  public boolean isSuccess() {
    return success;
  }
}