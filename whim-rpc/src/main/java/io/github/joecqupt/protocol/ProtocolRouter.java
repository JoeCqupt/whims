package io.github.joecqupt.protocol;

import io.github.joecqupt.exception.NotEnoughException;
import io.github.joecqupt.serialization.RpcRequest;

import java.nio.ByteBuffer;

public class ProtocolRouter implements Protocol {



    @Override
    public DataPackage readData(ByteBuffer buffer) {
        if (buffer.remaining() >= MASK_SIZE) {
            buffer.mark();
            int mask = buffer.getInt();
            buffer.reset();
            ProtocolType protocolType = ProtocolType.valueOf(mask);
            Protocol protocol = ProtocolManger.getProtocol(protocolType);
            return protocol.readData(buffer);
        } else {
            throw new NotEnoughException();
        }
    }

    @Override
    public DataPackage writeData(RpcRequest rpcRequest) {
        // do nothing
        return null;
    }
}
