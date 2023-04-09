package io.whim.gateway.filter;

import io.whim.gateway.annotations.ShareSafe;
import io.whim.gateway.server.ServerWebExchange;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

/**
 * a simple http routing filter
 */
@ShareSafe
public class HttpClientRoutingFilter implements WebFilter {
    @Override
    public Publisher<Void> filter(WebFilterChain webFilterChain, ServerWebExchange serverWebExchange) {
        HttpServerRequest request = serverWebExchange.getRequest();
        HttpServerResponse response = serverWebExchange.getResponse();

        ByteBufFlux byteBufFlux = HttpClient.create()
                .request(request.method())
                .uri(request.uri())
                .send(Mono.empty())
                .responseContent();

        return response.send(byteBufFlux);
    }
}
