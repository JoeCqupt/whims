package io.whim.rpc.echo.api;

import io.whim.rpc.annotation.RpcMethod;

public interface EchoService {

    @RpcMethod
    String echo(String str);

    @RpcMethod
    int test(String msg);
}
