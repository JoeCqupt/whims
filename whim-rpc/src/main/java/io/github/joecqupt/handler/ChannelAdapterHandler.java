package io.github.joecqupt.handler;


import io.github.joecqupt.channel.pipeline.ChannelContext;

import java.net.SocketAddress;

public abstract class ChannelAdapterHandler implements ChannelInboundHandler, ChannelOutboundHandler, ChannelHandler {
    @Override
    public void exceptionCaught(ChannelContext ctx, Throwable t) {
        ctx.fireExceptionCaught(t);
    }

    @Override
    public void channelRead(ChannelContext context, Object buf) {
        context.fireChannelRead(buf);
    }

    @Override
    public void connect(ChannelContext context, SocketAddress address) {
        context.connect(address);
    }

    @Override
    public void bind(ChannelContext context, SocketAddress address) {
        context.bind(address);
    }

    @Override
    public void write(ChannelContext context, Object msg) {
        context.write(msg);
    }
}
