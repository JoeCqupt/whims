package io.github.joecqupt.channel.handler;


import io.github.joecqupt.channel.pipeline.ChannelContext;

import java.net.SocketAddress;

public interface ChannelOutboundHandler extends ChannelHandler {

    void connect(ChannelContext context, SocketAddress address);

    void bind(ChannelContext context, SocketAddress address);

    void write(ChannelContext context, Object msg);
}
