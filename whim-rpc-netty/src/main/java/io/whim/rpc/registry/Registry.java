package io.whim.rpc.registry;

public interface Registry {

    void init(RegistryConfig config);

    void register(ProviderInfo providerInfo);

    void unRegister(ProviderInfo providerInfo);

    void subscribe(ConsumerInfo consumerInfo);

    void unSubscribe(ConsumerInfo consumerInfo);

    ServiceInstanceStore getInstanceStore();

}
