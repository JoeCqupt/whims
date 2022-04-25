//package io.github.joecqupt.channel.pipeline;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.SocketAddress;
//
//public class TailContext extends DefaultChannelContext implements ChannelContext {
//    private static final Logger LOG = LoggerFactory.getLogger(TailContext.class);
//
//    public TailContext(DefaultChannelPipeline pipeline) {
//        this.pipeline = pipeline;
//    }
//
//    @Override
//    public void channelRead(ChannelContext context, Object buf) {
//        // 丢弃
//        LOG.warn("TailContext channelRead discard buf");
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
//        context.write(msg);
//    }
//}
