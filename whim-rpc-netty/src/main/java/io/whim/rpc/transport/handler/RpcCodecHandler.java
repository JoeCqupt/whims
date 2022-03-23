package io.whim.rpc.transport.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.whim.rpc.protocol.DataPackage;

import java.util.List;

public class RpcCodecHandler extends ByteToMessageCodec<DataPackage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, DataPackage msg, ByteBuf out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
