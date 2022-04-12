package io.whim.rpc.serialize.json;

import com.google.gson.Gson;
import io.whim.rpc.serialize.Serializer;

import java.nio.charset.StandardCharsets;

public class JsonSerializer implements Serializer {
    private Gson gson;

    public JsonSerializer() {
        gson = new Gson();
    }

    @Override
    public byte[] serialize(Object object) {
        return gson.toJson(object).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), clazz);
    }
}
