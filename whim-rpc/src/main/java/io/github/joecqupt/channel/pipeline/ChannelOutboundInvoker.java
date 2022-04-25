package io.github.joecqupt.channel.pipeline;

import java.net.SocketAddress;

public interface ChannelOutboundInvoker {

    void connect(SocketAddress address);

    void bind(SocketAddress address);

    void write(Object msg);
}
