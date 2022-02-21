package io.github.joecqupt.handler.impl;

import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.handler.ChannelAdapterHandler;
import io.github.joecqupt.invoke.Invoker;
import io.github.joecqupt.protocol.DataPackage;
import io.github.joecqupt.serialization.RpcRequest;

import java.net.SocketAddress;

public class RpcServerHandler implements ChannelAdapterHandler {
    @Override
    public void channelRead(ChannelContext context, Object buf) {
        DataPackage dataPackage = (DataPackage) buf;
        RpcRequest rpcRequest = dataPackage.deserialize();
        Invoker.invokeApi(rpcRequest, context.pipeline().getChannel());
    }

    @Override
    public void connect(SocketAddress address) {

    }

    @Override
    public void bind(SocketAddress address) {

    }

    @Override
    public void write(Object msg) {

    }
}
