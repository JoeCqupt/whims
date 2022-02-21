package io.github.joecqupt.serialization;

import io.github.joecqupt.protocol.ProtocolType;

public class RpcMeta {
    private String apiKey;
    private ProtocolType protocolType;

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
}