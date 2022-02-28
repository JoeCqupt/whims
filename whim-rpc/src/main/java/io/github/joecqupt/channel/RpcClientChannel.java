package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class RpcClientChannel extends AbstractRpcChannel implements RpcChannel {
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
    public void write(ByteBuffer data) {

    }

    @Override
    public void flush() {

    }
}
