package io.github.joecqupt.channel.pipeline;

import io.github.joecqupt.handler.ChannelAdapterHandler;

public interface ChannelContext extends ChannelAdapterHandler {

    void fireChannelRead(Object msg);

}
