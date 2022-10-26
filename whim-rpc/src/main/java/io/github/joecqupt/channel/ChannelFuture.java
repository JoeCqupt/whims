package io.github.joecqupt.channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
// TODO @jiangyuan
public class ChannelFuture implements Future<Void>, Promise{
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
    public Void get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void setSuccess(Void res) {

    }

    @Override
    public void setFailure(Throwable t) {

    }
}
