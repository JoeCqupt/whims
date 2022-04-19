package io.whim.rpc.protocol.simple;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.exception.NotEnoughException;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.serialize.SerializeType;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.service.invoke.RpcResponse;

public class SimpleProtocol implements Protocol {


    public static final int SIMPLE_PROTOCOL_MASK = 9527;

    /**
     * simple 协议默认绑定的序列化类型是 JSON
     */
    public static final SerializeType SERIALIZE_TYPE = SerializeType.JSON;


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

        ByteBuf byteBuf = data.readSlice(packageSize);
        byteBuf.readInt();
        byteBuf.readInt();
        int headerSize = byteBuf.readInt();
        ByteBuf headerData = byteBuf.readBytes(headerSize);
        int bodySize = byteBuf.readInt();
        ByteBuf bodyData = byteBuf.readBytes(bodySize);

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
