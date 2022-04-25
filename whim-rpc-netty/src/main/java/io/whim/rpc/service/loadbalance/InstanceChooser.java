package io.whim.rpc.service.loadbalance;

import io.whim.rpc.registry.ServiceInstance;

import java.util.List;

public interface InstanceChooser {

    ServiceInstance choose(List<ServiceInstance> serviceInstanceList);


}
