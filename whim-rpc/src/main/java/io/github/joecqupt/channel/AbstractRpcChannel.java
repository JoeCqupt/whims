package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelPipeline;
import io.github.joecqupt.channel.pipeline.DefaultChannelPipeline;

public abstract class AbstractRpcChannel implements RpcChannel {

    protected DefaultChannelPipeline pipeline;

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }
}
