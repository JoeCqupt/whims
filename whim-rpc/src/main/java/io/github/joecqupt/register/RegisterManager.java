package io.github.joecqupt.register;

import io.github.joecqupt.register.endpoints.EndPointsRegister;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegisterManager {
    private static Map<RegisterConfig, ServiceRegister> registerMap = new ConcurrentHashMap<>();

    public static synchronized ServiceRegister getRegister(RegisterConfig config) {
        ServiceRegister serviceRegister = registerMap.get(config);
        if (serviceRegister != null) {
            return serviceRegister;
        }
        if (RegisterType.END_POINTS == config.getRegisterType()) {
            EndPointsRegister endPointsRegister = new EndPointsRegister();
            endPointsRegister.init(config);
            return endPointsRegister;
        }
        throw new RuntimeException("unsupported RegisterType. register config:" + config);
    }
}
