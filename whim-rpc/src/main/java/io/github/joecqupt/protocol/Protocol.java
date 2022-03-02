package io.github.joecqupt.protocol;

import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;

import java.nio.ByteBuffer;

public interface Protocol {
    int MASK_SIZE = 4;

    DataPackage readData(ByteBuffer buffer);

    DataPackage writeRequestData(RpcRequest rpcRequest);

    DataPackage writeResponseData(RpcResponse rpcResponse);
}
