package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.handler.ChannelHandler;
import io.github.joecqupt.handler.ChannelInboundHandler;
import io.github.joecqupt.handler.ChannelOutboundHandler;

public abstract class AbstractChannelContext implements ChannelContext {

    protected AbstractChannelContext prev;
    protected AbstractChannelContext next;

    protected ChannelHandler channelHandler;

    protected DefaultChannelPipeline pipeline;

    protected AbstractChannelContext head = new HeadContext();
    protected AbstractChannelContext tail = new TailContext();

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    @Override
    public void fireChannelRead(Object msg) {
        if (next == tail) {
            tail.channelRead(tail, msg);
        } else {
            // 触发后续的context
            ((ChannelInboundHandler) next.channelHandler).channelRead(next, msg);
        }
    }

    @Override
    public void write(Object msg) {
        if (prev == head) {
            head.write(head, msg);
        } else {
            ((ChannelOutboundHandler) prev.channelHandler).write(prev, msg);
        }
    }
}
