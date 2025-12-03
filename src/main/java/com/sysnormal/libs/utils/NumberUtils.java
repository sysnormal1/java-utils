package com.sysnormal.libs.utils;

import java.math.BigDecimal;

/**
 * Number utils
 *
 * @author Alencar
 * @version 1.0.0
 */
public final class NumberUtils {

    /**
     * private constructor avoid instantiate this class
     */
    private NumberUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * get byte value from object
     * @param obj the object to cast
     * @return byte value or 0
     */
    public static byte getByteValueOrZero(Object obj) {
        if (obj == null) return 0;
        if (obj instanceof Number) return ((Number) obj).byteValue();
        try {
            return Byte.parseByte(obj.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * get byte value from object
     * @param obj the object to cast
     * @return byte value or 0
     */
    public static Byte getByteValueOrNull(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).byteValue();
        try {
            return Byte.parseByte(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * safe cast string to big decimal
     * @param input the string to cast
     * @return the big decimal or null
     */
    public static BigDecimal safeStringToBigDecimal(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("[Conversion Error] Input is null or empty.");
            return null;
        }

        // Substitui vírgulas por pontos
        String normalized = input.replace(",", ".");

        // Remove tudo que não for número, +, -, ou ponto
        normalized = normalized.replaceAll("[^\\d+\\-\\.]", "");

        // Verifica se tem mais de um ponto
        int dotCount = normalized.length() - normalized.replace(".", "").length();
        if (dotCount > 1) {
            System.out.println("[Conversion Error] More than one decimal point found: " + normalized);
            return null;
        }

        // Tenta criar o BigDecimal
        try {
            if (normalized.isEmpty()) {
                System.out.println("[Conversion Error] String became empty after cleaning: " + input);
                return null;
            }
            return new BigDecimal(normalized);
        } catch (NumberFormatException e) {
            System.out.println("[Conversion Error] Failed to parse BigDecimal from: " + normalized);
            return null;
        }
    }
}
