package io.github.joecqupt.common;

import static io.github.joecqupt.common.Constants.HASH_SIGN;

public class Utils {

    public static String apiKey(String className, String methodName) {
        return className + HASH_SIGN + methodName;
    }

}
