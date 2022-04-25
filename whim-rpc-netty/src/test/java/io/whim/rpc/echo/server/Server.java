package io.whim.rpc.echo.server;

import io.whim.rpc.RpcServer;
import io.whim.rpc.echo.api.EchoService;
import io.whim.rpc.echo.server.impl.EchoServiceImpl;
import io.whim.rpc.registry.RegistryConfig;
import io.whim.rpc.registry.RegistryType;

public class Server {

    public static void main(String[] args) throws Exception {
        RegistryConfig config = new RegistryConfig();
        config.setRegisterType(RegistryType.END_POINTS);
        config.setRegisterUrl("127.0.0.1:6666");

        EchoService echoService = new EchoServiceImpl();

        RpcServer server = new RpcServer();
        server.port(6666)
                .registerConfig(config)
                .export(EchoService.class, echoService)
                .start();
    }
}
