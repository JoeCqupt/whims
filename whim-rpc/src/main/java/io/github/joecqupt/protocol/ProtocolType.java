package io.github.joecqupt.protocol;

import io.github.joecqupt.protocol.simple.SimpleDataPackage;

public enum ProtocolType {
    SIMPLE;


    public static ProtocolType valueOf(Object mask) {
        if (mask == SimpleDataPackage.mask) {
            return SIMPLE;
        }
        throw new IllegalStateException("not support this protocol mask");
    }
}
