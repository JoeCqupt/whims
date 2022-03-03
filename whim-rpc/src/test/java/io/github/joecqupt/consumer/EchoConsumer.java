package io.github.joecqupt.consumer;

import io.github.joecqupt.RpcClient;
import io.github.joecqupt.api.EchoService;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.protocol.ProtocolType;
import io.github.joecqupt.register.RegistryConfig;
import io.github.joecqupt.register.RegistryType;

public class EchoConsumer {

    private static int port = 6666;
    private static String endPoint = "127.0.0.1:" + port;

    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient();
        rpcClient.eventLoopGroup(new EventLoopGroup(1));
        rpcClient.protocolType(ProtocolType.SIMPLE);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setRegisterType(RegistryType.END_POINTS);
        registryConfig.setRegisterUrl(endPoint);
        rpcClient.registerConfig(registryConfig);
        EchoService echoService = rpcClient.importService(EchoService.class);
        String res = echoService.sayHello("hello whim-rpc!");
        System.out.println(res);
    }
}
