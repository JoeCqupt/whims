package io.whim.rpc.common;

import io.whim.rpc.service.invoke.RpcContext;

import java.util.concurrent.ConcurrentHashMap;

public class RpcContextHolder {

    private static ConcurrentHashMap<String, RpcContext> map = new ConcurrentHashMap<>();


    public static RpcContext addRpcContext(String invokeId, RpcContext rpcContext) {
        return map.put(invokeId, rpcContext);
    }

    public static RpcContext removeRpcContext(String invokeId) {
        return map.remove(invokeId);
    }
}
