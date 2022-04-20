package io.whim.rpc.registry;

import java.util.Objects;

public class RegistryConfig {
    private RegistryType registryType;
    private String registerUrl;
    private String userName;
    private String password;

    public RegistryType getRegisterType() {
        return registryType;
    }

    public void setRegisterType(RegistryType registryType) {
        this.registryType = registryType;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistryConfig that = (RegistryConfig) o;
        return registryType == that.registryType && Objects.equals(registerUrl, that.registerUrl) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registryType, registerUrl, userName, password);
    }

    @Override
    public String toString() {
        return "RegisterConfig{" +
                "registerType=" + registryType +
                ", registerUrl='" + registerUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
