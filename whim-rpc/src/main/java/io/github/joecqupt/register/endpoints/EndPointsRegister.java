package io.github.joecqupt.register.endpoints;

import io.github.joecqupt.register.RegisterConfig;
import io.github.joecqupt.register.ServiceRegister;

public class EndPointsRegister implements ServiceRegister {

    @Override
    public void init(RegisterConfig config) {

    }

    @Override
    public void register(String apiKey) {
        // do nothing
    }

    @Override
    public void unRegister(String apiKey) {
        // do nothing
    }
}
