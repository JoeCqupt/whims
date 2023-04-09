package io.whim.gateway.server;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.ReadOnlyHttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import reactor.netty.http.server.HttpServerRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
    private HttpServerRequest nativeRequest;


    private String path;

    private HttpHeaders headers;

    private Map<String, String> queryParams;

    private Map<String, Set<Cookie>> cookies;

    public HttpRequest(HttpServerRequest nativeRequest) {
        this.nativeRequest = nativeRequest;
        this.path = nativeRequest.path();

        HttpHeaders requestHeaders = nativeRequest.requestHeaders();
        if (requestHeaders instanceof ReadOnlyHttpHeaders) {
            this.headers = new DefaultHttpHeaders();
            this.headers.set(requestHeaders);
        } else {
            this.headers = requestHeaders;
        }

        this.queryParams = new HashMap<>(nativeRequest.params());
        this.cookies = new HashMap<>();
        nativeRequest.cookies().forEach((k, set) -> this.cookies.put(k.toString(), set));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, Set<Cookie>> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, Set<Cookie>> cookies) {
        this.cookies = cookies;
    }
}
