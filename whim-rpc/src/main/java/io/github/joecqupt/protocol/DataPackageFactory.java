package io.github.joecqupt.protocol;

import io.github.joecqupt.protocol.simple.SimpleDataPackage;

public class DataPackageFactory {

    public static DataPackage newInstance(ProtocolType protocolType) {
        if (ProtocolType.SIMPLE == protocolType) {
            return new SimpleDataPackage();
        }
        return new SimpleDataPackage();
    }
}
