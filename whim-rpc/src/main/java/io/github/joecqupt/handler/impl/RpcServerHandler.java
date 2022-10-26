package io.github.joecqupt.handler.impl;

import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.channel.handler.ChannelAdapterHandler;
import io.github.joecqupt.invoke.Invoker;
import io.github.joecqupt.protocol.DataPackage;
import io.github.joecqupt.protocol.Protocol;
import io.github.joecqupt.protocol.ProtocolManger;
import io.github.joecqupt.serialization.RpcMeta;
import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;


public class RpcServerHandler extends ChannelAdapterHandler {
    @Override
    public void channelRead(ChannelContext ctx, Object buf) {
        DataPackage dataPackage = (DataPackage) buf;
        RpcRequest rpcRequest = dataPackage.deserializeRequest();
        Invoker.invokeApi(rpcRequest, ctx.pipeline().getChannel());
    }


    @Override
    public void write(ChannelContext ctx, Object msg) {
        RpcResponse rpcResponse = (RpcResponse) msg;
        RpcMeta rpcMeta = rpcResponse.getRpcMeta();
        Protocol protocol = ProtocolManger.getProtocol(rpcMeta.getProtocolType());
        DataPackage dataPackage = protocol.writeResponseData(rpcResponse);
        ctx.write(dataPackage);
    }


}
