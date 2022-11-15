package io.github.joecqupt.bootstrap;

import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.DefaultChannelPromise;
import io.github.joecqupt.channel.RpcClientChannel;
import io.github.joecqupt.channel.handler.ChannelHandler;
import io.github.joecqupt.eventloop.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

public class Bootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBootstrap.class);
    private InetSocketAddress socketAddress;
    private EventLoopGroup workEventLoopGroup;
    private List<ChannelHandler> handlers;

    private Bootstrap() {
    }

    public static Bootstrap build() {
        return new Bootstrap();
    }

    public Bootstrap workEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.workEventLoopGroup = eventLoopGroup;
        return this;
    }

    public Bootstrap address(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        return this;
    }

    public Bootstrap handlers(List<ChannelHandler> handers) {
        this.handlers = handers;
        return this;
    }


    public ChannelPromise connect() {
        RpcClientChannel rpcClientChannel = new RpcClientChannel();
        if (handlers != null) {
            handlers.forEach(channelHandler ->
                    rpcClientChannel.pipeline().addLast(channelHandler));
        }
        workEventLoopGroup.register(rpcClientChannel);
        rpcClientChannel.connect(socketAddress);


        DefaultChannelPromise promise = new DefaultChannelPromise(rpcClientChannel);
        promise.setSuccess(true);
        return promise;
    }
}
