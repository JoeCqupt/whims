package io.whim.gateway.conf;

public interface Conf<K, V> {

    V get(K key);

    short order();
}
