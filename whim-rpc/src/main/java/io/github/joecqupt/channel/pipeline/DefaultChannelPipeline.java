package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.handler.ChannelHandler;
import io.github.joecqupt.handler.ChannelInboundHandler;
import io.github.joecqupt.handler.ChannelOutboundHandler;

import java.net.SocketAddress;

public class DefaultChannelPipeline implements ChannelPipeline, ChannelInboundInvoker, ChannelOutboundInvoker {
    private RpcChannel channel;

    private static ChannelHandler HEAD_HANDLER = new HeadChannelHandler();
    private static ChannelHandler TAIL_HANDLER = new TailChannelHandler();

    protected DefaultChannelContext head;
    protected DefaultChannelContext tail;


    public DefaultChannelPipeline(RpcChannel channel) {
        head = new DefaultChannelContext(HEAD_HANDLER, this);
        tail = new DefaultChannelContext(TAIL_HANDLER, this);
        head.next = tail;
        tail.prev = head;
        this.channel = channel;
    }

    @Override
    public RpcChannel getChannel() {
        return channel;
    }


    /**
     * 新增channelHandler到tail之前
     *
     * @param handler
     */
    @Override
    public void addLast(ChannelHandler handler) {
        DefaultChannelContext newCtx = new DefaultChannelContext(handler, this);
        DefaultChannelContext prev = tail.prev;

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

    static class HeadChannelHandler implements ChannelInboundHandler, ChannelOutboundHandler, ChannelHandler {

        @Override
        public void exceptionCaught(ChannelContext ctx, Throwable t) {

        }

        @Override
        public void channelRead(ChannelContext context, Object buf) {

        }

        @Override
        public void connect(ChannelContext context, SocketAddress address) {

        }

        @Override
        public void bind(ChannelContext context, SocketAddress address) {

        }

        @Override
        public void write(ChannelContext context, Object msg) {

        }
    }

    static class TailChannelHandler implements ChannelInboundHandler, ChannelOutboundHandler, ChannelHandler {

        @Override
        public void exceptionCaught(ChannelContext ctx, Throwable t) {

        }

        @Override
        public void channelRead(ChannelContext context, Object buf) {

        }

        @Override
        public void connect(ChannelContext context, SocketAddress address) {

        }

        @Override
        public void bind(ChannelContext context, SocketAddress address) {

        }

        @Override
        public void write(ChannelContext context, Object msg) {

        }
    }
}
