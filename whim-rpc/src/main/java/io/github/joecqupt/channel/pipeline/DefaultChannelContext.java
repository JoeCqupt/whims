package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.handler.ChannelHandler;
import io.github.joecqupt.handler.ChannelInboundHandler;
import io.github.joecqupt.handler.ChannelOutboundHandler;

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
    public void fireChannelRead(Object msg) {
        ((ChannelInboundHandler) next.channelHandler).channelRead(this, msg);
    }

    @Override
    public void fireExceptionCaught(Throwable t) {
        next.channelHandler.exceptionCaught(this, t);
    }

    @Override
    public void connect(SocketAddress address) {
        ((ChannelOutboundHandler) next.channelHandler).connect(this, address);
    }

    @Override
    public void bind(SocketAddress address) {
        ((ChannelOutboundHandler) next.channelHandler).bind(this, address);
    }

    @Override
    public void write(Object msg) {
        ((ChannelOutboundHandler) next.channelHandler).write(this, msg);
    }

}
