package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.channel.ChannelFuture;
import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.handler.ChannelHandler;
import io.github.joecqupt.channel.handler.ChannelInboundHandler;
import io.github.joecqupt.channel.handler.ChannelOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public ChannelFuture connect(SocketAddress address) {
        return tail.connect(address);
    }

    @Override
    public ChannelFuture connect(SocketAddress address, ChannelPromise promise) {
        return tail.connect(address, promise);
    }

    @Override
    public ChannelFuture disconnect() {
        return tail.disconnect();
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise promise) {
        return tail.disconnect(promise);
    }

    @Override
    public ChannelFuture bind(SocketAddress address) {
        return tail.bind(address);
    }

    @Override
    public ChannelFuture bind(SocketAddress address, ChannelPromise promise) {
        return tail.bind(address, promise);
    }

    @Override
    public ChannelFuture write(Object msg) {
        return tail.write(msg);
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        return tail.write(msg, promise);
    }

    @Override
    public ChannelFuture close() {
        return tail.close();
    }

    @Override
    public ChannelFuture close(ChannelPromise promise) {
        return tail.close(promise);
    }


    static class HeadContext extends DefaultChannelContext implements ChannelInboundHandler, ChannelOutboundHandler {

        public HeadContext(DefaultChannelPipeline pipeline) {
            super(null, pipeline);
        }

        @Override
        public ChannelHandler channelHandler() {
            return this;
        }

        @Override
        public void channelRead(ChannelContext context, Object buf) {
            context.fireChannelRead(buf);
        }

        @Override
        public void exceptionCaught(ChannelContext context, Throwable t) {
            context.fireExceptionCaught(t);
        }

        @Override
        public void connect(ChannelContext context, SocketAddress address, ChannelPromise promise) {
            RpcChannel channel = context.pipeline().getChannel();
            channel.unsafe().connect(address, promise);
        }

        @Override
        public void disconnect(ChannelContext context, ChannelPromise promise) {
            RpcChannel channel = context.pipeline().getChannel();
            try {
                channel.disconnect();
                promise.setSuccess(true);
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        }

        @Override
        public void bind(ChannelContext context, SocketAddress address, ChannelPromise promise) {
            RpcChannel channel = context.pipeline().getChannel();
            channel.unsafe().bind(address, promise);
        }

        @Override
        public void write(ChannelContext context, Object msg, ChannelPromise promise) {
            RpcChannel channel = context.pipeline().getChannel();
            try {
                channel.write(msg);
                channel.flush();
                promise.setSuccess(true);
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        }

        @Override
        public void close(ChannelContext context, ChannelPromise promise) {
            RpcChannel channel = context.pipeline().getChannel();
            channel.unsafe().close(promise);
        }
    }


    static class TailContext extends DefaultChannelContext implements ChannelInboundHandler {
        private static Logger logger = LoggerFactory.getLogger(TailContext.class);

        public TailContext(DefaultChannelPipeline pipeline) {
            super(null, pipeline);
        }

        @Override
        public ChannelHandler channelHandler() {
            return this;
        }

        @Override
        public void channelRead(ChannelContext context, Object buf) {
            logger.warn("no operation for data:{}", buf);
        }

        @Override
        public void exceptionCaught(ChannelContext context, Throwable t) {
            logger.warn("no operation for exception:{}", t);
        }
    }
}
