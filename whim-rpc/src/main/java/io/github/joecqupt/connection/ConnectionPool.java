package io.github.joecqupt.connection;

import io.github.joecqupt.bootstrap.Bootstrap;
import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.channel.handler.ChannelHandler;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.handler.impl.RpcClientHandler;
import io.github.joecqupt.handler.impl.RpcCodecHandler;
import io.github.joecqupt.register.ServiceInstance;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private static Map<ServiceInstance, RpcChannel> pool = new ConcurrentHashMap<>();

    public static synchronized RpcChannel getChannel(ServiceInstance instance) {
        RpcChannel rpcChannel = pool.get(instance);
        if (rpcChannel == null) {
            try {
                List<ChannelHandler> handlers = new ArrayList<>();
                handlers.add(new RpcCodecHandler());
                handlers.add(new RpcClientHandler());

                rpcChannel = Bootstrap.build()
                        .address(new InetSocketAddress(instance.getIp(), instance.getPort()))
                        .workEventLoopGroup(new EventLoopGroup(4))
                        .handlers(handlers)
                        .connect()
                        .await()
                        .channel();

                pool.put(instance, rpcChannel);
                return rpcChannel;
            } catch (Exception e) {
                throw new RuntimeException("fail connect to instance:" + instance, e);
            }
        }
        return rpcChannel;
    }

}
