package io.github.joecqupt.invoke;

import io.github.joecqupt.register.ServiceInstance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSelectionStrategy implements SelectionStrategy {
    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    @Override
    public ServiceInstance select(List<ServiceInstance> serviceInstanceList) {
        int size = serviceInstanceList.size();
        return serviceInstanceList.get(threadLocalRandom.nextInt() % size);
    }
}
