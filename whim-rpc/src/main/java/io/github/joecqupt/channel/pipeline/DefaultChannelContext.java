package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.handler.ChannelHandler;
import io.github.joecqupt.handler.ChannelInboundHandler;
import io.github.joecqupt.handler.ChannelOutboundHandler;

public class DefaultChannelContext implements ChannelContext {

    protected DefaultChannelContext prev;
    protected DefaultChannelContext next;

    protected ChannelHandler channelHandler;

    protected DefaultChannelPipeline pipeline;

    public DefaultChannelContext(ChannelHandler channelHandler, DefaultChannelPipeline pipeline) {
        this.channelHandler = channelHandler;
        this.pipeline = pipeline;
    }

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    @Override
    public void fireChannelRead(Object msg) {
        if (next instanceof TailContext) {
            next.channelRead(next, msg);
        } else {
            // 触发后续的context
            ((ChannelInboundHandler) next.channelHandler).channelRead(next, msg);
        }
    }

    @Override
    public void write(Object msg) {
        if (prev instanceof HeadContext) {
            prev.write(prev, msg);
        } else {
            ((ChannelOutboundHandler) prev.channelHandler).write(prev, msg);
        }
    }
}
