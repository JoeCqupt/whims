package io.whim.rpc.service.invoke;

import io.whim.rpc.protocol.ProtocolType;

/**
 * 上下文
 */
public class RpcContext {
    private String invokeId;

    private ProtocolType protocolType;

    public RpcContext(String invokeId, ProtocolType protocolType) {
        this.invokeId = invokeId;
        this.protocolType = protocolType;
    }

    public String getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(String invokeId) {
        this.invokeId = invokeId;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }
}
