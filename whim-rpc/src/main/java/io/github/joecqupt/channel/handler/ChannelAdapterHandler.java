package io.github.joecqupt.channel.handler;


import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.pipeline.ChannelContext;

import java.net.SocketAddress;

public abstract class ChannelAdapterHandler implements ChannelInboundHandler, ChannelOutboundHandler {

    @Override
    public void channelRead(ChannelContext context, Object buf) throws Exception {
        context.fireChannelRead(buf);
    }

    @Override
    public void exceptionCaught(ChannelContext context, Throwable t) {
        context.fireExceptionCaught(t);
    }

    @Override
    public void connect(ChannelContext context, SocketAddress address, ChannelPromise promise) {
        context.connect(address, promise);
    }

    @Override
    public void bind(ChannelContext context, SocketAddress address, ChannelPromise promise) {
        context.bind(address, promise);
    }

    @Override
    public void write(ChannelContext context, Object msg, ChannelPromise promise) {
        context.write(msg, promise);
    }

    @Override
    public void close(ChannelContext context, ChannelPromise promise) {
        context.close(promise);
    }

    @Override
    public void flush(ChannelContext context, ChannelPromise promise) {
        context.flush(promise);
    }
}
