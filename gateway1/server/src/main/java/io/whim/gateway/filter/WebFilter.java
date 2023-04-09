package io.whim.gateway.filter;

import io.whim.gateway.server.ServerWebExchange;
import org.reactivestreams.Publisher;

public interface WebFilter {

    Publisher<Void> filter(WebFilterChain webFilterChain, ServerWebExchange serverWebExchange);
}
