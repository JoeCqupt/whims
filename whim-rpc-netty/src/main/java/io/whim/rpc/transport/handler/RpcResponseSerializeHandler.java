package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.whim.rpc.common.RpcContextHolder;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.protocol.ProtocolManager;
import io.whim.rpc.protocol.ProtocolType;
import io.whim.rpc.service.invoke.RpcContext;
import io.whim.rpc.service.invoke.RpcResponse;

import java.util.List;

public class RpcResponseSerializeHandler extends MessageToMessageCodec<DataPackage, RpcResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse rpcResponse, List<Object> out) throws Exception {
        String invokeId = rpcResponse.getRpcMeta().getInvokeId();
        RpcContext rpcContext = RpcContextHolder.removeRpcContext(invokeId);
        ProtocolType protocolType = rpcContext.getProtocolType();
        Protocol protocol = ProtocolManager.getProtocol(protocolType);
        DataPackage dataPackage = protocol.writeResponseData(rpcResponse);
        out.add(dataPackage);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DataPackage dataPackage, List<Object> out) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // todo
    }
}
