package io.whim.gateway.handler;

import io.whim.gateway.filter.DefaultWebFilterChain;
import io.whim.gateway.filter.WebFilterChain;
import io.whim.gateway.filter.WebFilter;
import io.whim.gateway.server.ServerWebExchange;
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
        WebFilterChain webFilterChain = new DefaultWebFilterChain(webFilterList);

        ServerWebExchange serverWebExchange = new ServerWebExchange(request, response);
        return webFilterChain.doFilter(serverWebExchange);
    }
}
