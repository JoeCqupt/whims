package io.github.joecqupt.channel;

import io.github.joecqupt.eventloop.EventLoop;

import java.net.SocketAddress;

public interface RpcChannel {


    void register(EventLoop eventLoop);


    void bind(SocketAddress address);

    void connect(SocketAddress address);

    void read();

    void write(byte[] data);

    void flush();
}
