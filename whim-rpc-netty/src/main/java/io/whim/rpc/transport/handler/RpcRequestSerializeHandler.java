package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.service.invoke.RpcRequest;

import java.util.List;

public class RpcRequestSerializeHandler extends MessageToMessageCodec<DataPackage, RpcRequest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest rpcRequest, List<Object> out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DataPackage dataPackage, List<Object> out) throws Exception {
        RpcRequest rpcRequest = dataPackage.deserializeRequest();
        out.add(rpcRequest);
    }
}
