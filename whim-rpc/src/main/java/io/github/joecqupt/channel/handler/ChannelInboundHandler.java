package io.github.joecqupt.channel.handler;


import io.github.joecqupt.channel.pipeline.ChannelContext;

public interface ChannelInboundHandler extends ChannelHandler {


    void channelRead(ChannelContext context, Object buf);


    void exceptionCaught(ChannelContext context, Throwable t);

}
