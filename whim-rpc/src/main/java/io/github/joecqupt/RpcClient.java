package io.github.joecqupt;

import io.github.joecqupt.bootstrap.Bootstrap;
import io.github.joecqupt.invoke.RpcClientProxy;

import java.lang.reflect.Proxy;

public class RpcClient {


    public <T> T importService(T service) {
        Class<?> clazz = service.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new RpcClientProxy());
    }

    public void start() {
    }
}
