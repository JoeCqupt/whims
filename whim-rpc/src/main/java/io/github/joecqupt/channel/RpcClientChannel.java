package io.github.joecqupt.channel;

import io.github.joecqupt.exception.RpcException;
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
    /**
     * 每次默认读取多大的信息
     */
    private static int defaultReadBufferSize = 512;

    public RpcClientChannel() {
        this(newChannel());
    }

    public RpcClientChannel(SelectableChannel channel) {
        super(channel, SelectionKey.OP_READ);
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

    @Override
    public void bind(SocketAddress address) throws Exception {
        SocketChannel channel = (SocketChannel) this.channel;
        channel.bind(address);
    }

    @Override
    public void connect(SocketAddress address) throws Exception {
        SocketChannel channel = (SocketChannel) this.channel;
        channel.connect(address);
        key.interestOps(SelectionKey.OP_CONNECT | key.interestOps());
    }

    @Override
    public void disconnect() throws Exception {
        close();
    }

    @Override
    public void read() throws Exception {
        SocketChannel channel = (SocketChannel) this.channel;
        ByteBuffer buffer = ByteBuffer.allocate(defaultReadBufferSize);
        channel.read(buffer);
        LOGGER.debug("reading data...");
        buffer.flip();
        pipeline.fireChannelRead(buffer);
    }

    @Override
    public void write(Object data) {
        writeBuffer.add((ByteBuffer) data);
        key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
    }

    @Override
    public void flush() {
        SocketChannel channel = (SocketChannel) this.channel;
        for (ByteBuffer buffer : writeBuffer) {
            try {
                channel.write(buffer);
                LOGGER.debug("flush data ....");
            } catch (Exception e) {
                throw new RpcException("fail write data to remote", e);
            }
        }
        writeBuffer.clear();
        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
    }

    @Override
    public void finishConnect() throws Exception {
        SocketChannel channel = (SocketChannel) this.channel;
        boolean b = channel.finishConnect();
        if (b) {
            key.interestOps(~SelectionKey.OP_CONNECT & key.interestOps());
        }

    }

    @Override
    public void close() throws Exception {
        SocketChannel channel = (SocketChannel) this.channel;
        channel.close();
    }
}
