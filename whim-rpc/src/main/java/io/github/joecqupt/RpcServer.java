package io.github.joecqupt;

import io.github.joecqupt.bootstrap.ServerBootstrap;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.invoke.ServiceManager;
import io.github.joecqupt.register.RegisterConfig;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpcServer {
    private RegisterConfig registerConfig;
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void setRegisterConfig(RegisterConfig registerConfig) {
        this.registerConfig = registerConfig;
    }

    /**
     * export a service interface
     */
    public void export(Object service) {
        ServiceManager.registerService(service, registerConfig);
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
