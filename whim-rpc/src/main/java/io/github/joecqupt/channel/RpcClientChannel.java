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

public class RpcClientChannel extends AbstractRpcChannel implements RpcChannel {
    private List<ByteBuffer> writeBuffer = new ArrayList<>();

    private SocketChannel socketChannel;
    private ChannelPipeline pipeline;
    private static final Logger LOG = LoggerFactory.getLogger(RpcClientChannel.class);

    public RpcClientChannel(SocketChannel socketChannel, ChannelPipeline pipeline) {
        this.socketChannel = socketChannel;
        this.pipeline = pipeline;
    }

    @Override
    public void register(EventLoop eventLoop) {
        Selector selector = eventLoop.getSelector();
        try {
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, this);
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

    }

    @Override
    public void write(Object data) {
        writeBuffer.add((ByteBuffer) data);
    }

    @Override
    public void flush() {
        for (ByteBuffer buffer : writeBuffer) {
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                //  todo build exception
            }
        }
    }
}
