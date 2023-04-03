package io.whim.gateway.handler;

import org.reactivestreams.Publisher;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public interface WebHandler {
    Publisher<Void> handle(HttpServerRequest request, HttpServerResponse response);
}
