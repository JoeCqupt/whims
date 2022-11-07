package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelOutboundInvoker;
import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;


public interface RpcChannel extends ChannelOutboundInvoker {

    void register(EventLoop eventLoop);

    void read() throws Exception;

    ChannelPipeline pipeline();

}
