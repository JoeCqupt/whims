package io.github.joecqupt.channel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class RpcServerChannel extends AbstractRpcChannel implements RpcChannel {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerChannel.class);

    public RpcServerChannel() {
        super(newChannel(), SelectionKey.OP_ACCEPT);
        this.unsafe = new Unsafe();
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
        public void write(Object msg, ChannelPromise promise) {
            promise.setFailure(new UnsupportedOperationException());
        }

        @Override
        public void flush(ChannelPromise promise) {
            promise.setFailure(new UnsupportedOperationException());
        }

        @Override
        public void read() {
            SocketChannel socketChannel = null;
            try {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
                socketChannel = serverSocketChannel.accept();
            } catch (Exception e) {
                LOGGER.error("Fail accept. ", e);
                // closeOnReadError 要判斷特殊情況才能關閉通道
                RpcServerChannel.this.close();
            }
            pipeline.fireChannelRead(socketChannel);
        }

        @Override
        public void finishConnect() {
            throw new UnsupportedOperationException();
        }
    }
}
