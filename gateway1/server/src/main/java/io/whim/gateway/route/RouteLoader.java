package io.whim.gateway.route;

import io.whim.gateway.filter.DefaultFilterChain;
import io.whim.gateway.handler.FilteringWebHandler;
import reactor.netty.http.server.HttpServerRoutes;

public class RouteLoader {

    public static void load(HttpServerRoutes httpServerRoutes) {
        // from DB or ConfigCenter etc

        // example:
        // FIXME
        DefaultFilterChain filterChain = new DefaultFilterChain();

        httpServerRoutes.route(request -> request.path().equals("/test/path")
                , new FilteringWebHandler(filterChain)::handle);


    }

}
