package io.github.joecqupt.channel;

import io.github.joecqupt.eventloop.EventLoop;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public interface RpcChannel {


    void register(EventLoop eventLoop);


    void bind(SocketAddress address);

    void connect(SocketAddress address);

    void read();

    void write(ByteBuffer data);

    void flush();
}
