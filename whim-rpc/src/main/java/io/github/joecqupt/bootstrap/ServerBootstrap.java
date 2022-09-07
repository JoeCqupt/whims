package io.github.joecqupt.bootstrap;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.RpcServerChannel;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.handler.impl.RpcCodecHandler;
import io.github.joecqupt.handler.impl.RpcServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
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
    private InetSocketAddress socketAddress;

    private ServerBootstrap(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public static ServerBootstrap build() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        return new ServerBootstrap(serverSocketChannel);
    }

    public ServerBootstrap workEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
        return this;
    }

    public ServerBootstrap bind(InetSocketAddress socketAddress) throws IOException {
        this.socketAddress = socketAddress;
        this.serverSocketChannel.bind(socketAddress);
        return this;
    }

    public void start() throws IOException {
        ServerAcceptor serverAcceptor = new ServerAcceptor(this.serverSocketChannel);
        serverAcceptor.start();
        LOG.info("[RpcServer]ServerAcceptor started. listen port:{}", socketAddress.getPort());
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
                            SelectionKey key = iterator.next();
                            if (key.isValid() && key.isAcceptable()) {
                                SocketChannel socketChannel = serverSocketChannel.accept();
                                socketChannel.configureBlocking(false);
                                LOG.debug("accept a new connection:{}", socketChannel.getRemoteAddress());
                                // init pipe
                                RpcChannel rpcChannel = new RpcServerChannel(socketChannel);
                                rpcChannel.pipeline().addLast(new RpcCodecHandler());
                                rpcChannel.pipeline().addLast(new RpcServerHandler());
                                eventLoopGroup.register(rpcChannel);
                            }
                            iterator.remove();
                        }
                    }
                } catch (Exception e) {
                    LOG.error("ServerAcceptor run ex", e);
                }
            }

        }
    }
}
