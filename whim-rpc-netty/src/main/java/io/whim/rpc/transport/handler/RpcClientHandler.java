package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.whim.rpc.service.invoke.RpcFutureStore;
import io.whim.rpc.service.invoke.RpcMeta;
import io.whim.rpc.service.invoke.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.whim.rpc.common.Constants.*;

@ChannelHandler.Sharable
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.debug("[RpcClientHandler] read invoke");

        RpcResponse rpcResponse = (RpcResponse) msg;
        RpcFutureStore.callback(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("[RpcClientHandler] ex", cause);
        ctx.fireExceptionCaught(cause);
    }
}
