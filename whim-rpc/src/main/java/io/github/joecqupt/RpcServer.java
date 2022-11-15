package io.github.joecqupt;

import io.github.joecqupt.bootstrap.ServerBootstrap;
import io.github.joecqupt.channel.handler.ChannelHandler;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.handler.impl.RpcCodecHandler;
import io.github.joecqupt.handler.impl.RpcServerHandler;
import io.github.joecqupt.invoke.ServiceManager;
import io.github.joecqupt.register.ProviderInfo;
import io.github.joecqupt.register.Registry;
import io.github.joecqupt.register.RegistryConfig;
import io.github.joecqupt.register.RegistryManager;
import lombok.Setter;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Setter
public class RpcServer {
    private RegistryConfig registryConfig;
    private int port;
    private int bossCount = 1;
    private int workerCont = 4;

    /**
     * export a service interface
     */
    public void export(Class<?> interfaze, Object service) {
        // local register
        List<Method> rpcMethods = ServiceManager.registerService(interfaze, service);
        // remote register
        Registry register = RegistryManager.getRegister(registryConfig);
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setRpcMethods(rpcMethods);
        providerInfo.setPort(port);
        register.register(providerInfo);
    }

    /**
     * start the server
     */
    public void start() {
        List<ChannelHandler> childHandlers = new ArrayList<>();
        childHandlers.add(new RpcCodecHandler());
        childHandlers.add(new RpcServerHandler());

        ServerBootstrap.build()
                .address(new InetSocketAddress(port))
                .workEventLoopGroup(new EventLoopGroup(bossCount))
                .bossEventLoopGroup(new EventLoopGroup(workerCont))
                .childHandler(childHandlers)
                .bind();
    }
}
