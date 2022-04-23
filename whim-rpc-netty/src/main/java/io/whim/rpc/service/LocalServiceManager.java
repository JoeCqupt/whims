package io.whim.rpc.service;

import io.whim.rpc.annotation.RpcMethod;
import io.whim.rpc.common.Utils;
import io.whim.rpc.registry.ProviderInfo;
import io.whim.rpc.registry.Registry;
import io.whim.rpc.service.api.ApiInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalServiceManager {

    private static ConcurrentHashMap<String, ApiInfo> apiMap = new ConcurrentHashMap<>();

    public synchronized static ApiInfo getApiInfo(String apiKey) {
        return apiMap.get(apiKey);
    }

    public synchronized static void registerService(Class<?> interfaceClass, Object service) {
        Method[] declaredMethods = interfaceClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(RpcMethod.class)) {
                String apiKey = Utils.apiKey(interfaceClass.getName(), method.getName());
                if (apiMap.containsKey(apiKey)) {
                    throw new IllegalStateException("api already exist, appKey:" + apiKey);
                } ApiInfo apiInfo = new ApiInfo();
                apiInfo.setService(service);
                apiInfo.setParameterType(method.getParameterTypes()[0]);
                apiInfo.setServiceClass(interfaceClass);
                apiInfo.setMethod(method);
                apiInfo.setReturnType(method.getReturnType());
                apiMap.put(apiKey, apiInfo);
            }
        }
    }

    public static void export(Registry registry, int port) {
        for (Map.Entry<String, ApiInfo> entry : apiMap.entrySet()) {
            ProviderInfo providerInfo = new ProviderInfo();
            providerInfo.setPort(port);
            List<Method> methods = new ArrayList<>();
            methods.add(entry.getValue().getMethod());
            providerInfo.setRpcMethods(methods);
            registry.register(providerInfo);
        }
    }
}
