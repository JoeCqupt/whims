package io.whim.gateway.handler;

import io.whim.gateway.filter.DefaultFilterChain;
import io.whim.gateway.filter.FilterChain;
import org.reactivestreams.Publisher;

public class FilteringWebHandler implements WebHandler {


    @Override
    public Publisher<Void> handle(ServerExchange serverExchange) {

        FilterChain filterChain = new DefaultFilterChain();
        return filterChain.doFilter(serverExchange);
    }
}
