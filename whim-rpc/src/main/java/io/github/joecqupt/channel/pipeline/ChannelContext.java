package io.github.joecqupt.channel.pipeline;


public interface ChannelContext  extends  ChannelInboundInvoker, ChannelOutboundInvoker {

    ChannelPipeline pipeline();

}
