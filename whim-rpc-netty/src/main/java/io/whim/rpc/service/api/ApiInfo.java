package io.whim.rpc.service.api;

import java.lang.reflect.Method;

/**
 * 服务信息
 */
public class ApiInfo {

    private Class<?> serviceClass;

    private Method method;

    private Object service;

    private Class<?> parameterType;

    private Class<?> returnType;



    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "serviceClass=" + serviceClass +
                ", method=" + method +
                ", service=" + service +
                ", parameterType=" + parameterType +
                ", returnType=" + returnType +
                '}';
    }
}
