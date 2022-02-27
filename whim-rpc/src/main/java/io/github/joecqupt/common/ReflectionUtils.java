package io.github.joecqupt.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static List<Method> getMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> list = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(annotation)) {
                list.add(m);
            }
        }
        return list;
    }
}
