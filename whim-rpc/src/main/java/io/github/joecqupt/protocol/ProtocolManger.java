package io.github.joecqupt.protocol;

import io.github.joecqupt.protocol.simple.SimpleProtocol;

import java.util.HashMap;
import java.util.Map;


public class ProtocolManger {

    private static Map<ProtocolType, Protocol> map = new HashMap<>();

    static {
        map.put(ProtocolType.SIMPLE, new SimpleProtocol());
    }

    public static Protocol getProtocol(ProtocolType protocolType) {
        return map.get(protocolType);
    }

}
