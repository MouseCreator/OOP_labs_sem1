package mouse.project.lib.utils;

import java.lang.annotation.Annotation;

public class Utils {
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        T configuration = clazz.getAnnotation(annotation);
        if (configuration == null) {
            throw new IllegalArgumentException(
                    String.format("Configuration class %s does not have %s annotation present.",
                            clazz.getName(), annotation.getName()));
        }
        return configuration;
    }

    public static Object validatePrimitive(Class<?> clazz, Object toTest) {
        if (clazz == int.class) {
            if (toTest instanceof Integer) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not an integer");
            }
        } else if (clazz == byte.class) {
            if (toTest instanceof Byte) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not a byte");
            }
        } else if (clazz == short.class) {
            if (toTest instanceof Short) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not a short");
            }
        } else if (clazz == long.class) {
            if (toTest instanceof Long) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not a long");
            }
        } else if (clazz == float.class) {
            if (toTest instanceof Float) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not a float");
            }
        } else if (clazz == double.class) {
            if (toTest instanceof Double) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not a double");
            }
        } else if (clazz == char.class) {
            if (toTest instanceof Character) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not a char");
            }
        } else if (clazz == boolean.class) {
            if (toTest instanceof Boolean) {
                return toTest;
            } else {
                throw new IllegalArgumentException(toTest + " is not a boolean");
            }
        } else {
            throw new IllegalArgumentException(clazz + " is not a primitive type");
        }
    }
}
