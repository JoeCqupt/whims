package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public interface RpcChannel {

    void register(EventLoop eventLoop);

    void bind(SocketAddress address);

    void connect(SocketAddress address);

    void read();

    void write(Object data);

    void flush();

    ChannelPipeline pipeline();

    void finishConnect();
}
