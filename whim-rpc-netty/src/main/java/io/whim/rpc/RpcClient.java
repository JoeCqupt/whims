package io.whim.rpc;

import io.whim.rpc.registry.Registry;
import io.whim.rpc.registry.RegistryConfig;
import io.whim.rpc.registry.RegistryManager;
import io.whim.rpc.service.LocalServiceManager;
import io.whim.rpc.service.invoke.RpcProxy;

import java.lang.reflect.Proxy;

public class RpcClient {

    private RegistryConfig config;


    public RpcClient registerConfig(RegistryConfig config) {
        this.config = config;
        return this;
    }

    public <T> T importService(Class<T> interfaceClass) {
        Registry registry = RegistryManager.getRegister(config);
        LocalServiceManager.subscribe(interfaceClass, registry);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new RpcProxy(registry));
    }
}
