package io.github.joecqupt.channel;

import java.util.concurrent.Future;

public interface ChannelFuture<T> extends Future<T> {

    RpcChannel channel();

    void addListener(FutureListener listeners);

    void addListeners(FutureListener... listeners);

    void removeListener(FutureListener listeners);

    void removeListeners(FutureListener... listeners);

    ChannelFuture<T> await();
}
