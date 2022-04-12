package io.whim.rpc.serialize;


import io.whim.rpc.serialize.json.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class SerializerManager {
    private static Map<SerializeType, Serializer> serializerMap = new HashMap<>();

    static {
        serializerMap.put(SerializeType.JSON, new JsonSerializer());
    }

    public static Serializer getSerializer(SerializeType serializeType) {
        Serializer serializer = serializerMap.get(serializeType);
        if (serializer == null) {
            throw new IllegalStateException("no such serializer for " + serializeType);
        }
        return serializer;
    }

}
