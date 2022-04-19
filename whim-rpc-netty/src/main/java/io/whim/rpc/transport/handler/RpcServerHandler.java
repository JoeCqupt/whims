package io.whim.rpc.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.whim.rpc.service.ServiceManager;
import io.whim.rpc.service.api.ApiInfo;
import io.whim.rpc.service.invoke.RpcMeta;
import io.whim.rpc.service.invoke.RpcRequest;
import io.whim.rpc.service.invoke.RpcResponse;

import java.lang.reflect.Method;

public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;
        RpcMeta meta = rpcRequest.getMeta();
        String apiKey = meta.getApiKey();
        ApiInfo apiInfo = ServiceManager.getApiInfo(apiKey);
        Method method = apiInfo.getMethod();
        Object service = apiInfo.getService();
        Object result = method.invoke(service, rpcRequest.getRequest());

        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRpcMeta(meta);
        rpcResponse.setResponse(result);

        ctx.write(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // todo
    }
}
