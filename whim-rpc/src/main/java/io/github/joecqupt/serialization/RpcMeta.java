package io.github.joecqupt.serialization;

import io.github.joecqupt.protocol.ProtocolType;

public class RpcMeta {
    private String apiKey;
    private int invokeId;
    private ProtocolType protocolType;

    public RpcMeta() {
    }

    public RpcMeta(int invokeId) {
        this.invokeId = invokeId;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(int invokeId) {
        this.invokeId = invokeId;
    }
}