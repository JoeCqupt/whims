package io.github.joecqupt.invoke;

import io.github.joecqupt.register.ServiceInstance;

import java.util.List;

public interface SelectionStrategy {
     ServiceInstance select(List<ServiceInstance> serviceInstanceList);
}
