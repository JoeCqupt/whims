package io.github.joecqupt.channel;

public interface Promise<R> {

    void setSuccess(Void res);

    void setFailure(Throwable t);
}
