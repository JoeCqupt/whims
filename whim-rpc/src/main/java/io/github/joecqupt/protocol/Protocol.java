package io.github.joecqupt.protocol;

import io.github.joecqupt.serialization.RpcRequest;

import java.nio.ByteBuffer;

public interface Protocol {
    int MASK_SIZE = 4;

    DataPackage readData(ByteBuffer buffer);

    DataPackage writeData(RpcRequest rpcRequest);
}
