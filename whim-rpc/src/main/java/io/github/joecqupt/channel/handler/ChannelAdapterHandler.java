package io.github.joecqupt.channel.handler;


import io.github.joecqupt.channel.handler.ChannelHandler;
import io.github.joecqupt.channel.handler.ChannelInboundHandler;
import io.github.joecqupt.channel.handler.ChannelOutboundHandler;
import io.github.joecqupt.channel.pipeline.ChannelContext;

import java.net.SocketAddress;

public abstract class ChannelAdapterHandler implements ChannelInboundHandler, ChannelOutboundHandler, ChannelHandler {
    @Override
    public void exceptionCaught(ChannelContext ctx, Throwable t) {
        ctx.fireExceptionCaught(t);
    }

    @Override
    public void channelRead(ChannelContext ctx, Object buf) {
        ctx.fireChannelRead(buf);
    }

    @Override
    public void connect(ChannelContext ctx, SocketAddress address) {
        ctx.connect(address);
    }

    @Override
    public void bind(ChannelContext ctx, SocketAddress address) {
        ctx.bind(address);
    }

    @Override
    public void write(ChannelContext ctx, Object msg) {
        ctx.write(msg);
    }
}
