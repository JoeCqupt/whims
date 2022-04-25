package io.github.joecqupt.channel.pipeline;

public interface ChannelInboundInvoker {

    void fireChannelRead(Object msg);

    void fireExceptionCaught(Throwable t);
}
