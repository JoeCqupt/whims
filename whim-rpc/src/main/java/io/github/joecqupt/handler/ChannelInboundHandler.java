package io.github.joecqupt.handler;


import io.github.joecqupt.channel.pipeline.ChannelContext;

public interface ChannelInboundHandler extends ChannelHandler {

    void channelRead(ChannelContext context, Object buf);

}
