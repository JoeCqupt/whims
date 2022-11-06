package io.github.joecqupt.handler.impl;

import io.github.joecqupt.channel.ChannelPromise;
import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.channel.handler.ChannelAdapterHandler;
import io.github.joecqupt.invoke.FutureStore;
import io.github.joecqupt.protocol.DataPackage;
import io.github.joecqupt.protocol.Protocol;
import io.github.joecqupt.protocol.ProtocolManger;
import io.github.joecqupt.protocol.ProtocolType;
import io.github.joecqupt.serialization.RpcRequest;
import io.github.joecqupt.serialization.RpcResponse;

public class RpcClientHandler extends ChannelAdapterHandler {
    @Override
    public void channelRead(ChannelContext ctx, Object buf) {
        DataPackage dataPackage = (DataPackage) buf;
        RpcResponse rpcResponse = dataPackage.deserializeResponse();
        FutureStore.notifyFuture(rpcResponse.getRpcMeta().getInvokeId(), rpcResponse.getResponse());
    }


    @Override
    public void write(ChannelContext context, Object msg, ChannelPromise promise) {
        // RpcRequest to DataPackage
        RpcRequest rpcRequest = (RpcRequest) msg;
        ProtocolType protocolType = rpcRequest.getRpcMeta().getProtocolType();
        Protocol protocol = ProtocolManger.getProtocol(protocolType);
        DataPackage dataPackage = protocol.writeRequestData(rpcRequest);
        context.write(dataPackage, promise);
    }
}
