package io.github.joecqupt.bootstrap;

import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.DefaultChannelPromise;
import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.RpcClientChannel;
import io.github.joecqupt.channel.RpcServerChannel;
import io.github.joecqupt.channel.handler.ChannelAdapterHandler;
import io.github.joecqupt.channel.handler.ChannelHandler;
import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.eventloop.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

public class ServerBootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBootstrap.class);

    private EventLoopGroup workEventLoopGroup;
    private EventLoopGroup bossEventLoopGroup;
    private InetSocketAddress socketAddress;
    private List<ChannelHandler> handlers;
    private List<ChannelHandler> childHandlers;

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

    public ServerBootstrap address(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        return this;
    }

    public ServerBootstrap handler(List<ChannelHandler> handlers) {
        this.handlers = handlers;
        return this;
    }

    public ServerBootstrap childHandler(List<ChannelHandler> childHandlers) {
        this.childHandlers = childHandlers;
        return this;
    }

    public ChannelPromise bind() {
        RpcServerChannel rpcServerChannel = new RpcServerChannel();
        rpcServerChannel.pipeline().addLast(new ServerAcceptor());
        if (handlers != null) {
            handlers.forEach(channelHandler ->
                    rpcServerChannel.pipeline().addLast(channelHandler));
        }
        rpcServerChannel.bind(socketAddress);
        bossEventLoopGroup.register(rpcServerChannel);
        LOGGER.info("[RpcServer]ServerAcceptor started. listen port:{}", socketAddress.getPort());

        DefaultChannelPromise promise = new DefaultChannelPromise(rpcServerChannel);
        promise.setSuccess(true);
        return promise;
    }

    class ServerAcceptor extends ChannelAdapterHandler {
        @Override
        public void channelRead(ChannelContext context, Object buf) throws Exception {
            SocketChannel socketChannel = (SocketChannel) buf;
            LOGGER.debug("accept a new connection:{}", socketChannel.getRemoteAddress());
            // init pipe
            RpcChannel rpcChannel = new RpcClientChannel(socketChannel);
            if (childHandlers != null) {
                childHandlers.forEach(channelHandler ->
                        rpcChannel.pipeline().addLast(channelHandler));
            }
            workEventLoopGroup.register(rpcChannel);
        }
    }
}
