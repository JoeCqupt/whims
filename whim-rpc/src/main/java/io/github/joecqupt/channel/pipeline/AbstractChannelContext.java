package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.handler.ChannelHandler;
import io.github.joecqupt.handler.ChannelInboundHandler;

public abstract class AbstractChannelContext implements ChannelContext {

    protected AbstractChannelContext prev;
    protected AbstractChannelContext next;

    protected ChannelHandler channelHandler;

    protected DefaultChannelPipeline pipeline;

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    @Override
    public void fireChannelRead(Object msg) {
        // 触发后续的context
        ((ChannelInboundHandler) next.channelHandler).channelRead(next, msg);
    }
}
