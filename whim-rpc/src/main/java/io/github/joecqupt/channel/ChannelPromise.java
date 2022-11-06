package io.github.joecqupt.channel;

public interface ChannelPromise<R> extends ChannelFuture<R> {

    void setSuccess(R res);

    void setFailure(Throwable t);
}
