package io.whim.gateway.handler;

import io.whim.gateway.filter.DefaultFilterChain;
import io.whim.gateway.filter.FilterChain;
import io.whim.gateway.filter.WebFilter;
import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.util.List;

public class FilteringWebHandler implements WebHandler {

    private List<WebFilter> webFilterList;

    public FilteringWebHandler(List<WebFilter> webFilterList) {
        this.webFilterList = webFilterList;
    }

    @Override
    public Publisher<Void> handle(HttpServerRequest request, HttpServerResponse response) {
        FilterChain filterChain = new DefaultFilterChain(webFilterList);
        ServerExchange serverExchange = new ServerExchange(request, response);
        return filterChain.doFilter(serverExchange);
    }
}
