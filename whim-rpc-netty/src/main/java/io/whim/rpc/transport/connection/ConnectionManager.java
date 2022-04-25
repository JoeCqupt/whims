package io.whim.rpc.transport.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.whim.rpc.registry.ServiceInstance;
import io.whim.rpc.transport.handler.RpcClientHandler;
import io.whim.rpc.transport.handler.RpcCodecHandler;
import io.whim.rpc.transport.handler.RpcRequestCodecHandler;
import io.whim.rpc.transport.handler.RpcResponseCodecHandler;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private static Map<ServiceInstance, Channel> channelMap = new HashMap<>();

    private static RpcRequestCodecHandler requestCodecHandler = new RpcRequestCodecHandler();
    private static RpcResponseCodecHandler responseCodecHandler = new RpcResponseCodecHandler();
    private static RpcClientHandler clientHandler = new RpcClientHandler();
    private static NioEventLoopGroup group = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());

    public synchronized static Channel getChannel(ServiceInstance instance) {
        if (channelMap.containsKey(instance)) {
            return channelMap.get(instance);
        }
        try {
            // init channel
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap
                    .group(group)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new RpcCodecHandler(), responseCodecHandler, requestCodecHandler, clientHandler
                            );
                        }
                    })
                    .connect(instance.getIp(), instance.getPort())
                    .sync();
            if (!future.isSuccess())
                throw future.cause();

            Channel channel = future.channel();
            channelMap.put(instance, channel);
            return channel;
        } catch (Throwable e) {
            throw new RuntimeException("init channel to " + instance + " fail", e);
        }
    }
}
