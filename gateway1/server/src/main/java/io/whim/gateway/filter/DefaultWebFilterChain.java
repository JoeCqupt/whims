package io.whim.gateway.filter;

import io.whim.gateway.server.ServerWebExchange;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;

public class DefaultWebFilterChain implements WebFilterChain {

    private List<WebFilter> webFilterList;
    private int idx = 0;

    public DefaultWebFilterChain(List<WebFilter> webFilterList) {
        this.webFilterList = webFilterList;
    }

    @Override
    public Publisher<Void> doFilter(ServerWebExchange serverWebExchange) {
        if (idx >= webFilterList.size()) {
            return Mono.empty();
        }
        WebFilter webFilter = webFilterList.get(idx);
        idx++;
        return webFilter.filter(this, serverWebExchange);
    }
}
