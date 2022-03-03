package io.github.joecqupt.channel;


import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;
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

public class RpcServerChannel extends AbstractRpcChannel implements RpcChannel {
    private static final Logger LOG = LoggerFactory.getLogger(RpcServerChannel.class);
    private SocketChannel socketChannel;

    private List<ByteBuffer> writeBuffer = new ArrayList<>();

    private SelectionKey key;

    /**
     * 每次默认读取多大的信息
     */
    private static int defaultReadBufferSize = 512;

    public RpcServerChannel(SocketChannel socketChannel, ChannelPipeline pipeline) {
        this.socketChannel = socketChannel;
        this.pipeline = pipeline;
        pipeline.setChannel(this);
    }

    @Override
    public void register(EventLoop eventLoop) {
        Selector selector = eventLoop.getSelector();
        try {
            key = socketChannel.register(selector, SelectionKey.OP_READ, this);
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
            } catch (IOException e) {
                //  todo build response
            }
        }
        writeBuffer.clear();

        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
    }

    @Override
    public void finishConnect() {

    }
}
