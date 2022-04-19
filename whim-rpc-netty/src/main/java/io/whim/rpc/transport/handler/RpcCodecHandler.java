package io.whim.rpc.transport.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.whim.rpc.exception.NotEnoughException;
import io.whim.rpc.protocol.DataPackage;
import io.whim.rpc.protocol.Protocol;
import io.whim.rpc.protocol.ProtocolManager;
import io.whim.rpc.protocol.ProtocolType;

import java.util.List;

public class RpcCodecHandler extends ByteToMessageCodec<DataPackage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, DataPackage dataPackage, ByteBuf out) throws Exception {
        // todo
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
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
        // todo
    }
}
