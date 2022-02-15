package io.github.joecqupt.handler;


import java.net.SocketAddress;

public interface ChannelOutboundHandler {

    void connect(SocketAddress address);

    void bind(SocketAddress address);

    void write(Object msg);
}
