package io.whim.gateway.filter;

import io.whim.gateway.annotations.ShareSafe;
import io.whim.gateway.server.HttpRequest;
import io.whim.gateway.server.HttpResponse;
import io.whim.gateway.server.ServerWebExchange;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

/**
 * a simple http routing filter
 */
@ShareSafe
public class HttpClientRoutingFilter implements WebFilter {
    @Override
    public Publisher<Void> filter(WebFilterChain webFilterChain, ServerWebExchange serverWebExchange) {
        HttpRequest request = serverWebExchange.getRequest();
        HttpResponse response = serverWebExchange.getResponse();

        // FIXME
        ByteBufFlux byteBufFlux = HttpClient.create()
                .request(request.getMethod())
                .uri(request.getUri())
                .send(Mono.empty())
                .responseContent();

        return response.send(byteBufFlux);
    }
}
