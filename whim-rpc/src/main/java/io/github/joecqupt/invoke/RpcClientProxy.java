package io.github.joecqupt.invoke;

import io.github.joecqupt.annotation.RpcMethod;
import io.github.joecqupt.channel.RpcChannel;
import io.github.joecqupt.common.Utils;
import io.github.joecqupt.connection.ConnectionPool;
import io.github.joecqupt.protocol.ProtocolType;
import io.github.joecqupt.register.Registry;
import io.github.joecqupt.register.ServiceInstance;
import io.github.joecqupt.register.ServiceInstanceStore;
import io.github.joecqupt.serialization.RpcMeta;
import io.github.joecqupt.serialization.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class RpcClientProxy implements InvocationHandler {

    private Registry registry;

    private ProtocolType protocolType;

    public RpcClientProxy(Registry register, ProtocolType protocolType) {
        this.registry = register;
        this.protocolType = protocolType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean isRpc = method.isAnnotationPresent(RpcMethod.class);
        if (isRpc) {
            String apiKey = Utils.apiKey(method.getDeclaringClass().getName(), method.getName());
            if (args.length > 1) {
                throw new IllegalArgumentException("too many args:" + apiKey);
            }
            Object req = args[0];
            ServiceInstanceStore instanceStore = registry.getInstanceStore();
            List<ServiceInstance> serviceInstanceList = instanceStore.getServiceInstanceList(apiKey);
            SelectionStrategy random = SelectionStrategyManager.random();
            ServiceInstance instance = random.select(serviceInstanceList);
            RpcChannel channel = ConnectionPool.getChannel(instance);

            // 构造请求
            RpcRequest rpcRequest = new RpcRequest();
            RpcMeta rpcMeta = new RpcMeta();
            rpcMeta.setApiKey(apiKey);
            int invokeId = Utils.genInvokeId();
            rpcMeta.setInvokeId(invokeId);
            rpcMeta.setProtocolType(protocolType);
            rpcRequest.setRpcMeta(rpcMeta);
            rpcRequest.setRequest(req);
            // TODO fixit
            channel.write(rpcRequest);
            // 获取返回信息
            RpcFuture future = FutureStore.buildFuture(invokeId, method.getReturnType());
            // 同步请求方式
            return future.get();
        }
        return method.invoke(proxy, args);
    }
}
