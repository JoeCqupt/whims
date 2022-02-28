package io.github.joecqupt.handler;


import io.github.joecqupt.channel.pipeline.ChannelContext;

import java.net.SocketAddress;

public interface ChannelOutboundHandler extends ChannelHandler {

    void connect(SocketAddress address);

    void bind(SocketAddress address);

    void write(ChannelContext context, Object msg);
}
