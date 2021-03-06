package io.whim.rpc.protocol;

import io.netty.buffer.ByteBuf;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;

public interface Protocol {

    /**
     * 用于区分协议的魔数 长度
     */
    int PROTOCOL_MASK_SIZE = 4;


    DataPackage readData(ByteBuf data);

    DataPackage writeRequestData(RpcRequest request);

    DataPackage writeResponseData(RpcResponse response);

}
