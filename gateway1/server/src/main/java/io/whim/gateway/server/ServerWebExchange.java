package io.whim.gateway.server;

import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public class ServerWebExchange {
    private HttpRequest request;
    private HttpResponse response;

    public ServerWebExchange(HttpServerRequest request, HttpServerResponse response) {
        this.request = new HttpRequest(request);
        this.response = new HttpResponse(response);
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpResponse getResponse() {
        return response;
    }
}
