package io.github.joecqupt.serialization;

import io.github.joecqupt.serialization.json.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class SerializerManager {
    private static Map<SerializeType, Serializer> serializerMap = new HashMap<>();

    static {
        serializerMap.put(SerializeType.JSON, new JsonSerializer());
    }

    public static Serializer getSerializer(SerializeType serializeType) {
        return serializerMap.get(serializeType);
    }

}
