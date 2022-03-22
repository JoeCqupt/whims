//package io.github.joecqupt.channel.pipeline;
//
//import io.github.joecqupt.channel.RpcChannel;
//
//import java.net.SocketAddress;
//
//public class HeadContext extends DefaultChannelContext implements ChannelContext {
//    public HeadContext(DefaultChannelPipeline pipeline) {
//        this.pipeline = pipeline;
//    }
//
//    @Override
//    public void channelRead(ChannelContext context, Object buf) {
//        context.fireChannelRead(buf);
//    }
//
//
//    @Override
//    public void connect(SocketAddress address) {
//
//    }
//
//    @Override
//    public void bind(SocketAddress address) {
//
//    }
//
//    @Override
//    public void write(ChannelContext context, Object msg) {
//        RpcChannel channel = context.pipeline().getChannel();
//        channel.write(msg);
//    }
//}
