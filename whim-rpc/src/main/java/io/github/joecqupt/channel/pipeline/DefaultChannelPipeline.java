package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.handler.ChannelHandler;

import java.net.SocketAddress;

public class DefaultChannelPipeline implements ChannelPipeline {
    private RpcChannel channel;

    protected DefaultChannelContext head;
    protected DefaultChannelContext tail;


    public DefaultChannelPipeline(RpcChannel channel) {
        head = new HeadContext(this);
        tail = new TailContext(this);
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
    public void fireChannelRead(Object msg) {
        head.fireChannelRead(msg);
    }

    @Override
    public void fireExceptionCaught(Throwable t) {
        head.fireExceptionCaught(t);
    }

    @Override
    public void connect(SocketAddress address) {
        tail.connect(address);
    }

    @Override
    public void bind(SocketAddress address) {
        tail.bind(address);
    }

    @Override
    public void write(Object msg) {
        tail.write(msg);
    }

    static class HeadContext extends DefaultChannelContext {

        public HeadContext(DefaultChannelPipeline pipeline) {
            super(null, pipeline);
        }

        @Override
        public ChannelPipeline pipeline() {
            return null;
        }


        @Override
        public void fireExceptionCaught(Throwable t) {
            // TODO @joecqupt
        }

        @Override
        public void connect(SocketAddress address) {
            // TODO
        }

        @Override
        public void bind(SocketAddress address) {
            // TODO
        }

        @Override
        public void write(Object msg) {
            this.pipeline.channel.write(msg);
            this.pipeline.channel.flush();
        }
    }


    static class TailContext extends DefaultChannelContext {
        public TailContext(DefaultChannelPipeline pipeline) {
            super(null, pipeline);
        }

        @Override
        public ChannelPipeline pipeline() {
            return null;
        }


        @Override
        public void fireExceptionCaught(Throwable t) {
            // TODO @joecqupt
        }

        @Override
        public void fireChannelRead(Object msg) {
            // TODO @joecqupt logging it
        }
    }
}
