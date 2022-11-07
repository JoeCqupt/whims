package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelOutboundInvoker;
import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;

import java.net.SocketAddress;


public interface RpcChannel extends ChannelOutboundInvoker {

    void register(EventLoop eventLoop);

    ChannelPipeline pipeline();

    Unsafe unsafe();


    interface Unsafe {
        void bind(SocketAddress address, ChannelPromise promise) ;

        void connect(SocketAddress address, ChannelPromise promise);

        void disconnect(ChannelPromise promise);

        void close(ChannelPromise promise);

        void write(Object msg, ChannelPromise promise);

        void read();

        void finishConnect(ChannelPromise promise);
    }

}
