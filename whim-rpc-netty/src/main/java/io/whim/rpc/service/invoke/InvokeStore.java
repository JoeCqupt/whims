package io.whim.rpc.service.invoke;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InvokeStore {

    private static Map<Integer, CountDownLatch> invokeMap = new HashMap<>();

    private static Map<Integer, RpcResponse> responseMap = new HashMap<>();

    public synchronized static void recordRequest(int invokeId) {
        if (invokeMap.containsKey(invokeId)) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        invokeMap.put(invokeId, countDownLatch);
    }

    public static void callbackResponse(RpcResponse response) {
        int invokeId = response.getRpcMeta().getInvokeId();
        if (!invokeMap.containsKey(invokeId)) {
            throw new IllegalStateException("invoke not exist, invokeId:" + invokeId);
        }
        responseMap.put(invokeId, response);
        invokeMap.get(invokeId).countDown();
    }

    public static RpcResponse getResult(int invokeId, long timeout) throws InterruptedException, TimeoutException {
        try {
            if (!invokeMap.containsKey(invokeId)) {
                throw new IllegalStateException("invoke not exist, invokeId:" + invokeId);
            }
            CountDownLatch countDownLatch = invokeMap.get(invokeId);
            boolean done = countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
            if (done) {
                return responseMap.get(invokeId);
            } else {
                throw new TimeoutException("rpc time out");
            }
        } finally {
            invokeMap.remove(invokeId);
            responseMap.remove(invokeId);
        }
    }
}
