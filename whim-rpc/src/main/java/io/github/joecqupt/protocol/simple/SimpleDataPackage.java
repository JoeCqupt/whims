package io.github.joecqupt.protocol.simple;

import io.github.joecqupt.protocol.DataPackage;

/**
 * the simple protocol data package desc:
 * <p>
 * |-9527-|-packageSize-|-headerSize-|---header-data---|-bodySize-|---body-data--|
 */
public class SimpleDataPackage implements DataPackage {

    private int mask = 9527;

    private int packageSize;

    private int headerSize;
    private byte[] header;

    private int bodySize;
    private byte[] body;
}
