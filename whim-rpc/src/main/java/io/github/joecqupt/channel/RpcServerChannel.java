package io.github.joecqupt.channel;

import io.github.joecqupt.eventloop.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

public class RpcServerChannel extends AbstractRpcChannel implements RpcChannel {
    private static final Logger LOG = LoggerFactory.getLogger(RpcServerChannel.class);

    private ServerSocketChannel serverSocketChannel;

    public RpcServerChannel(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void register(EventLoop eventLoop) {
        // TODO
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
    public void write() {

    }

}
