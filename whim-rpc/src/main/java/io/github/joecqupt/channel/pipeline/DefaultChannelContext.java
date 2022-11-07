package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.channel.ChannelFuture;
import io.github.joecqupt.channel.DefaultChannelPromise;
import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.handler.ChannelHandler;
import io.github.joecqupt.channel.handler.ChannelInboundHandler;
import io.github.joecqupt.channel.handler.ChannelOutboundHandler;

import java.net.SocketAddress;

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
    public ChannelHandler channelHandler() {
        return channelHandler;
    }

    private ChannelContext findNextInboundContext(DefaultChannelContext cur) {
        while (cur.next != null) {
            DefaultChannelContext ctx = cur.next;
            if (ctx.channelHandler() instanceof ChannelInboundHandler) {
                return ctx;
            }
            cur = ctx;
        }
        return null;
    }

    private ChannelContext findNextOutboundContext(DefaultChannelContext cur) {
        while (cur.prev != null) {
            DefaultChannelContext ctx = cur.prev;
            if (ctx.channelHandler() instanceof ChannelOutboundHandler) {
                return ctx;
            }
            cur = ctx;
        }
        return null;
    }

    @Override
    public void fireChannelRead(Object msg) {
        ChannelContext nextCtx = findNextInboundContext(this);
        ChannelInboundHandler handler = (ChannelInboundHandler) nextCtx.channelHandler();
        try {
            handler.channelRead(nextCtx, msg);
        } catch (Exception e) {
            handler.exceptionCaught(nextCtx, e);
        }
    }


    @Override
    public void fireExceptionCaught(Throwable t) {
        ChannelContext nextCtx = findNextInboundContext(this);
        ChannelInboundHandler handler = (ChannelInboundHandler) nextCtx.channelHandler();
        handler.exceptionCaught(nextCtx, t);
    }

    @Override
    public ChannelFuture connect(SocketAddress address) {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        connect(address, promise);
        return promise;
    }

    @Override
    public ChannelFuture connect(SocketAddress address, ChannelPromise promise) {
        ChannelContext nextCtx = findNextOutboundContext(this);
        ChannelOutboundHandler handler = (ChannelOutboundHandler) nextCtx.channelHandler();
        handler.connect(nextCtx, address, promise);
        return promise;
    }


    @Override
    public ChannelFuture disconnect() {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        disconnect(promise);
        return promise;
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise promise) {
        ChannelContext nextCtx = findNextOutboundContext(this);
        ChannelOutboundHandler handler = (ChannelOutboundHandler) nextCtx.channelHandler();
        handler.disconnect(nextCtx, promise);
        return promise;
    }

    @Override
    public ChannelFuture bind(SocketAddress address) {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        bind(address, promise);
        return promise;
    }

    @Override
    public ChannelFuture bind(SocketAddress address, ChannelPromise promise) {
        ChannelContext nextCtx = findNextOutboundContext(this);
        ChannelOutboundHandler handler = (ChannelOutboundHandler) nextCtx.channelHandler();
        handler.bind(nextCtx, address, promise);
        return promise;
    }

    @Override
    public ChannelFuture write(Object msg) {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        write(msg, promise);
        return promise;
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        ChannelContext nextCtx = findNextOutboundContext(this);
        ChannelOutboundHandler handler = (ChannelOutboundHandler) nextCtx.channelHandler();
        handler.write(nextCtx, msg, promise);
        return promise;
    }

    @Override
    public ChannelFuture close() {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        close(promise);
        return promise;
    }

    @Override
    public ChannelFuture close(ChannelPromise promise) {
        ChannelContext nextCtx = findNextOutboundContext(this);
        ChannelOutboundHandler handler = (ChannelOutboundHandler) nextCtx.channelHandler();
        handler.close(nextCtx, promise);
        return promise;
    }

    @Override
    public ChannelFuture flush() {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        flush(promise);
        return promise;
    }

    @Override
    public ChannelFuture flush(ChannelPromise promise) {
        ChannelContext nextCtx = findNextOutboundContext(this);
        ChannelOutboundHandler handler = (ChannelOutboundHandler) nextCtx.channelHandler();
        handler.flush(nextCtx, promise);
        return promise;
    }

}
