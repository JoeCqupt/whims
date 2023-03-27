package io.whim.gateway;

import io.whim.gateway.conf.Confs;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class ServerMain {
    public static void main(String[] args) {
        Confs.loadConf(args);

        DisposableServer httpServer = HttpServer.create().bindNow();

    }
}
