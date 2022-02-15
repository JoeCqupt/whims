package io.github.joecqupt.channel.pipeline;

import java.net.SocketAddress;

public class HeadContext extends AbstractChannelContext implements ChannelContext {
    @Override
    public void channelRead(ChannelContext context, Object buf) {
        context.fireChannelRead(buf);
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
