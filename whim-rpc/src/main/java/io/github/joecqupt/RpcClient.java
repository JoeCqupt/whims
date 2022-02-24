package io.github.joecqupt;

import io.github.joecqupt.invoke.RpcClientProxy;
import io.github.joecqupt.register.RegistryConfig;

import java.lang.reflect.Proxy;

public class RpcClient {

    private RegistryConfig registryConfig;

    public void setRegisterConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }


    public <T> T importService(T service) {
        Class<?> clazz = service.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new RpcClientProxy());
    }

    public void start() {

    }
}
