package io.github.joecqupt.channel.handler;


import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.pipeline.ChannelContext;

import java.net.SocketAddress;

public interface ChannelOutboundHandler extends ChannelHandler {


    void connect(ChannelContext context, SocketAddress address, ChannelPromise promise);

    void disconnect(ChannelContext context, ChannelPromise promise);

    void bind(ChannelContext context, SocketAddress address, ChannelPromise promise);

    void write(ChannelContext context, Object msg, ChannelPromise promise);

    void close(ChannelContext context, ChannelPromise promise);

    void flush(ChannelContext context, ChannelPromise promise);
}
