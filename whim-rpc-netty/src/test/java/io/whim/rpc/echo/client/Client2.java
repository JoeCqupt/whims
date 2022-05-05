package io.whim.rpc.echo.client;

import io.whim.rpc.RpcClient;
import io.whim.rpc.echo.api.EchoService;
import io.whim.rpc.registry.RegistryConfig;
import io.whim.rpc.registry.RegistryType;

public class Client2 {
    public static void main(String[] args) {
        RegistryConfig config = new RegistryConfig();
        config.setRegisterType(RegistryType.END_POINTS);
        config.setRegisterUrl("127.0.0.1:6666");

        RpcClient rpcClient = new RpcClient();
        EchoService echoService = rpcClient.registerConfig(config)
                .importService(EchoService.class);

        int res = echoService.test("test msg");

        System.out.println(res);
    }
}
