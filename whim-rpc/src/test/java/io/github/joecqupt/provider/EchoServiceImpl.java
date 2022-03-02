package io.github.joecqupt.provider;

import io.github.joecqupt.api.EchoService;

public class EchoServiceImpl implements EchoService {
    @Override
    public String sayHello(String str) {
        return "response to:" + str;
    }
}
