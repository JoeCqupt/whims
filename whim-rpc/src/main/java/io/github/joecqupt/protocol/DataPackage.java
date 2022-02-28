package io.github.joecqupt.protocol;

import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;

import java.nio.ByteBuffer;

public interface DataPackage {

    RpcRequest deserialize();

    DataPackage serialize(RpcResponse rpcResponse);

    ByteBuffer toByteBuffer();

}
