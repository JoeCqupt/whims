package io.whim.gateway.filter;

import io.whim.gateway.handler.ServerExchange;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class DefaultFilterChain implements FilterChain {

    // TODO: init
    private List<WebFilter> webFilterList = new ArrayList<>();
    private int idx = 0;

    @Override
    public Publisher<Void> doFilter(ServerExchange serverExchange) {
        if(idx>=webFilterList.size()){
            return Mono.empty();
        }
        WebFilter webFilter = webFilterList.get(idx);
        idx++;
        return webFilter.filter( this, serverExchange);
    }
}
