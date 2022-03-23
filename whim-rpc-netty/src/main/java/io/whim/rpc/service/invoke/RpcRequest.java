package io.whim.rpc.service.invoke;

public class RpcRequest {
    private RpcMeta meta;

    private Object request;

    public RpcMeta getMeta() {
        return meta;
    }

    public void setMeta(RpcMeta meta) {
        this.meta = meta;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }
}
