package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.channel.ChannelFuture;
import io.github.joecqupt.channel.ChannelPromise;

import java.net.SocketAddress;

public interface ChannelOutboundInvoker {

    ChannelFuture connect(SocketAddress address);

    ChannelFuture connect(SocketAddress address, ChannelPromise promise);

    ChannelFuture disconnect();

    ChannelFuture disconnect(ChannelPromise promise);

    ChannelFuture bind(SocketAddress address);

    ChannelFuture bind(SocketAddress address, ChannelPromise promise);

    ChannelFuture write(Object msg);

    ChannelFuture write(Object msg, ChannelPromise promise);

    ChannelFuture close();

    ChannelFuture close(ChannelPromise promise);

}
