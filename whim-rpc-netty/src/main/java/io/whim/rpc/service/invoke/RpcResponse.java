package io.whim.rpc.service.invoke;

public class RpcResponse {
    private RpcMeta rpcMeta;

    private Object response;

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
