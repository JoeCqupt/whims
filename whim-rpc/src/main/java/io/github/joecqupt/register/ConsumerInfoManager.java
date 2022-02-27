package io.github.joecqupt.register;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消费信息管理
 */
public class ConsumerInfoManager {

    private Map<String, List<ServiceInstance>> providerCache = new ConcurrentHashMap<>();

    public synchronized List<ServiceInstance> getServiceInstanceList(String apiKey) {
        return providerCache.get(apiKey);
    }

    public synchronized void updateConsumerInfo(String apiKey, List<ServiceInstance> serviceInstanceList) {
        providerCache.put(apiKey, serviceInstanceList);
    }
}
