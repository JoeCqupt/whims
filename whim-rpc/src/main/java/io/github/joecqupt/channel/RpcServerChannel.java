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
    public void bind(SocketAddress address) throws Exception {
        ServerSocketChannel channel = (ServerSocketChannel) this.channel;
        channel.bind(address);
    }

    @Override
    public void connect(SocketAddress address) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void read() throws Exception {
        ServerSocketChannel channel = (ServerSocketChannel) this.channel;
        SocketChannel socketChannel = channel.accept();
        pipeline.fireChannelRead(socketChannel);
    }

    @Override
    public void write(Object data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void finishConnect() {
        throw new UnsupportedOperationException();
    }


}
