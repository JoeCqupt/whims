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

import static io.whim.rpc.common.Constants.RPC_META_ATTRIBUTE_KEY;

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

        boolean b = ctx.channel().hasAttr(RPC_META_ATTRIBUTE_KEY);
        if (b) {
            Attribute<RpcMeta> attr = ctx.channel().attr(RPC_META_ATTRIBUTE_KEY);
            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setRpcMeta(attr.get());
            // todo
            rpcResponse.setResponse(cause);
            RpcFutureStore.callback(rpcResponse);
        } else {
            ctx.fireExceptionCaught(cause);
        }
    }
}
