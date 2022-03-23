package io.whim.rpc.protocol;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;

public interface Protocol {

    /**
     * 用于区分协议的魔数
     */
    int PROTOCOL_MASK_SIZE = 4;

    int getProtocolMask();

    RpcRequest decodeRequest(ByteBuf data);

    ByteBuf encodeRequest(RpcRequest request);

    RpcResponse decodeResponse(ByteBuf data);

    ByteBuf encodeResponse(RpcResponse response);
}
