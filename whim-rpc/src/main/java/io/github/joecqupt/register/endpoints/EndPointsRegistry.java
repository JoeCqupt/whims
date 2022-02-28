package io.github.joecqupt.register.endpoints;

import io.github.joecqupt.register.*;

public class EndPointsRegistry implements Registry {
    private ServiceInstanceStore serviceInstanceStore;

    @Override
    public void init(RegistryConfig config) {
        serviceInstanceStore = new ServiceInstanceStore();
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
    public void subscribe(ConsumerInfo supplierInfo) {
        // do nothing
    }

    @Override
    public void unSubscribe(ConsumerInfo supplierInfo) {
        // do nothing
    }

    @Override
    public ServiceInstanceStore getInstanceStore() {
        return this.serviceInstanceStore;
    }
}
