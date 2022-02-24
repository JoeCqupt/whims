package io.github.joecqupt.register;

import io.github.joecqupt.common.NetUtils;

import java.util.List;

public class ProviderInfo {
    private List<String> apiKeys;
    private int port;
    private String ip = NetUtils.getLocalIp();
    private List<String> labels;

    public List<String> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(List<String> apiKeys) {
        this.apiKeys = apiKeys;
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
