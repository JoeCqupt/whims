package io.github.joecqupt.handler.impl;

import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.handler.ChannelAdapterHandler;
import io.github.joecqupt.protocol.DataPackage;
import io.github.joecqupt.protocol.Protocol;
import io.github.joecqupt.protocol.ProtocolManger;
import io.github.joecqupt.protocol.ProtocolType;
import io.github.joecqupt.serialization.RpcRequest;

import java.net.SocketAddress;

public class RpcClientHandler implements ChannelAdapterHandler {
    @Override
    public void channelRead(ChannelContext context, Object buf) {

    }

    @Override
    public void connect(SocketAddress address) {

    }

    @Override
    public void bind(SocketAddress address) {

    }

    @Override
    public void write(ChannelContext context, Object msg) {
        // RpcRequest to DataPackage
        RpcRequest rpcRequest = (RpcRequest) msg;
        ProtocolType protocolType = rpcRequest.getRpcMeta().getProtocolType();
        Protocol protocol = ProtocolManger.getProtocol(protocolType);
        DataPackage dataPackage = protocol.writeData(rpcRequest);
        context.write(dataPackage);
    }
}
