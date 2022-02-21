package io.github.joecqupt.serialization;

public class RpcRequest {
    private RpcMeta rpcMeta;
    private Object request;


    public RpcMeta getRpcMeta() {
        return rpcMeta;
    }

    public void setRpcMeta(RpcMeta rpcMeta) {
        this.rpcMeta = rpcMeta;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }


}
