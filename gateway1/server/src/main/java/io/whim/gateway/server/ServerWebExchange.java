package io.whim.gateway.server;

import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public class ServerWebExchange {
    private HttpServerRequest request;
    private HttpServerResponse response;

    public ServerWebExchange(HttpServerRequest request, HttpServerResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServerRequest getRequest() {
        return request;
    }

    public HttpServerResponse getResponse() {
        return response;
    }
}
