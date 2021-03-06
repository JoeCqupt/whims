package io.whim.rpc.protocol;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;

public interface DataPackage {

    int getProtocolMaskCode();

    ProtocolType getProtocolType();

    RpcRequest deserializeRequest();

    RpcResponse deserializeResponse();

    DataPackage serialize(RpcResponse rpcResponse);

    DataPackage serialize(RpcRequest rpcRequest);

    void writeData(ByteBuf out);
}
