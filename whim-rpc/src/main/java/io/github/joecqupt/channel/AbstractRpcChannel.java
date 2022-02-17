package io.github.joecqupt.channel;

import io.github.joecqupt.channel.pipeline.ChannelPipeline;

public abstract class AbstractRpcChannel implements RpcChannel {

    protected ChannelPipeline pipeline;

}
