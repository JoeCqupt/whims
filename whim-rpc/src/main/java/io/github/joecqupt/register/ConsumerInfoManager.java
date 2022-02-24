package io.github.joecqupt.register;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消费信息管理
 */
public class ConsumerInfoManager {

    private Map<ConsumerInfo, List<ServiceInstance>> providerCache = new ConcurrentHashMap<>();

    public synchronized List<ServiceInstance> getServiceInstanceList(ConsumerInfo consumerInfo) {
        return providerCache.get(consumerInfo);
    }

    public synchronized void updateConsumerInfo(ConsumerInfo consumerInfo, List<ServiceInstance> serviceInstanceList) {
        providerCache.put(consumerInfo, serviceInstanceList);
    }
}
