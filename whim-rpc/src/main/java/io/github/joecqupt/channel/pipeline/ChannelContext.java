package io.github.joecqupt.channel.pipeline;


import io.github.joecqupt.channel.handler.ChannelHandler;

public interface ChannelContext extends ChannelInboundInvoker, ChannelOutboundInvoker {

    ChannelPipeline pipeline();

    ChannelHandler channelHandler();
}
