package io.github.joecqupt.protocol;

import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;

import java.nio.ByteBuffer;

public interface DataPackage {

    RpcRequest deserialize();

    ByteBuffer serialize(RpcResponse rpcResponse);
}
