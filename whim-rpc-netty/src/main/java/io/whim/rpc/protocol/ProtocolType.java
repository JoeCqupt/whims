package io.whim.rpc.protocol;


import static io.whim.rpc.protocol.simple.SimpleDataPackage.SIMPLE_PROTOCOL_MASK;

public enum ProtocolType {
    SIMPLE;

    public static ProtocolType valueOf(int mask) {
        if (mask == SIMPLE_PROTOCOL_MASK) {
            return SIMPLE;
        }
        throw new IllegalStateException("not support protocol, invalid protocol mask:" + mask);
    }
}
