package io.whim.rpc.echo.server.impl;

import io.whim.rpc.echo.api.EchoService;

public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String str) {
        return str;
    }

    @Override
    public int test(String msg) {
        throw new RuntimeException("app runtime ex");
    }
}
