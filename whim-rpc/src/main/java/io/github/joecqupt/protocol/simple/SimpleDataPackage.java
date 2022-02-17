package io.github.joecqupt.protocol.simple;

import io.github.joecqupt.protocol.DataPackage;

import static io.github.joecqupt.protocol.Protocol.MASK_SIZE;

/**
 * the simple protocol data package desc:
 * <p>
 * |-9527(int)-|-packageSize(int)-|-headerSize(int)-|---header-data(byte[])---|-bodySize(int)-|---body-data(byte[])--|
 */
public class SimpleDataPackage implements DataPackage {

    public final static int MASK = 9527;

    private int packageSize;

    private int headerSize;
    private byte[] header;

    private int bodySize;
    private byte[] body;


    @Override
    public int totalSize() {
        return MASK_SIZE + packageSize;
    }

    public int getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(int packageSize) {
        this.packageSize = packageSize;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public int getBodySize() {
        return bodySize;
    }

    public void setBodySize(int bodySize) {
        this.bodySize = bodySize;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
