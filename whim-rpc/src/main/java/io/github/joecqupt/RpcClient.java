package io.github.joecqupt;

import io.github.joecqupt.annotation.RpcMethod;
import io.github.joecqupt.common.ReflectionUtils;
import io.github.joecqupt.invoke.RpcClientProxy;
import io.github.joecqupt.protocol.ProtocolType;
import io.github.joecqupt.register.ConsumerInfo;
import io.github.joecqupt.register.Registry;
import io.github.joecqupt.register.RegistryConfig;
import io.github.joecqupt.register.RegistryManager;

import java.lang.reflect.Proxy;

public class RpcClient {

    private RegistryConfig registryConfig;

    private ProtocolType protocolType;

    public void registerConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }


    public void protocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public <T> T importService(Class<T> serviceClazz) {
        if (!serviceClazz.isInterface()) {
            throw new IllegalArgumentException("importService must be interface");
        }

        // 订阅服务注册信息
        Registry register = RegistryManager.getRegister(registryConfig);
        ConsumerInfo consumerInfo = new ConsumerInfo();
        consumerInfo.setRpcMethods(ReflectionUtils.getMethods(serviceClazz, RpcMethod.class));
        register.subscribe(consumerInfo);

        // 创建代理
        T proxy = (T) Proxy.newProxyInstance(serviceClazz.getClassLoader(),
                new Class[]{serviceClazz},
                new RpcClientProxy(register, protocolType));
        
        return proxy;
    }


}
