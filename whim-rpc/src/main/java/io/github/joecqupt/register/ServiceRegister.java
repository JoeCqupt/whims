package io.github.joecqupt.register;

public interface ServiceRegister {
    void init(RegisterConfig config);
    void register(String apiKey);
    void unRegister(String apiKey);
}
