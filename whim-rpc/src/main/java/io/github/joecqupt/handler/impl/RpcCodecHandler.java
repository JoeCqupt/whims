package io.github.joecqupt.handler.impl;

import io.github.joecqupt.channel.pipeline.ChannelContext;
import io.github.joecqupt.exception.NotEnoughException;
import io.github.joecqupt.handler.ChannelAdapterHandler;
import io.github.joecqupt.protocol.*;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import static io.github.joecqupt.protocol.Protocol.MASK_SIZE;

public class RpcCodecHandler implements ChannelAdapterHandler {


    private ByteBuffer bufferCollector = null;


    @Override
    public void channelRead(ChannelContext context, Object buf) {
        // 根据自定义协议格式，解析数据包
        ByteBuffer buffer = (ByteBuffer) buf;
        try {
            // 这一段突出的就是性能不高.....
            if (bufferCollector == null) {
                bufferCollector = ByteBuffer.allocate(buffer.remaining());
            } else {
                int newSize = bufferCollector.remaining() + buffer.remaining();
                ByteBuffer oldBufferCollector = bufferCollector;
                bufferCollector = ByteBuffer.allocate(newSize);
                bufferCollector.put(oldBufferCollector.array(), oldBufferCollector.position(), oldBufferCollector.limit());
            }
            bufferCollector.put(buffer.array(), buffer.position(), buffer.limit());

            DataPackage dataPackage;
            if (bufferCollector.remaining() >= MASK_SIZE) {
                buffer.mark();
                int mask = buffer.getInt();
                buffer.reset();
                ProtocolType protocolType = ProtocolType.valueOf(mask);
                Protocol protocol = ProtocolManger.getProtocol(protocolType);
                dataPackage = protocol.readData(buffer);
            } else {
                throw new NotEnoughException();
            }

            // 压缩bufferCollector内存
            int compactSize = bufferCollector.remaining();
            ByteBuffer oldBufferCollector = bufferCollector;
            bufferCollector = ByteBuffer.allocate(compactSize);
            bufferCollector.put(oldBufferCollector.array(), oldBufferCollector.position(), oldBufferCollector.limit());
            // 让后续处理这个数据包
            context.fireChannelRead(dataPackage);
        } catch (NotEnoughException nee) {
            // ignore
        }
    }

    @Override
    public void connect(SocketAddress address) {

    }

    @Override
    public void bind(SocketAddress address) {

    }

    @Override
    public void write(ChannelContext context, Object msg) {
        DataPackage dataPackage = (DataPackage) msg;
        ByteBuffer byteBuffer = dataPackage.toByteBuffer();
        context.write(byteBuffer);
    }
}
