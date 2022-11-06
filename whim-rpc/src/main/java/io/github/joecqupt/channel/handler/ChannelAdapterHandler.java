package io.github.joecqupt.channel.handler;


import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.pipeline.ChannelContext;

import java.net.SocketAddress;

public abstract class ChannelAdapterHandler implements ChannelInboundHandler, ChannelOutboundHandler {

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

    }

    @Override
    public void disconnect(ChannelContext context, ChannelPromise promise) {

    }

    @Override
    public void bind(ChannelContext context, SocketAddress address, ChannelPromise promise) {

    }

    @Override
    public void write(ChannelContext context, Object msg, ChannelPromise promise) {

    }

    @Override
    public void close(ChannelContext context, ChannelPromise promise) {

    }
}
