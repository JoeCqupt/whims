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
        try {
            ((ChannelInboundHandler) next.channelHandler).channelRead(this, msg);
        } catch (Exception e) {
            next.channelHandler.exceptionCaught(this, e);
        }
    }

    @Override
    public void fireExceptionCaught(Throwable t) {
        if (channelHandler instanceof ChannelInboundHandler) {
            next.channelHandler.exceptionCaught(this, t);
        } else {
            prev.channelHandler.exceptionCaught(this, t);
        }
    }

    @Override
    public void connect(SocketAddress address) {
        try {
            ((ChannelOutboundHandler) prev.channelHandler).connect(this, address);
        } catch (Exception e) {
            prev.channelHandler.exceptionCaught(this, e);
        }
    }

    @Override
    public void bind(SocketAddress address) {
        try {
            ((ChannelOutboundHandler) prev.channelHandler).bind(this, address);
        } catch (Exception e) {
            prev.channelHandler.exceptionCaught(this, e);
        }
    }

    @Override
    public void write(Object msg) {
        try {
            ((ChannelOutboundHandler) prev.channelHandler).write(this, msg);
        } catch (Exception e) {
            prev.channelHandler.exceptionCaught(this, e);
        }
    }

}
