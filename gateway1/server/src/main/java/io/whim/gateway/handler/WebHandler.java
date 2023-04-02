package io.whim.gateway.handler;

import org.reactivestreams.Publisher;

public interface WebHandler {
    Publisher<Void> handle(ServerExchange serverExchange);
}
