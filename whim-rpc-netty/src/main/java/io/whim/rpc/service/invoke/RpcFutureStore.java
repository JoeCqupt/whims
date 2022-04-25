package io.whim.rpc.service.invoke;

import java.util.HashMap;
import java.util.Map;

public class RpcFutureStore {

    private static Map<Integer, RpcFuture> invokeMap = new HashMap<>();


    public static RpcFuture buildFuture(int invokeId, Class<?> returnType) {
        RpcFuture future = invokeMap.get(invokeId);
        if (future != null) {
            return future;
        }
        future = new RpcFuture(returnType);
        invokeMap.put(invokeId, future);
        return future;
    }

    public static void callback(RpcResponse response) {
        int invokeId = response.getRpcMeta().getInvokeId();
        if (!invokeMap.containsKey(invokeId)) {
            throw new IllegalStateException("invoke not exist, invokeId:" + invokeId);
        }
        RpcFuture future = invokeMap.get(invokeId);
        future.callback(response.getResponse());
        invokeMap.remove(invokeId);
    }

    public static Class<?> getReturnType(int invokeId) {
        RpcFuture future = invokeMap.get(invokeId);
        if (future == null) {
            throw new IllegalStateException("invoke not exist, invokeId:" + invokeId);
        }
        return future.getReturnType();
    }
}
