package io.github.joecqupt.serialization;

public interface Serializer {
    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes);
}
