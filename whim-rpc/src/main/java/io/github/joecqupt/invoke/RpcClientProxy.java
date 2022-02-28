package io.github.joecqupt.invoke;

import io.github.joecqupt.annotation.RpcMethod;
import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.common.Utils;
import io.github.joecqupt.connection.ConnectionPool;
import io.github.joecqupt.eventloop.EventLoopGroup;
import io.github.joecqupt.register.Registry;
import io.github.joecqupt.register.ServiceInstance;
import io.github.joecqupt.register.ServiceInstanceStore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class RpcClientProxy implements InvocationHandler {

    private Registry registry;

    private EventLoopGroup eventLoopGroup;

    public RpcClientProxy(Registry register, EventLoopGroup eventLoopGroup) {
        this.registry = register;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean isRpc = method.isAnnotationPresent(RpcMethod.class);
        if (isRpc) {
            String apiKey = Utils.apiKey(method.getDeclaringClass().getName(), method.getName());
            if(args.length>1){
                throw new IllegalArgumentException("too many args:" + apiKey);
            }
            ServiceInstanceStore instanceStore = registry.getInstanceStore();
            List<ServiceInstance> serviceInstanceList = instanceStore.getServiceInstanceList(apiKey);
            SelectionStrategy random = SelectionStrategyManager.random();
            ServiceInstance instance = random.select(serviceInstanceList);
            RpcChannel channel = ConnectionPool.getChannel(instance);
            eventLoopGroup.register(channel);
            // todo 写参数
            
        }
        return method.invoke(proxy, args);
    }
}
