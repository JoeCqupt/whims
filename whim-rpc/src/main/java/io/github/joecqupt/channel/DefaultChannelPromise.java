package io.github.joecqupt.channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// TODO @jiangyuan04
public class DefaultChannelPromise implements ChannelPromise {

    private RpcChannel channel;

    public DefaultChannelPromise() {
    }

    public DefaultChannelPromise(RpcChannel channel) {
        this.channel = channel;
    }

    @Override
    public RpcChannel channel() {
        return channel;
    }

    @Override
    public void addListener(FutureListener listeners) {

    }

    @Override
    public void addListeners(FutureListener... listeners) {

    }

    @Override
    public void removeListener(FutureListener listeners) {

    }

    @Override
    public void removeListeners(FutureListener... listeners) {

    }

    @Override
    public ChannelFuture await() {
        return this;
    }

    @Override
    public void setSuccess(Object res) {

    }

    @Override
    public void setFailure(Throwable t) {

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
