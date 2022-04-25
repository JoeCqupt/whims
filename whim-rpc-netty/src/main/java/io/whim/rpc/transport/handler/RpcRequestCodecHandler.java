package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.protocol.ProtocolManager;
import io.whim.rpc.protocol.ProtocolType;
import io.whim.rpc.service.invoke.RpcRequest;

import java.util.List;

@ChannelHandler.Sharable
public class RpcRequestCodecHandler extends MessageToMessageCodec<DataPackage, RpcRequest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest rpcRequest, List<Object> out) throws Exception {
        ProtocolType protocolType = rpcRequest.getMeta().getProtocolType();
        Protocol protocol = ProtocolManager.getProtocol(protocolType);
        DataPackage dataPackage = protocol.writeRequestData(rpcRequest);
        out.add(dataPackage);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DataPackage dataPackage, List<Object> out) throws Exception {
        RpcRequest rpcRequest = dataPackage.deserializeRequest();
        out.add(rpcRequest);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // todo
    }
}
