package io.github.joecqupt.protocol;

import java.nio.ByteBuffer;

public interface Protocol {
    DataPackage readData(ByteBuffer buffer);
}
