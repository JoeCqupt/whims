package io.whim.rpc.service.invoke;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RpcFuture {

    private Class<?> returnType;
    private Object res;
    private Exception ex;

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

    public void callback(Exception e) {
        this.ex = e;
        this.countDown.countDown();
    }

    public Object result(long timeout) throws Exception {
        boolean done = countDown.await(timeout, TimeUnit.MILLISECONDS);
        if (done) {
            if (ex != null) throw ex;
            return res;
        } else {
            throw new TimeoutException("invoke timeout");
        }
    }
}
