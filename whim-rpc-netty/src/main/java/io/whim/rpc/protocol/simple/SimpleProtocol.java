package io.whim.rpc.protocol.simple;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.serialize.SerializeType;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;
import io.whim.rpc.protocol.Protocol;

public class SimpleProtocol implements Protocol {


    public static final int SIMPLE_PROTOCOL_MASK = 9527;

    /**
     * simple 协议默认绑定的序列化类型是 JSON
     */
    public static final SerializeType SERIALIZE_TYPE = SerializeType.JSON;


    // todo
    @Override
    public int getProtocolMask() {
        return SIMPLE_PROTOCOL_MASK;
    }

    @Override
    public DataPackage decodeRequestData(ByteBuf data) {
        return null;
    }

    @Override
    public DataPackage encodeRequestData(RpcRequest request) {
        return null;
    }

    @Override
    public DataPackage decodeResponseData(ByteBuf data) {
        return null;
    }

    @Override
    public DataPackage encodeResponseData(RpcResponse response) {
        return null;
    }
}
