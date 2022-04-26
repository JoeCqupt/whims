package io.whim.rpc.transport.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.whim.rpc.exception.NotEnoughException;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.protocol.ProtocolManager;
import io.whim.rpc.protocol.ProtocolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RpcCodecHandler extends ByteToMessageCodec<DataPackage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcCodecHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, DataPackage dataPackage, ByteBuf out) throws Exception {
        LOGGER.debug("[RpcCodecHandler] encode invoke");
        dataPackage.writeData(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        LOGGER.debug("[RpcCodecHandler] decode invoke");
        // 解包
        if (in.readableBytes() < Protocol.PROTOCOL_MASK_SIZE) {
            // 不足以读取协议信息
            return;
        }
        int protocolCode = in.getInt(in.readerIndex());
        ProtocolType protocolType = ProtocolType.valueOf(protocolCode);
        Protocol protocol = ProtocolManager.getProtocol(protocolType);
        try {
            DataPackage dataPackage = protocol.readData(in);
            out.add(dataPackage);
        } catch (NotEnoughException e) {
            // ignore
            return;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("[RpcCodecHandler] ex", cause);
        ctx.fireExceptionCaught(cause);
    }
}
