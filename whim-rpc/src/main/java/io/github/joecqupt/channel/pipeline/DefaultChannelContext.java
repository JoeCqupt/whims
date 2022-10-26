package io.github.joecqupt.channel.pipeline;

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
    public void fireChannelRead(Object msg) {
        try {
            ((ChannelInboundHandler) next.channelHandler).channelRead(next, msg);
        } catch (Exception e) {
            next.channelHandler.exceptionCaught(this, e);
        }
    }

    @Override
    public void fireExceptionCaught(Throwable t) {
        // FIXME @joecqupt
        if (channelHandler instanceof ChannelInboundHandler) {
            next.channelHandler.exceptionCaught(next, t);
        } else {
            prev.channelHandler.exceptionCaught(prev, t);
        }
    }

    @Override
    public void connect(SocketAddress address) {
        try {
            ((ChannelOutboundHandler) prev.channelHandler).connect(prev, address);
        } catch (Exception e) {
            prev.channelHandler.exceptionCaught(this, e);
        }
    }

    @Override
    public void bind(SocketAddress address) {
        try {
            ((ChannelOutboundHandler) prev.channelHandler).bind(prev, address);
        } catch (Exception e) {
            prev.channelHandler.exceptionCaught(this, e);
        }
    }

    @Override
    public void write(Object msg) {
        try {
            // FIXME 
            if (prev instanceof DefaultChannelPipeline.HeadContext) {
                prev.write(msg);
            }
            ((ChannelOutboundHandler) prev.channelHandler).write(prev, msg);
        } catch (Exception e) {
            prev.channelHandler.exceptionCaught(this, e);
        }
    }

}
