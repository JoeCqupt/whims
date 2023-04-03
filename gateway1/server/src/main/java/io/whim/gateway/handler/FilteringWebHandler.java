package io.whim.gateway.handler;

import io.whim.gateway.filter.FilterChain;
import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public class FilteringWebHandler implements WebHandler {

    private FilterChain filterChain;

    public FilteringWebHandler(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    @Override
    public Publisher<Void> handle(HttpServerRequest request, HttpServerResponse response){

        ServerExchange serverExchange = new ServerExchange(request, response);
        return filterChain.doFilter(serverExchange);
    }
}
