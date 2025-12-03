package com.sysnormal.libs.utils;

/**
 * utils contains common utils static methods to common use
 *
 * @author Alencar
 * @version 1.0.0
 */
public final class ObjectUtils {

    /**
     * private constructor avoid instantiate this class
     */
    private ObjectUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * coalescence between multiple values
     * @param values multiple values of an type
     * @return the first value with is not null
     * @param <T> the type of values
     */
    public static <T> T coalesce(T... values) {
        if (values != null) {
            for (T value : values) {
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

}
