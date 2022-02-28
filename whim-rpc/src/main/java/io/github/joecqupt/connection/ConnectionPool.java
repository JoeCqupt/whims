package io.github.joecqupt.connection;

import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.RpcClientChannel;
import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.channel.pipeline.DefaultChannelPipeline;
import io.github.joecqupt.handler.impl.RpcClientHandler;
import io.github.joecqupt.handler.impl.RpcCodecHandler;
import io.github.joecqupt.register.ServiceInstance;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private static Map<ServiceInstance, RpcClientChannel> pool = new ConcurrentHashMap<>();

    public static synchronized RpcChannel getChannel(ServiceInstance instance) {
        RpcClientChannel rpcClientChannel = pool.get(instance);
        if (rpcClientChannel == null) {
            try {
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
                socketChannel.connect(new InetSocketAddress(instance.getIp(), instance.getPort()));

                ChannelPipeline pipeline = new DefaultChannelPipeline();
                pipeline.addLast(new RpcCodecHandler());
                pipeline.addLast(new RpcClientHandler());
                rpcClientChannel = new RpcClientChannel(socketChannel, pipeline);
                pool.put(instance, rpcClientChannel);
                return rpcClientChannel;
            } catch (Exception e) {
                throw new RuntimeException("fail connect to instance:" + instance, e);
            }
        }
        return rpcClientChannel;
    }

}
