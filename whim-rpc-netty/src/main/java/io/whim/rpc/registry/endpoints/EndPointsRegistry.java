package io.whim.rpc.registry.endpoints;

import io.whim.rpc.common.Utils;
import io.whim.rpc.registry.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.whim.rpc.common.Constants.COLON;
import static io.whim.rpc.common.Constants.SEMICOLON;

public class EndPointsRegistry implements Registry {
    private ServiceInstanceStore serviceInstanceStore;
    List<ServiceInstance> serviceInstanceList = new ArrayList<>();

    @Override
    public void init(RegistryConfig config) {
        serviceInstanceStore = new ServiceInstanceStore();
        String registerUrl = config.getRegisterUrl();
        Arrays.stream(registerUrl.split(SEMICOLON)).forEach(
                ipPortStr -> {
                    String[] split = ipPortStr.split(COLON);
                    ServiceInstance instance = new ServiceInstance(split[0], Integer.valueOf(split[1]));
                    serviceInstanceList.add(instance);
                }
        );
    }

    @Override
    public void register(ProviderInfo providerInfo) {
        // do nothing
    }

    @Override
    public void unRegister(ProviderInfo providerInfo) {
        // do nothing
    }

    @Override
    public void subscribe(ConsumerInfo consumerInfo) {
        List<Method> rpcMethods = consumerInfo.getRpcMethods();
        for (Method m : rpcMethods) {
            Class<?> declaringClass = m.getDeclaringClass();
            String apiKey = Utils.apiKey(declaringClass.getName(), m.getName());
            serviceInstanceStore.updateServiceInstanceList(apiKey, serviceInstanceList);
        }
    }

    @Override
    public void unSubscribe(ConsumerInfo consumerInfo) {
        // do nothing
    }

    @Override
    public ServiceInstanceStore getInstanceStore() {
        return this.serviceInstanceStore;
    }
}
