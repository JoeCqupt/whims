package io.github.joecqupt.serialization.json;

import com.google.gson.Gson;
import io.github.joecqupt.serialization.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * 虽然性能不高，但是简单使用足已
 */
public class JsonSerializer implements Serializer {
    private Gson gson;

    public JsonSerializer() {
        gson = new Gson();
    }

    @Override
    public byte[] serialize(Object obj) {
        return gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), clazz);
    }
}
