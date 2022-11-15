package io.github.joecqupt.invoke;

import io.github.joecqupt.annotation.RpcMethod;
import io.github.joecqupt.common.ReflectionUtils;
import io.github.joecqupt.common.Utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ServiceManager {
    /**
     * 方法缓存
     * key: serviceName#methodName
     * value: ApiMeta(instance, method)
     */
    private static final Map<String, ApiMeta> API = new ConcurrentHashMap<>();

    public static synchronized List<Method> registerService(Class<?> interfaze, Object service) {
        if (!interfaze.isInstance(service)) {
            throw new IllegalArgumentException(service + "not instance of " + interfaze);
        }
        List<Method> rpcMethods = ReflectionUtils.getMethods(interfaze, RpcMethod.class);
        for (Method m : rpcMethods) {
            String apiKey = Utils.apiKey(interfaze.getName(), m.getName());

            if (API.containsKey(apiKey)) {
                throw new RuntimeException("api duplicate register: " + apiKey);
            }
            if (m.getParameters().length > 1) {
                throw new RuntimeException("api has over one parameter: " + apiKey);
            }

            try {
                API.put(apiKey, new ApiMeta(service,
                        service.getClass().getDeclaredMethod(m.getName(), m.getParameterTypes())));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return rpcMethods;
    }

    public static ApiMeta getApiMeta(String apiKey) {
        return API.get(apiKey);
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
