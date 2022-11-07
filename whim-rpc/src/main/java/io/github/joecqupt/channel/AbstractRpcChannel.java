package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.channel.pipeline.DefaultChannelPipeline;
import io.github.joecqupt.eventloop.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
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

    protected Unsafe unsafe;

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    @Override
    public Unsafe unsafe() {
        return unsafe;
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
    public ChannelFuture bind(SocketAddress address) {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        this.bind(address, promise);
        return promise;
    }

    @Override
    public ChannelFuture bind(SocketAddress address, ChannelPromise promise) {
        pipeline.bind(address, promise);
        return promise;
    }

    @Override
    public ChannelFuture connect(SocketAddress address) {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        this.connect(address, promise);
        return promise;
    }

    @Override
    public ChannelFuture connect(SocketAddress address, ChannelPromise promise) {
        pipeline.connect(address, promise);
        return promise;
    }


    @Override
    public ChannelFuture disconnect() {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        this.disconnect(promise);
        return promise;
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise promise) {
        this.close(promise);
        return promise;
    }

    @Override
    public ChannelFuture close() {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        close(promise);
        return promise;
    }

    @Override
    public ChannelFuture close(ChannelPromise promise) {
        pipeline.close(promise);
        return promise;
    }

    @Override
    public ChannelFuture write(Object msg) {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        this.write(msg, promise);
        return promise;
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        pipeline.write(msg, promise);
        return promise;
    }

    @Override
    public ChannelFuture flush() {
        DefaultChannelPromise promise = new DefaultChannelPromise();
        this.flush(promise);
        return promise;
    }

    @Override
    public ChannelFuture flush(ChannelPromise promise) {
        pipeline.flush(promise);
        return promise;
    }

    protected abstract class AbstractUnsafe implements Unsafe {


        @Override
        public void close(ChannelPromise promise) {
            try {
                channel.close();
                promise.setSuccess(this);
            } catch (IOException e) {
                promise.setFailure(e);
            }
        }


    }
}
