package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.channel.ChannelFuture;

import java.net.SocketAddress;

public interface ChannelOutboundInvoker {

    ChannelFuture connect(SocketAddress address);

    ChannelFuture bind(SocketAddress address);

    ChannelFuture write(Object msg);
}
