package io.github.joecqupt.register;

import java.util.Objects;

public class ServiceInstance {
    private String ip;
    private int port;

    public ServiceInstance(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceInstance instance = (ServiceInstance) o;
        return port == instance.port && Objects.equals(ip, instance.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
