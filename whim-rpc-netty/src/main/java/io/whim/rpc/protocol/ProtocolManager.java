package io.whim.rpc.protocol;

import io.whim.rpc.protocol.simple.SimpleProtocol;

import static io.whim.rpc.protocol.ProtocolType.SIMPLE;

public class ProtocolManager {
    private static SimpleProtocol simpleProtocol = new SimpleProtocol();

    public static Protocol getProtocol(ProtocolType protocolType) {
        if (SIMPLE == protocolType) {
            return simpleProtocol;
        }
        throw new IllegalStateException("Protocol not found. ProtocolType:" + protocolType);
    }
}
