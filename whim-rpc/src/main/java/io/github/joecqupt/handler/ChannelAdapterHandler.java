package io.github.joecqupt.handler;


import java.net.SocketAddress;

public abstract class ChannelAdapterHandler implements ChannelInboundHandler, ChannelOutboundHandler {
    @Override
    public void exceptionCaught(ChannelContext ctx, Throwable t) {
        // todo
    }

    @Override
    public void channelRead(ChannelContext context, Object buf) {
        // todo
    }

    @Override
    public void connect(ChannelContext context, SocketAddress address) {
        // todo
    }

    @Override
    public void bind(ChannelContext context, SocketAddress address) {
        // todo
    }

    @Override
    public void write(ChannelContext context, Object msg) {
        // todo
    }
}
