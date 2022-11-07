package io.github.joecqupt.bootstrap;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.RpcClientChannel;
import io.github.joecqupt.channel.RpcServerChannel;
import io.github.joecqupt.channel.handler.ChannelAdapterHandler;
import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.handler.impl.RpcCodecHandler;
import io.github.joecqupt.handler.impl.RpcServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ServerBootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBootstrap.class);

    private EventLoopGroup workEventLoopGroup;
    private EventLoopGroup bossEventLoopGroup;
    private InetSocketAddress socketAddress;

    private ServerBootstrap() {
    }

    public static ServerBootstrap build() {
        return new ServerBootstrap();
    }

    public ServerBootstrap workEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.workEventLoopGroup = eventLoopGroup;
        return this;
    }

    public ServerBootstrap bossEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.bossEventLoopGroup = eventLoopGroup;
        return this;
    }

    public ServerBootstrap bind(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        return this;
    }

    public void start() throws Exception {
        RpcServerChannel rpcServerChannel = new RpcServerChannel();
        rpcServerChannel.bind(socketAddress);
        rpcServerChannel.pipeline().addLast(new ServerAcceptor());
        bossEventLoopGroup.register(rpcServerChannel);
        LOGGER.info("[RpcServer]ServerAcceptor started. listen port:{}", socketAddress.getPort());
    }

    class ServerAcceptor extends ChannelAdapterHandler {
        @Override
        public void channelRead(ChannelContext context, Object buf) throws Exception {
            SocketChannel socketChannel = (SocketChannel) buf;
            socketChannel.configureBlocking(false);
            LOGGER.debug("accept a new connection:{}", socketChannel.getRemoteAddress());
            // init pipe
            RpcChannel rpcChannel = new RpcClientChannel(socketChannel);
            rpcChannel.pipeline().addLast(new RpcCodecHandler());
            rpcChannel.pipeline().addLast(new RpcServerHandler());
            workEventLoopGroup.register(rpcChannel);
        }
    }
}
