package io.github.joecqupt.handler.impl;

import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.handler.ChannelInboundHandler;

import java.nio.ByteBuffer;

public class RpcCodecHandler implements ChannelInboundHandler {

    @Override
    public void channelRead(ChannelContext context, Object buf) {
        // 根据自定义协议格式，解析数据包
        ByteBuffer buffer = (ByteBuffer) buf;
    }
}
