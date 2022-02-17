package io.github.joecqupt.protocol.simple;

import io.github.joecqupt.exception.NotEnoughException;
import io.github.joecqupt.protocol.DataPackage;
import io.github.joecqupt.protocol.Protocol;

import java.nio.ByteBuffer;

/**
 * custom protocol
 */
public class SimpleProtocol implements Protocol {

    private static int PACKAGE_SIZE = 4;

    @Override
    public DataPackage readData(ByteBuffer buffer) {
        /**
         * the simple protocol data package desc:
         * |-9527(int)-|-packageSize(int)-|-headerSize(int)-|---header-data(byte[])---|-bodySize(int)-|---body-data(byte[])--|
         */

        if (buffer.remaining() >= MASK_SIZE + PACKAGE_SIZE) {
            buffer.mark();
            int mask = buffer.getInt();
            int packageSize = buffer.getInt();
            if (buffer.remaining() >= packageSize) {
                // 读取package内容
                // TODO
                SimpleDataPackage dataPackage = new SimpleDataPackage();
                dataPackage.setPackageSize(packageSize);
                int headerSize = buffer.getInt();
                dataPackage.setHeaderSize(headerSize);
                byte[] header = new byte[headerSize];
                buffer.get(header);
                dataPackage.setHeader(header);

                int bodySize = buffer.getInt();
                dataPackage.setBodySize(bodySize);
                byte[] body = new byte[bodySize];
                buffer.get(body);
                dataPackage.setBody(body);

                return dataPackage;
            } else {
                buffer.reset();
                throw new NotEnoughException();
            }
        } else {
            throw new NotEnoughException();
        }
    }
}
