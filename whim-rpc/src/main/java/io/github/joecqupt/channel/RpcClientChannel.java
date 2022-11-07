package io.github.joecqupt.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class RpcClientChannel extends AbstractRpcChannel implements RpcChannel {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientChannel.class);

    private List<ByteBuffer> writeBuffer = new ArrayList<>();

    private static int defaultReadBufferSize = 512;

    public RpcClientChannel() {
        this(newChannel());
    }

    public RpcClientChannel(SelectableChannel channel) {
        super(channel, SelectionKey.OP_READ);
        this.unsafe = new Unsafe();
    }

    private static SelectableChannel newChannel() {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return channel;
    }


    class Unsafe extends AbstractUnsafe {

        @Override
        public void bind(SocketAddress address, ChannelPromise promise) {
            try {
                SocketChannel socketChannel = (SocketChannel) channel;
                socketChannel.bind(address);
                promise.setSuccess(true);
            } catch (IOException e) {
                promise.setFailure(e);
            }
        }

        @Override
        public void connect(SocketAddress address, ChannelPromise promise) {
            try {
                SocketChannel socketChannel = (SocketChannel) channel;
                socketChannel.connect(address);
                key.interestOps(SelectionKey.OP_CONNECT | key.interestOps());
                promise.setSuccess(true);
            } catch (IOException e) {
                promise.setFailure(e);
            }
        }

        @Override
        public void disconnect(ChannelPromise promise) {
            close(promise);
        }

        @Override
        public void write(Object msg, ChannelPromise promise) {
            try {
                writeBuffer.add((ByteBuffer) msg);
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                promise.setSuccess(true);
            } catch (Exception e) {
                promise.setFailure(e);
            }
        }

        @Override
        public void flush(ChannelPromise promise) {
            SocketChannel socketChannel = (SocketChannel) channel;
            for (ByteBuffer buffer : writeBuffer) {
                try {
                    socketChannel.write(buffer);
                } catch (Exception e) {
                    LOGGER.error("Fail to write.", e);
                    promise.setFailure(e);
                    RpcClientChannel.this.close(promise);
                    return;
                }
            }
            writeBuffer.clear();
            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
            promise.setSuccess(true);
        }

        @Override
        public void read() {
            SocketChannel socketChannel = (SocketChannel) channel;
            ByteBuffer buffer = null;
            try {
                buffer = ByteBuffer.allocate(defaultReadBufferSize);
                socketChannel.read(buffer);
                LOGGER.debug("reading data...");
                buffer.flip();
            } catch (Exception e) {
                LOGGER.error("Fail to read. ", e);
                RpcClientChannel.this.close();
            }
            pipeline.fireChannelRead(buffer);
        }

        @Override
        public void finishConnect() {
            try {
                SocketChannel socketChannel = (SocketChannel) channel;
                socketChannel.finishConnect();
                key.interestOps(~SelectionKey.OP_CONNECT & key.interestOps());
            } catch (Exception e) {
                LOGGER.error("Fail to finishConnect.", e);
                RpcClientChannel.this.close();
            }
        }
    }
}
