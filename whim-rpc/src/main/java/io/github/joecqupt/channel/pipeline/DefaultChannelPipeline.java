package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.handler.ChannelHandler;
import io.github.joecqupt.handler.ChannelInboundHandler;
import io.github.joecqupt.handler.ChannelOutboundHandler;

import java.net.SocketAddress;

public class DefaultChannelPipeline extends AbstractChannelContext implements ChannelContext, ChannelPipeline {
    private RpcChannel channel;

    protected AbstractChannelContext head;
    protected AbstractChannelContext tail;



    public DefaultChannelPipeline() {
        head = new HeadContext(this);
        tail = new TailContext(this);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public RpcChannel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(RpcChannel channel) {
        this.channel = channel;
    }

    /**
     * 新增channelHandler到tail之前
     *
     * @param handler
     */
    @Override
    public void addLast(ChannelHandler handler) {
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
    public void write(ChannelContext context, Object msg) {

    }

    @Override
    public void write(Object msg) {
        tail.write(tail, msg);
    }

    @Override
    public void fireChannelRead(Object msg) {
        // 从头开始触发
        head.channelRead(head, msg);
    }
}
