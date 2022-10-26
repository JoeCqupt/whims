package io.github.joecqupt.channel.pipeline;


import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.handler.ChannelHandler;

public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker {

    void addLast(ChannelHandler handler);

    RpcChannel getChannel();

}
