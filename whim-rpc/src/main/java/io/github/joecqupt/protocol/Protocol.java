package io.github.joecqupt.protocol;

import java.nio.ByteBuffer;

public interface Protocol {
    int MASK_SIZE = 4;

    DataPackage readData(ByteBuffer buffer);
}
