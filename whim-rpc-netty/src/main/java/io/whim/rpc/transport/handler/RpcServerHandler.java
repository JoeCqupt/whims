package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.whim.rpc.service.invoke.RpcRequest;

public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;

        // todo
    }
}
