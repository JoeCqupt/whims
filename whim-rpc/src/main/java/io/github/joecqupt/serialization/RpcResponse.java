package io.github.joecqupt.serialization;

public class RpcResponse {
    private RpcMeta rpcMeta;
    private Object response;

    public RpcResponse(Object response) {
        this.response = response;
    }


}
