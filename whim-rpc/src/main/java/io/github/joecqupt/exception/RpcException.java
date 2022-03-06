package io.github.joecqupt.exception;


public class RpcException extends RuntimeException {

    public RpcException(String message, Exception e) {
        super(message, e);
    }
}
