package io.whim.rpc.serialize.json;

import io.whim.rpc.serialize.Serializer;

public class JsonSerializer implements Serializer {
    // todo

    @Override
    public byte[] serialize(Object object) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return null;
    }
}
