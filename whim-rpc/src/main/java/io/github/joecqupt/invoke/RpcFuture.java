package io.github.joecqupt.invoke;

import java.util.concurrent.*;

public class RpcFuture implements Future {

    public RpcFuture(int invokeId) {
        this.invokeId = invokeId;
    }

    private int invokeId;
    private Object res;

    private CountDownLatch countDown = new CountDownLatch(1);

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
