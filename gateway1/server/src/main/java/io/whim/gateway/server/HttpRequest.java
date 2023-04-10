package io.whim.gateway.server;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.ReadOnlyHttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import reactor.netty.http.server.HttpServerRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest {

    private HttpServerRequest request;

    private String path;

    private HttpHeaders headers;

    private Map<String, String> queryParams;

    private Map<String, Set<Cookie>> cookies;

    private String uri;

    private HttpMethod method;

    public HttpRequest(HttpServerRequest request) {
        this.request = request;
        this.path = request.path();
        this.uri = request.uri();
        this.method = request.method();
    }

    private void initQueryParams() {
        this.queryParams = new HashMap<>(request.params());
    }

    private void initHeaders() {
        HttpHeaders requestHeaders = request.requestHeaders();
        if (requestHeaders instanceof ReadOnlyHttpHeaders) {
            this.headers = new DefaultHttpHeaders();
            this.headers.set(requestHeaders);
        } else {
            this.headers = requestHeaders;
        }
    }


    private void initCookies() {
        this.cookies = new HashMap<>();
        request.cookies().forEach((k, set) -> this.cookies.put(k.toString(), set));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpHeaders getHeaders() {
        if (headers == null) {
            initHeaders();
        }
        return headers;
    }


    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryParams() {
        if (queryParams == null) {
            initQueryParams();
        }
        return queryParams;
    }


    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, Set<Cookie>> getCookies() {
        if (cookies == null) {
            initCookies();
        }
        return cookies;
    }


    public void setCookies(Map<String, Set<Cookie>> cookies) {
        this.cookies = cookies;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }
}
