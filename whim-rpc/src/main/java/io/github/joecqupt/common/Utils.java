package io.github.joecqupt.common;

import java.util.concurrent.atomic.AtomicInteger;

import static io.github.joecqupt.common.Constants.HASH_SIGN;

public class Utils {

    private static AtomicInteger invokeIdGen = new AtomicInteger(0);

    public static String apiKey(String className, String methodName) {
        return className + HASH_SIGN + methodName;
    }

    public static int genInvokeId() {
        return invokeIdGen.getAndIncrement();
    }

}
