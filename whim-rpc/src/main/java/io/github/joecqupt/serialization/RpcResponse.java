package io.github.joecqupt.serialization;

public class RpcResponse {
    private RpcMeta rpcMeta;
    private Object response;

    public RpcResponse(RpcMeta rpcMeta, Object response) {
        this.rpcMeta = rpcMeta;
        this.response = response;
    }

    public RpcMeta getRpcMeta() {
        return rpcMeta;
    }

    public void setRpcMeta(RpcMeta rpcMeta) {
        this.rpcMeta = rpcMeta;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
