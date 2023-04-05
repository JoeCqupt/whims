package io.whim.gateway.route;

import io.whim.gateway.filter.FilterChain;
import io.whim.gateway.filter.HttpClientRoutingFilter;
import io.whim.gateway.filter.WebFilter;
import io.whim.gateway.handler.FilteringWebHandler;
import io.whim.gateway.handler.ServerExchange;
import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.ArrayList;
import java.util.List;

public class RouteLoader {

    public static void load(HttpServerRoutes httpServerRoutes) {
        // from DB or ConfigCenter etc

        // example:
        // FIXME
        List<WebFilter> webFilterList = new ArrayList<>();
        webFilterList.add(new WebFilter() {
            @Override
            public Publisher<Void> filter(FilterChain filterChain, ServerExchange serverExchange) {
                String reUrl = serverExchange.getRequest().param("reUrl");

                return filterChain.doFilter(serverExchange);
            }
        });
        webFilterList.add(new HttpClientRoutingFilter());

        httpServerRoutes.route(request -> request.path().equals("more")
                , new FilteringWebHandler(webFilterList)::handle);


    }

}
