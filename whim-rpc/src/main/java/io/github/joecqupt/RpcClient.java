package io.github.joecqupt;

import io.github.joecqupt.annotation.RpcMethod;
import io.github.joecqupt.common.ReflectionUtils;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.invoke.RpcClientProxy;
import io.github.joecqupt.register.ConsumerInfo;
import io.github.joecqupt.register.Registry;
import io.github.joecqupt.register.RegistryConfig;
import io.github.joecqupt.register.RegistryManager;

import java.lang.reflect.Proxy;

public class RpcClient {

    private RegistryConfig registryConfig;

    private EventLoopGroup eventLoopGroup;

    public void setRegisterConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }


    public void eventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
    }

    public <T> T importService(T service) {
        // 订阅服务注册信息
        Registry register = RegistryManager.getRegister(registryConfig);

        // 创建代理
        Class<?> clazz = service.getClass();
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new RpcClientProxy(register, eventLoopGroup ));

        ConsumerInfo consumerInfo = new ConsumerInfo();
        consumerInfo.setRpcMethods(ReflectionUtils.getMethods(service.getClass(), RpcMethod.class));
        register.subscribe(consumerInfo);
        return proxy;
    }


}
