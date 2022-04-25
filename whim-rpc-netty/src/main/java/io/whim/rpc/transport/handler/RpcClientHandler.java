package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.whim.rpc.service.invoke.RpcFutureStore;
import io.whim.rpc.service.invoke.RpcResponse;

@ChannelHandler.Sharable
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse rpcResponse = (RpcResponse) msg;
        RpcFutureStore.callback(rpcResponse);
    }
}
