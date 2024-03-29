package io.whim.rpc.service.invoke;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.whim.rpc.annotation.RpcMethod;
import io.whim.rpc.common.Utils;
import io.whim.rpc.protocol.ProtocolType;
import io.whim.rpc.registry.Registry;
import io.whim.rpc.registry.ServiceInstance;
import io.whim.rpc.registry.ServiceInstanceStore;
import io.whim.rpc.service.loadbalance.InstanceChooser;
import io.whim.rpc.service.loadbalance.InstanceChoosers;
import io.whim.rpc.transport.connection.ConnectionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class RpcProxy implements InvocationHandler {

    private Registry registry;

    private static long defaultTimeOut = 100000; // 1000ms

    private static ProtocolType defaultProtocolType = ProtocolType.SIMPLE;

    public RpcProxy(Registry registry) {
        this.registry = registry;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(RpcMethod.class)) {
            ServiceInstanceStore instanceStore = registry.getInstanceStore();
            String apiKey = Utils.apiKey(method.getDeclaringClass().getName(),
                    method.getName());
            List<ServiceInstance> serviceInstanceList = instanceStore.getServiceInstanceList(apiKey);
            if (serviceInstanceList == null || serviceInstanceList.isEmpty()) {
                throw new IllegalStateException("no instance available");
            }
            InstanceChooser chooser = InstanceChoosers.random();
            ServiceInstance instance = chooser.choose(serviceInstanceList);
            // 现在没有连接池，只能对这个连接进行全局加锁
            synchronized (ConnectionManager.class) {
                // TODO 连接池
                Channel channel = ConnectionManager.getChannel(instance);

                RpcRequest rpcRequest = new RpcRequest();
                RpcMeta rpcMeta = new RpcMeta();
                rpcMeta.setProtocolType(defaultProtocolType);
                rpcMeta.setApiKey(apiKey);
                int invokeId = Utils.genInvokeId();
                rpcMeta.setInvokeId(invokeId);
                rpcRequest.setMeta(rpcMeta);
                // 目前只支持一个参数
                rpcRequest.setRequest(args[0]);
                // 同步获取write的结果
                ChannelFuture channelFuture = channel.writeAndFlush(rpcRequest).sync();
                if (channelFuture.isSuccess()) {
                    RpcFuture future = RpcFutureStore.buildFuture(invokeId, method.getReturnType());
                    return future.result(defaultTimeOut);
                } else {
                    // channel write fail
                    throw channelFuture.cause();
                }
            }
        } else {
            return method.invoke(proxy, args);
        }
    }
}
