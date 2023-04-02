package io.whim.gateway;

import io.whim.gateway.conf.Confs;
import io.whim.gateway.handler.FilteringWebHandler;
import io.whim.gateway.handler.ServerExchange;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class ServerMain {
    public static void main(String[] args) {
        Confs.loadConf(args);

        FilteringWebHandler webHandler = new FilteringWebHandler();

        DisposableServer httpServer = HttpServer
                .create()
                .handle((request, response) -> {
                    ServerExchange serverExchange = new ServerExchange(request, response);
                    return webHandler.handle(serverExchange);
                })
                .bindNow();

    }
}
