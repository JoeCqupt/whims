package io.github.joecqupt.serialization.json;

import io.github.joecqupt.serialization.Serializer;

/**
 * 虽然性能不高，但是简单使用足已
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        // todo
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) {
        // todo
        return null;
    }
}
