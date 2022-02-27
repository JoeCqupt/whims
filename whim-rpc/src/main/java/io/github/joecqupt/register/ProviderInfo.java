package io.github.joecqupt.register;

import io.github.joecqupt.common.NetUtils;

import java.lang.reflect.Method;
import java.util.List;

public class ProviderInfo {
    private List<Method> rpcMethods;
    private int port;
    private String ip = NetUtils.getLocalIp();
    private List<String> labels;

    public List<Method> getRpcMethods() {
        return rpcMethods;
    }

    public void setRpcMethods(List<Method> rpcMethods) {
        this.rpcMethods = rpcMethods;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
