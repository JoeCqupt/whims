package io.whim.rpc.protocol.simple;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.exception.NotEnoughException;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.service.invoke.RpcResponse;

public class SimpleProtocol implements Protocol {


    @Override
    public DataPackage readData(ByteBuf data) {
        int min = Protocol.PROTOCOL_MASK_SIZE + SimpleDataPackage.PACKAGE_SIZE_LENGTH;
        if (data.readableBytes() < min) {
            throw new NotEnoughException();
        }
        int packageSize = data.getInt(data.readerIndex() + Protocol.PROTOCOL_MASK_SIZE);
        if (data.readableBytes() < packageSize) {
            throw new NotEnoughException();
        }
        SimpleDataPackage simpleDataPackage = new SimpleDataPackage();
        simpleDataPackage.setPackageSize(packageSize);

        data.readInt(); // read mask
        data.readInt(); // read package size
        int headerSize = data.readInt(); // read header size
        byte[] headerData = new byte[headerSize];
        data.readBytes(headerData);

        int bodySize = data.readInt(); // read body size
        byte[] bodyData = new byte[bodySize];
        data.readBytes(bodyData);

        simpleDataPackage.setHeaderSize(headerSize);
        simpleDataPackage.setHeaderData(headerData);
        simpleDataPackage.setBodySize(bodySize);
        simpleDataPackage.setBodyData(bodyData);

        return simpleDataPackage;
    }

    @Override
    public DataPackage writeRequestData(RpcRequest request) {
        SimpleDataPackage dataPackage = new SimpleDataPackage();
        dataPackage.serialize(request);
        return dataPackage;
    }

    @Override
    public DataPackage writeResponseData(RpcResponse response) {
        SimpleDataPackage dataPackage = new SimpleDataPackage();
        dataPackage.serialize(response);
        return dataPackage;
    }

}
