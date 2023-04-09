package io.whim.gateway.filter;

import io.whim.gateway.server.ServerWebExchange;
import org.reactivestreams.Publisher;

public interface WebFilterChain {

    Publisher<Void> doFilter(ServerWebExchange serverWebExchange);
}
