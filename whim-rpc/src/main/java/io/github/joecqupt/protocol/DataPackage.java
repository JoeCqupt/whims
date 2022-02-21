package io.github.joecqupt.protocol;

import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;

public interface DataPackage {

    RpcRequest deserialize();

    byte[] serialize(RpcResponse rpcResponse);
}
