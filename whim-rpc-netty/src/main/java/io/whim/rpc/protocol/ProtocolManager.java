package io.whim.rpc.protocol;


import io.whim.rpc.protocol.simple.SimpleProtocol;

import java.util.HashMap;
import java.util.Map;

import static io.whim.rpc.protocol.ProtocolType.SIMPLE;

public class ProtocolManager {


    private static Map<ProtocolType, Protocol> protocolMap = new HashMap<>();

    static {
        protocolMap.put(SIMPLE, new SimpleProtocol());
    }

    public static Protocol getProtocol(ProtocolType protocolType) {
        Protocol protocol = protocolMap.get(protocolType);
        if (protocol == null) {
            throw new IllegalStateException("Protocol not found. ProtocolType:" + protocolType);
        }
        return protocol;
    }
}
