package io.whim.rpc.protocol;

import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;

public interface DataPackage {

    RpcRequest deserializeRequest();

    RpcResponse deserializeResponse();

    DataPackage serialize(RpcResponse rpcResponse);

    DataPackage serialize(RpcRequest rpcRequest);

}
