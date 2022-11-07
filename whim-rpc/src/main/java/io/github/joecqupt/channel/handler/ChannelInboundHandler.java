package io.github.joecqupt.channel.handler;


import io.github.joecqupt.channel.pipeline.ChannelContext;

public interface ChannelInboundHandler extends ChannelHandler {


    void channelRead(ChannelContext context, Object buf) throws Exception;


    void exceptionCaught(ChannelContext context, Throwable t);

}
