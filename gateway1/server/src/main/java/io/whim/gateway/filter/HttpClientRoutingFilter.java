package io.whim.gateway.filter;

import io.whim.gateway.handler.ServerExchange;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

/**
 * a simple http routing filter
 */
public class HttpClientRoutingFilter implements WebFilter {
    @Override
    public Publisher<Void> filter(FilterChain filterChain, ServerExchange serverExchange) {
        HttpServerRequest request = serverExchange.getRequest();
        HttpServerResponse response = serverExchange.getResponse();

        ByteBufFlux byteBufFlux = HttpClient.create()
                .request(request.method())
                .uri(request.uri())
                .send(Mono.empty())
                .responseContent();

        return response.send(byteBufFlux);
    }
}
