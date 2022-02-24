package io.github.joecqupt.register;


public interface Registry {
    void init(RegistryConfig config);

    void register(ProviderInfo providerInfo);

    void unRegister(ProviderInfo providerInfo);

    void subscribe(ConsumerInfo supplierInfo);

    void unSubscribe(ConsumerInfo supplierInfo);

    ConsumerInfoManager getConsumerInfoManager();
}
