package io.github.joecqupt.channel;



public interface Future<T> extends java.util.concurrent.Future<T> {
    void addListener(FutureListener listeners);

    void addListeners(FutureListener... listeners);

    void removeListener(FutureListener listeners);

    void removeListeners(FutureListener... listeners);
}
