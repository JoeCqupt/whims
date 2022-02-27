package io.github.joecqupt.invoke;

import io.github.joecqupt.annotation.RpcMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcClientProxy implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean isRpc = method.isAnnotationPresent(RpcMethod.class);
        if (isRpc) {
            // todo 
        }
        return method.invoke(proxy, args);
    }
}
