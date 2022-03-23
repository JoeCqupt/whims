package io.whim.rpc.protocol;

import io.whim.rpc.protocol.simple.SimpleProtocol;

public enum ProtocolType {
    SIMPLE;

    ProtocolType valueOf(int mask) {
        if (mask == SimpleProtocol.SIMPLE_PROTOCOL_MASK) {
            return SIMPLE;
        }
        throw new IllegalStateException("not support protocol, invalid protocol mask:" + mask);
    }
}
