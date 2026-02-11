package com.sysnormal.libs.utils;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public static <T> void setPropertyFromMap(
            Map<String, Object> map,
            String key,
            Consumer<T> setter,
            Function<Object, T> parser
    ) {
        if (map == null || map.isEmpty()) return;
        if (!TextUtils.hasText(key) || setter == null) return;
        if (!map.containsKey(key)) return;

        Object value = map.get(key);
        if (value == null) return;

        try {
            T parsedValue;

            if (parser != null) {
                parsedValue = parser.apply(value);
            } else {
                parsedValue = (T) value; // uso direto
            }

            if (parsedValue != null) {
                setter.accept(parsedValue);
            }

        } catch (Exception e) {
            // loga se quiser, mas não quebra o fluxo de auth
            e.printStackTrace();
        }
    }

    public static void setLongPropertyFromMap(
            Map<String, Object> map,
            String key,
            Consumer<Long> setter
    ) {
        setPropertyFromMap(map, key, setter, v -> Long.valueOf(String.valueOf(v)));
    }

}
