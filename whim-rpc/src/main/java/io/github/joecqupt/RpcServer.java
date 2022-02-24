package io.github.joecqupt;

import io.github.joecqupt.bootstrap.ServerBootstrap;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.invoke.ServiceManager;
import io.github.joecqupt.register.ProviderInfo;
import io.github.joecqupt.register.Registry;
import io.github.joecqupt.register.RegistryConfig;
import io.github.joecqupt.register.RegistryManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class RpcServer {
    private RegistryConfig registryConfig;
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void setRegisterConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    /**
     * export a service interface
     */
    public void export(Object service) {
        List<String> apiKeys = ServiceManager.registerService(service);
        Registry register = RegistryManager.getRegister(registryConfig);
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setApiKeys(apiKeys);
        providerInfo.setPort(port);
        register.register(providerInfo);
    }

    /**
     * start the server
     */
    public void start() throws IOException {
        ServerBootstrap.build()
                .bind(new InetSocketAddress(port))
                .workEventLoopGroup(new EventLoopGroup(Runtime.getRuntime().availableProcessors()))
                .start();
    }
}
