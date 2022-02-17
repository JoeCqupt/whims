package io.github.joecqupt.channel.pipeline;


import io.github.joecqupt.handler.ChannelHandler;

public interface ChannelPipeline {
    void fireChannelRead(Object msg);

    void  addLast(ChannelHandler handler);
}
