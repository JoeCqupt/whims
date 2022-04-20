package io.whim.rpc;

import io.whim.rpc.registry.RegistryConfig;

public class RpcServer {
    private RegistryConfig config;
    private int port;
    private Class<?> interfaceClass;
    private Object service;

    public RpcServer registerConfig(RegistryConfig config) {
        this.config = config;
        return this;
    }

    public RpcServer port(int port) {
        this.port = port;
        return this;
    }

    public RpcServer export(Class<?> interfaceClass, Object service) {
        this.interfaceClass = interfaceClass;
        this.service = service;
        return this;
    }

    public void start() {
        // start server

        // service local
        // register service
    }
}
