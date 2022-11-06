package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.DefaultChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;
import io.github.joecqupt.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class RpcClientChannel extends AbstractRpcChannel implements RpcChannel {
    private List<ByteBuffer> writeBuffer = new ArrayList<>();

    private SocketChannel socketChannel;

    /**
     * 每次默认读取多大的信息
     */
    private static int defaultReadBufferSize = 512;

    private SelectionKey key;
    private static final Logger LOG = LoggerFactory.getLogger(RpcClientChannel.class);

    public RpcClientChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.pipeline = new DefaultChannelPipeline(this);
    }

    @Override
    public void register(EventLoop eventLoop) {
        Selector selector = eventLoop.getSelector();
        try {
            key = socketChannel.register(selector, SelectionKey.OP_CONNECT, this);
        } catch (ClosedChannelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void bind(SocketAddress address) {

    }

    @Override
    public void connect(SocketAddress address) {

    }

    @Override
    public void disconnect() throws Exception{
        close();
    }

    @Override
    public void read() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(defaultReadBufferSize);
            socketChannel.read(buffer);
            LOG.debug("reading data...");
            buffer.flip();
            pipeline.fireChannelRead(buffer);
        } catch (IOException ioe) {
            // todo build response
        }
    }

    @Override
    public void write(Object data) {
        writeBuffer.add((ByteBuffer) data);
        key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
    }

    @Override
    public void flush() {
        for (ByteBuffer buffer : writeBuffer) {
            try {
                socketChannel.write(buffer);
                LOG.debug("flush data ....");
            } catch (Exception e) {
                throw new RpcException("fail write data to remote", e);
            }
        }
        writeBuffer.clear();
        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
        key.interestOps(key.interestOps() | SelectionKey.OP_READ);
    }

    @Override
    public void finishConnect() {
        try {
            boolean b = socketChannel.finishConnect();
            if (b) {
                key.interestOps(SelectionKey.OP_WRITE);
            }
        } catch (IOException e) {
            throw new RpcException("fail connect to remote", e);
        }
    }

    @Override
    public void close() throws Exception {
        socketChannel.close();
    }
}
