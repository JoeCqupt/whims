package io.whim.rpc.protocol.simple;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.serialize.SerializeType;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;
import io.whim.rpc.protocol.Protocol;

public class SimpleProtocol implements Protocol {


    public static final int SIMPLE_PROTOCOL_MASK = 9527;

    /**
     * simple 协议绑定的序列化类型是 JSON
     */
    public static final SerializeType SERIALIZE_TYPE = SerializeType.JSON;


    // todo
    @Override
    public int getProtocolMask() {
        return SIMPLE_PROTOCOL_MASK;
    }

    @Override
    public RpcRequest decodeRequest(ByteBuf data) {
        return null;
    }

    @Override
    public ByteBuf encodeRequest(RpcRequest request) {
        return null;
    }

    @Override
    public RpcResponse decodeResponse(ByteBuf data) {
        return null;
    }

    @Override
    public ByteBuf encodeResponse(RpcResponse response) {
        return null;
    }
}
