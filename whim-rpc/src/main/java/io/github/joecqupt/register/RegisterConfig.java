package io.github.joecqupt.register;

import java.util.Objects;

public class RegisterConfig {
    private RegisterType registerType;
    private String registerUrl;
    private String userName;
    private String password;

    public RegisterType getRegisterType() {
        return registerType;
    }

    public void setRegisterType(RegisterType registerType) {
        this.registerType = registerType;
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
        RegisterConfig that = (RegisterConfig) o;
        return registerType == that.registerType && Objects.equals(registerUrl, that.registerUrl) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerType, registerUrl, userName, password);
    }

    @Override
    public String toString() {
        return "RegisterConfig{" +
                "registerType=" + registerType +
                ", registerUrl='" + registerUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
