package io.github.joecqupt.invoke;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FutureStore {

    private static Map<Integer, RpcFuture> store = new ConcurrentHashMap<>();


    public static synchronized RpcFuture buildFuture(int invokeId) {
        RpcFuture future = store.get(invokeId);
        if (future == null) {
            future = new RpcFuture(invokeId);
            store.put(invokeId, future);
        }
        return future;
    }

    public static synchronized void notifyFuture(int invokeId, Object res) {
        RpcFuture future = store.get(invokeId);
        if (future == null) {
            throw new IllegalStateException(String.format("invokeId:%s not exit", invokeId));
        }
        future.notify(res);
    }
}
