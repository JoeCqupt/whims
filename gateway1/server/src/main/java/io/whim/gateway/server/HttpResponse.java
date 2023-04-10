package io.whim.gateway.server;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.cookie.Cookie;
import org.reactivestreams.Publisher;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.server.HttpServerResponse;

import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private HttpServerResponse response;

    private HttpResponseStatus httpResponseStatus;

    private HttpHeaders headers;

    private Map<String, Set<Cookie>> cookies;

    public HttpResponse(HttpServerResponse response) {
        this.response = response;
    }

    public HttpResponseStatus getHttpResponseStatus() {
        return httpResponseStatus;
    }

    public void setHttpResponseStatus(HttpResponseStatus httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Map<String, Set<Cookie>> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, Set<Cookie>> cookies) {
        this.cookies = cookies;
    }

    public Publisher<Void> send(ByteBufFlux byteBufFlux) {
       return this.response.send(byteBufFlux);
    }
}
