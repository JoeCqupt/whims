package io.whim.gateway;

import io.whim.gateway.conf.Confs;
import io.whim.gateway.route.RouteLoader;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

public class ServerMain {
    public static void main(String[] args)  {
        Confs.loadConf(args);

        HttpServerRoutes httpServerRoutes = HttpServerRoutes.newRoutes();
        RouteLoader.load(httpServerRoutes);

        DisposableServer httpServer = HttpServer
                .create()
                .handle(httpServerRoutes)
                .port(8989)
                .bindNow();

        httpServer.onDispose()
                .block();
    }
}
