package io.github.joecqupt.register;

import java.util.List;

public class ConsumerInfo {
    private String serviceName;
    private String methodNAme;
    private List<String> labels;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodNAme() {
        return methodNAme;
    }

    public void setMethodNAme(String methodNAme) {
        this.methodNAme = methodNAme;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
