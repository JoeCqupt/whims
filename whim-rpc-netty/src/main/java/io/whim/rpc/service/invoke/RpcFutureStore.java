package io.whim.rpc.service.invoke;

import io.whim.rpc.exception.RemoteInvokeException;

import java.util.HashMap;
import java.util.Map;

import static io.whim.rpc.common.Constants.STATUS_SUCCESS;

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
        RpcMeta rpcMeta = response.getRpcMeta();
        int invokeId = rpcMeta.getInvokeId();
        if (!invokeMap.containsKey(invokeId)) {
            throw new IllegalStateException("invoke not exist, invokeId:" + invokeId);
        }
        RpcFuture future = invokeMap.get(invokeId);
        byte status = rpcMeta.getStatus();
        if (status != STATUS_SUCCESS) {
            Exception e = new RemoteInvokeException((String) response.getResponse());
            future.callback(e);
        } else {
            future.callback(response.getResponse());
        }
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
