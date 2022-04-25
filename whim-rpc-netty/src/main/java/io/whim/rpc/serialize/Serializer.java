package io.whim.rpc.serialize;

public interface Serializer {

    byte[] serialize(Object object);

    <T> T deserialize(byte[] data, Class<T> clazz);
}
