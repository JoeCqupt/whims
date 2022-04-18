package io.whim.rpc.service;

import io.whim.rpc.service.api.ApiInfo;

import java.util.concurrent.ConcurrentHashMap;

public class ServiceManager {

    private static ConcurrentHashMap<String, ApiInfo> apiMap = new ConcurrentHashMap<>();

    public static ApiInfo getApiInfo(String apiKey) {
        return apiMap.get(apiKey);
    }

    public static void putApiInfo(String apiKey, ApiInfo apiInfo) {
        apiMap.put(apiKey, apiInfo);
    }
}
