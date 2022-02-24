package io.github.joecqupt.register;

import io.github.joecqupt.register.endpoints.EndPointsRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryManager {
    private static Map<RegistryConfig, Registry> registerMap = new ConcurrentHashMap<>();

    public static synchronized Registry getRegister(RegistryConfig config) {
        Registry serviceRegister = registerMap.get(config);
        if (serviceRegister != null) {
            return serviceRegister;
        }
        if (RegistryType.END_POINTS == config.getRegisterType()) {
            EndPointsRegistry endPointsRegistry = new EndPointsRegistry();
            endPointsRegistry.init(config);
            return endPointsRegistry;
        }
        throw new RuntimeException("unsupported RegisterType. register config:" + config);
    }
}
