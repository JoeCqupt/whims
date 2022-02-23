package io.github.joecqupt.invoke;

import io.github.joecqupt.annotation.RpcMethod;
import io.github.joecqupt.register.RegisterConfig;
import io.github.joecqupt.register.RegisterManager;
import io.github.joecqupt.register.RegisterType;
import io.github.joecqupt.register.ServiceRegister;

import java.lang.reflect.Method;
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

    public static synchronized void registerService(Object service, RegisterConfig registerConfig) {
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
                ServiceRegister register = RegisterManager.getRegister(registerConfig);
                // TODO 这里没有endpoint信息
                register.register(apiKey);
            }
        }
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
