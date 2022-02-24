package io.github.joecqupt.register.endpoints;

import io.github.joecqupt.register.*;

public class EndPointsRegistry implements Registry {
    private ConsumerInfoManager consumerInfoManager;

    @Override
    public void init(RegistryConfig config) {
        consumerInfoManager = new ConsumerInfoManager();
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
    public ConsumerInfoManager getConsumerInfoManager() {
        return this.consumerInfoManager;
    }
}
