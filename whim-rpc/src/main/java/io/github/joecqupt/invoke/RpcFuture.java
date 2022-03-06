package io.github.joecqupt.invoke;

import java.util.concurrent.*;

public class RpcFuture implements Future {


    private int invokeId;
    private Class<?> returnType;
    private Object res;
    private Exception e;

    public Class<?> getReturnType() {
        return returnType;
    }

    private CountDownLatch countDown = new CountDownLatch(1);

    public RpcFuture(int invokeId, Class<?> returnType) {
        this.invokeId = invokeId;
        this.returnType = returnType;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        // not support
        return false;
    }

    @Override
    public boolean isCancelled() {
        // not support
        return false;
    }

    @Override
    public boolean isDone() {
        return countDown.getCount() == 0;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        countDown.await();
        return res;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        boolean done = countDown.await(timeout, unit);
        if (done) {
            return res;
        }
        throw new TimeoutException("invokeId:" + invokeId);
    }

    public void notify(Object res) {
        this.res = res;
        countDown.countDown();
    }
}
