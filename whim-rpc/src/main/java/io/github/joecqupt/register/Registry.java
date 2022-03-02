package io.github.joecqupt.register;


public interface Registry {
    void init(RegistryConfig config);

    void register(ProviderInfo providerInfo);

    void unRegister(ProviderInfo providerInfo);

    void subscribe(ConsumerInfo consumerInfo);

    void unSubscribe(ConsumerInfo consumerInfo);

    ServiceInstanceStore getInstanceStore();
}
