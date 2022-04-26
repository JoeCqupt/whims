package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.Attribute;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.protocol.ProtocolManager;
import io.whim.rpc.protocol.ProtocolType;
import io.whim.rpc.service.invoke.RpcMeta;
import io.whim.rpc.service.invoke.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.whim.rpc.common.Constants.RPC_META_ATTRIBUTE_KEY;

@ChannelHandler.Sharable
public class RpcRequestCodecHandler extends MessageToMessageCodec<DataPackage, RpcRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcRequestCodecHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest rpcRequest, List<Object> out) throws Exception {
        LOGGER.debug("[RpcRequestCodecHandler] encode invoke");
        ProtocolType protocolType = rpcRequest.getMeta().getProtocolType();
        Protocol protocol = ProtocolManager.getProtocol(protocolType);
        DataPackage dataPackage = protocol.writeRequestData(rpcRequest);
        out.add(dataPackage);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DataPackage dataPackage, List<Object> out) throws Exception {
        LOGGER.debug("[RpcRequestCodecHandler] decode invoke");
        RpcRequest rpcRequest = dataPackage.deserializeRequest();

        // 设置attr
        Attribute<RpcMeta> attr = ctx.channel().attr(RPC_META_ATTRIBUTE_KEY);
        attr.set(rpcRequest.getMeta());

        out.add(rpcRequest);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("[RpcRequestCodecHandler] ex", cause);
        ctx.fireExceptionCaught(cause);
    }
}
