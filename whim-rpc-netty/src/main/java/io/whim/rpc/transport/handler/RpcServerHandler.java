package io.whim.rpc.transport.handler;

import io.netty.channel.*;
import io.whim.rpc.service.LocalServiceManager;
import io.whim.rpc.service.api.ApiInfo;
import io.whim.rpc.service.invoke.RpcMeta;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static io.whim.rpc.common.Constants.*;

@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

    private static final ChannelFutureListener LOGGING_LISTENER = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            boolean success = future.isSuccess();
            if (!success) {
                LOGGER.warn("write response fail", future.cause());
            }
        }
    };

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.debug("[RpcServerHandler] read invoke");
        RpcRequest rpcRequest = (RpcRequest) msg;
        try {
            RpcMeta meta = rpcRequest.getMeta();
            meta.setStatus(STATUS_SUCCESS);
            String apiKey = meta.getApiKey();
            ApiInfo apiInfo = LocalServiceManager.getApiInfo(apiKey);
            Method method = apiInfo.getMethod();
            Object service = apiInfo.getService();
            Object result = method.invoke(service, rpcRequest.getRequest());

            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setRpcMeta(meta);
            rpcResponse.setResponse(result);

            ctx.writeAndFlush(rpcResponse).addListener(LOGGING_LISTENER);
        } catch (Exception e) {
            LOGGER.error("RpcServerHandler handle ex", e);
            RpcResponse rpcResponse = new RpcResponse();
            RpcMeta meta = rpcRequest.getMeta();
            meta.setStatus(STATUS_FAIL);
            rpcResponse.setRpcMeta(meta);
            rpcResponse.setResponse(e.getCause().toString());
            ctx.writeAndFlush(rpcResponse).addListener(LOGGING_LISTENER);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("[RpcServerHandler] ex", cause);
    }
}
