package io.whim.rpc.service.invoke;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RpcFuture {

    private Class<?> returnType;
    private Object res;

    private CountDownLatch countDown;

    public RpcFuture(Class<?> returnType) {
        this.returnType = returnType;
        this.countDown = new CountDownLatch(1);
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void callback(Object res) {
        this.res = res;
        this.countDown.countDown();
    }

    public Object result(long timeout) throws InterruptedException, TimeoutException {
        boolean done = countDown.await(timeout, TimeUnit.MILLISECONDS);
        if (done) {
            return res;
        } else {
            throw new TimeoutException("invoke timeout");
        }
    }
}
