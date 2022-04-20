package io.whim.rpc.registry;

import java.lang.reflect.Method;
import java.util.List;

public class ConsumerInfo {
    private List<Method> rpcMethods;

    private List<String> labels;

    public List<Method> getRpcMethods() {
        return rpcMethods;
    }

    public void setRpcMethods(List<Method> rpcMethods) {
        this.rpcMethods = rpcMethods;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}