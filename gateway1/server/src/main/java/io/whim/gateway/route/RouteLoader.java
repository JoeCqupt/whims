package io.whim.gateway.route;

import io.whim.gateway.filter.WebFilterChain;
import io.whim.gateway.filter.HttpClientRoutingFilter;
import io.whim.gateway.filter.WebFilter;
import io.whim.gateway.handler.FilteringWebHandler;
import io.whim.gateway.server.ServerWebExchange;
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
            public Publisher<Void> filter(WebFilterChain webFilterChain, ServerWebExchange serverWebExchange) {
                String reUrl = serverWebExchange.getRequest().param("reUrl");

                return webFilterChain.doFilter(serverWebExchange);
            }
        });
        webFilterList.add(new HttpClientRoutingFilter());

        httpServerRoutes.route(request -> request.path().equals("more")
                , new FilteringWebHandler(webFilterList)::handle);


    }

}
