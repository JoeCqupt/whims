package io.github.joecqupt;

import io.github.joecqupt.bootstrap.ServerBootstrap;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.invoke.ServiceManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpcServer {
    /**
     * export a service interface
     */
    public void export(Object service) {
        ServiceManager.registerService(service);
    }

    /**
     * start the server
     */
    public void start() throws IOException {
        ServerBootstrap.build()
                .bind(new InetSocketAddress(8000))
                .workEventLoopGroup(new EventLoopGroup(Runtime.getRuntime().availableProcessors()))
                .start();
    }
}
