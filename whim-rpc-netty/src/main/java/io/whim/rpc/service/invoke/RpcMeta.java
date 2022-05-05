package io.whim.rpc.service.invoke;

import io.whim.rpc.protocol.ProtocolType;

public class RpcMeta {
    private String apiKey;
    private int invokeId;
    private ProtocolType protocolType;
    /**
     * 请求状态
     */
    private byte status;

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

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
