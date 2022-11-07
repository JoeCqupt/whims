package io.github.joecqupt.channel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class RpcServerChannel extends AbstractRpcChannel implements RpcChannel {
    private static final Logger LOG = LoggerFactory.getLogger(RpcServerChannel.class);

    public RpcServerChannel() {
        super(newChannel(), SelectionKey.OP_ACCEPT);
    }


    private static SelectableChannel newChannel() {
        ServerSocketChannel channel = null;
        try {
            channel = ServerSocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return channel;
    }





    @Override
    public RpcChannel.Unsafe unsafe() {
        return null;
    }

//
//    @Override
//    public void read() throws Exception {
//        ServerSocketChannel channel = (ServerSocketChannel) this.channel;
//        SocketChannel socketChannel = channel.accept();
//        pipeline.fireChannelRead(socketChannel);
//    }


    class Unsafe extends AbstractUnsafe {

        @Override
        public void bind(SocketAddress address, ChannelPromise promise) {
            try {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
                serverSocketChannel.bind(address);
                promise.setSuccess(this);
            } catch (IOException e) {
                promise.setFailure(e);
            }
        }

        @Override
        public void connect(SocketAddress address, ChannelPromise promise) {
            promise.setFailure(new UnsupportedOperationException());
        }

        @Override
        public void disconnect(ChannelPromise promise) {
            promise.setFailure(new UnsupportedOperationException());
        }

        @Override
        public void write(Object msg, ChannelPromise promise) {
            promise.setFailure(new UnsupportedOperationException());
        }

        @Override
        public void read() {

        }
    }
}
