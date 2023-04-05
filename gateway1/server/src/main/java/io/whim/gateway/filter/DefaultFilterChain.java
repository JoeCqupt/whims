package io.whim.gateway.filter;

import io.whim.gateway.handler.ServerExchange;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;

public class DefaultFilterChain implements FilterChain {

    private List<WebFilter> webFilterList;
    private int idx = 0;

    public DefaultFilterChain(List<WebFilter> webFilterList) {
        this.webFilterList = webFilterList;
    }

    @Override
    public Publisher<Void> doFilter(ServerExchange serverExchange) {
        if (idx >= webFilterList.size()) {
            return Mono.empty();
        }
        WebFilter webFilter = webFilterList.get(idx);
        idx++;
        return webFilter.filter(this, serverExchange);
    }
}
