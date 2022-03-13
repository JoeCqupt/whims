package io.github.joecqupt.handler;


import io.github.joecqupt.channel.pipeline.ChannelContext;

public interface ChannelHandler {
    void exceptionCaught(ChannelContext ctx, Throwable t);
}
