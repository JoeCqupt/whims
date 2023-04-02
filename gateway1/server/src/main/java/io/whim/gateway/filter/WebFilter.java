package io.whim.gateway.filter;

import io.whim.gateway.handler.ServerExchange;
import org.reactivestreams.Publisher;

public interface WebFilter {

    Publisher<Void> filter(FilterChain filterChain, ServerExchange serverExchange);
}
