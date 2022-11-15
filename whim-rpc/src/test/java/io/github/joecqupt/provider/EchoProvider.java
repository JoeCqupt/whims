package io.github.joecqupt.provider;

import io.github.joecqupt.RpcServer;
import io.github.joecqupt.api.EchoService;
import io.github.joecqupt.register.RegistryConfig;
import io.github.joecqupt.register.RegistryType;

public class EchoProvider {
    private static int port = 6666;
    private static String endPoint = "127.0.0.1:" + port;

    public static void main(String[] args) throws Exception {
        EchoService echoService = new EchoServiceImpl();

        RpcServer rpcServer = new RpcServer();
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setRegistryType(RegistryType.END_POINTS);
        registryConfig.setRegisterUrl(endPoint);
        rpcServer.setPort(port);
        rpcServer.setRegistryConfig(registryConfig);
        rpcServer.setBossCount(1);
        rpcServer.setWorkerCont(4);
        rpcServer.export(EchoService.class, echoService);
        rpcServer.start();
    }
}
