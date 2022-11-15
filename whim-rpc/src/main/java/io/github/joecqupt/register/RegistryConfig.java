package io.github.joecqupt.register;

import lombok.Data;


@Data
public class RegistryConfig {
    private RegistryType registryType;
    private String registerUrl;
    private String userName;
    private String password;
}
