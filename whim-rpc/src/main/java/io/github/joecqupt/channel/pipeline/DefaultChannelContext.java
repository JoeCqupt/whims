package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.handler.ChannelHandler;

import java.net.SocketAddress;

public class DefaultChannelContext extends AbstractChannelContext  implements ChannelContext{

    public DefaultChannelContext(ChannelHandler channelHandler, DefaultChannelPipeline pipeline) {
        this.channelHandler = channelHandler;
        this.pipeline = pipeline;
    }

    @Override
    public void channelRead(ChannelContext context, Object buf) {

    }

    @Override
    public void connect(SocketAddress address) {

    }

    @Override
    public void bind(SocketAddress address) {

    }

    @Override
    public void write(Object msg) {

    }
}
