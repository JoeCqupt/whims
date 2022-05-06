package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.protocol.ProtocolManager;
import io.whim.rpc.protocol.ProtocolType;
import io.whim.rpc.service.invoke.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@ChannelHandler.Sharable
public class RpcResponseCodecHandler extends MessageToMessageCodec<DataPackage, RpcResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcResponseCodecHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse rpcResponse, List<Object> out) throws Exception {
        LOGGER.debug("[RpcResponseCodecHandler] encode invoke");
        ProtocolType protocolType = rpcResponse.getRpcMeta().getProtocolType();
        Protocol protocol = ProtocolManager.getProtocol(protocolType);
        DataPackage dataPackage = protocol.writeResponseData(rpcResponse);
        out.add(dataPackage);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DataPackage dataPackage, List<Object> out) throws Exception {
        LOGGER.debug("[RpcResponseCodecHandler] decode invoke");
        RpcResponse rpcResponse = dataPackage.deserializeResponse();
        out.add(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("[RpcResponseCodecHandler] ex", cause);
        ctx.fireExceptionCaught(cause);
    }
}
