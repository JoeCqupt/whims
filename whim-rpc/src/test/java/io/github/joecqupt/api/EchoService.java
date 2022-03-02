package io.github.joecqupt.api;

import io.github.joecqupt.annotation.RpcMethod;

public interface EchoService {

    @RpcMethod
    String sayHello(String str);
}
