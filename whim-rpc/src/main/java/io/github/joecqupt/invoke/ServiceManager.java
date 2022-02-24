package io.github.joecqupt.invoke;

import io.github.joecqupt.annotation.RpcMethod;
import io.github.joecqupt.register.RegistryConfig;
import io.github.joecqupt.register.RegistryManager;
import io.github.joecqupt.register.Registry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.joecqupt.common.Constants.HASH_SIGN;

public class ServiceManager {
    /**
     * 方法缓存
     * key: serviceName#methodName
     * value: ApiMeta(instance, method)
     */
    private static final Map<String, ApiMeta> API = new ConcurrentHashMap<>();

    public static synchronized List<String> registerService(Object service) {
        List<String> apiKeys = new ArrayList<>();
        Class<?> clazz = service.getClass();
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            boolean isRpcMethod = m.isAnnotationPresent(RpcMethod.class);
            if (isRpcMethod) {
                String apiKey = apiKey(clazz.getName(), m.getName());
                if (API.containsKey(apiKey)) {
                    throw new RuntimeException("api duplicate register: " + apiKey);
                }
                if (m.getParameters().length > 1) {
                    throw new RuntimeException("api has over one parameter: " + apiKey);
                }
                API.put(apiKey, new ApiMeta(service, m));
                apiKeys.add(apiKey);
            }
        }
        return apiKeys;
    }

    public static ApiMeta getApiMeta(String apiKey) {
        return API.get(apiKey);
    }

    private static String apiKey(String className, String methodName) {
        return className + HASH_SIGN + methodName;
    }

    public static class ApiMeta {
        private Object instance;
        private Method method;

        public Object getInstance() {
            return instance;
        }

        public Method getMethod() {
            return method;
        }

        public ApiMeta(Object instance, Method method) {
            this.instance = instance;
            this.method = method;
        }
    }
}
