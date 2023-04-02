package io.whim.gateway.filter;

import io.whim.gateway.handler.ServerExchange;
import org.reactivestreams.Publisher;

public interface FilterChain {

    Publisher<Void> doFilter(ServerExchange serverExchange);
}
