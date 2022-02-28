package io.github.joecqupt.register;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务实例存储
 */
public class ServiceInstanceStore {

    private Map<String, List<ServiceInstance>> cache = new ConcurrentHashMap<>();

    public synchronized List<ServiceInstance> getServiceInstanceList(String apiKey) {
        return cache.get(apiKey);
    }

    public synchronized void updateServiceInstanceList(String apiKey, List<ServiceInstance> serviceInstanceList) {
        cache.put(apiKey, serviceInstanceList);
    }
}
