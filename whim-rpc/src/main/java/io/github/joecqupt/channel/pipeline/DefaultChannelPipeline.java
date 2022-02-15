package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.handler.ChannelHandler;
import io.github.joecqupt.handler.ChannelInboundHandler;

import java.net.SocketAddress;

public class DefaultChannelPipeline extends AbstractChannelContext implements ChannelContext {

    private AbstractChannelContext head = new HeadContext();
    private AbstractChannelContext tail = new TailContext();

    public DefaultChannelPipeline() {
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 新增channelHandler到tail之前
     * @param handler
     */
    public void addLastChannelHandler(ChannelHandler handler) {
        AbstractChannelContext newCtx = new DefaultChannelContext(handler, this);
        AbstractChannelContext prev = tail.prev;

        newCtx.prev = prev;
        prev.next = newCtx;
        newCtx.next = tail;
        tail.prev = newCtx;
    }

    @Override
    public void channelRead(ChannelContext context, Object buf) {

    }

    @Override
    public void connect(SocketAddress address) {

    }

    @Override
    public void bind(SocketAddress address) {

    }

    @Override
    public void write(Object msg) {

    }

    @Override
    public void fireChannelRead(Object msg) {
        // 从头开始触发
        ((ChannelInboundHandler) head.channelHandler).channelRead(head, msg);
    }
}
