package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.channel.pipeline.DefaultChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;


public abstract class AbstractRpcChannel implements RpcChannel {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRpcChannel.class);

    protected DefaultChannelPipeline pipeline;

    protected SelectableChannel channel;

    protected int interestedOps;

    protected SelectionKey key;

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    public AbstractRpcChannel(SelectableChannel channel, int interestedOps) {
        this.pipeline = new DefaultChannelPipeline(this);
        this.channel = channel;
        this.interestedOps = interestedOps;
        try {
            this.channel.configureBlocking(false);
        } catch (IOException e) {
            try {
                this.channel.close();
            } catch (IOException ex) {
                LOGGER.warn("Failed to close channel.", ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(EventLoop eventLoop) {
        Selector selector = eventLoop.getSelector();
        try {
            key = channel.register(selector, interestedOps, this);
        } catch (ClosedChannelException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        channel.close();
    }
}
