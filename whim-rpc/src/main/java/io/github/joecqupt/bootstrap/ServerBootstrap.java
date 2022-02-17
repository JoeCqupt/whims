package io.github.joecqupt.bootstrap;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.RpcChannelImpl;
import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.channel.pipeline.DefaultChannelPipeline;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.handler.impl.RpcCodecHandler;
import io.github.joecqupt.handler.impl.RpcServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerBootstrap {
    private static final Logger LOG = LoggerFactory.getLogger(ServerBootstrap.class);
    private static final Long ACCEPTOR_SELECT_TIME_OUT = 100L;

    private ServerSocketChannel serverSocketChannel;
    private EventLoopGroup eventLoopGroup;

    private ServerBootstrap(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public static ServerBootstrap build() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        return new ServerBootstrap(serverSocketChannel);
    }

    public ServerBootstrap eventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
        return this;
    }

    public ServerBootstrap bind(SocketAddress socketAddress) throws IOException {
        this.serverSocketChannel.bind(socketAddress);
        return this;
    }

    public void start() throws IOException {
        ServerAcceptor serverAcceptor = new ServerAcceptor(this.serverSocketChannel);
        serverAcceptor.start();
    }

    class ServerAcceptor extends Thread {

        private ServerSocketChannel serverSocketChannel;
        private Selector selector;

        public ServerAcceptor(ServerSocketChannel serverSocketChannel) throws IOException {
            this.serverSocketChannel = serverSocketChannel;
            this.selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int select = selector.select(ACCEPTOR_SELECT_TIME_OUT);
                    if (select > 0) {
                        Set<SelectionKey> keys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = keys.iterator();
                        while (iterator.hasNext()) {
                            iterator.remove();
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            // init pipe
                            ChannelPipeline pipeline = new DefaultChannelPipeline();
                            pipeline.addLast(new RpcCodecHandler());
                            pipeline.addLast(new RpcServerHandler());
                            RpcChannel rpcChannel = new RpcChannelImpl(socketChannel, pipeline);
                            eventLoopGroup.register(rpcChannel);
                        }
                    }
                } catch (Exception e) {
                    LOG.error("ServerAcceptor run ex", e);
                }
            }

        }
    }
}
