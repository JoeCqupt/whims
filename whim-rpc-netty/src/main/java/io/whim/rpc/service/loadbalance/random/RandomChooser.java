package io.whim.rpc.service.loadbalance.random;

import io.whim.rpc.registry.ServiceInstance;
import io.whim.rpc.service.loadbalance.InstanceChooser;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomChooser implements InstanceChooser {

    @Override
    public ServiceInstance choose(List<ServiceInstance> serviceInstanceList) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int i = random.nextInt(serviceInstanceList.size());
        return serviceInstanceList.get(i);
    }
}
