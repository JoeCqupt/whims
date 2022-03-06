package io.github.joecqupt.serialization;

import io.github.joecqupt.protocol.ProtocolType;

import static io.github.joecqupt.common.Constants.STATUS_SUCCESS;

public class RpcMeta {
    private String apiKey;
    private int invokeId;
    private ProtocolType protocolType;
    private int status = STATUS_SUCCESS;
    private String errMsg;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}