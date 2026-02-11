package com.sysnormal.libs.utils;

/**
 * Text utils
 *
 * @author Alencar
 * @version 1.0.0
 */
public final class TextUtils {

    /**
     * private constructor avoid instantiate this class
     */
    private TextUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * check if text is not null and is not empty
     * @param text the text to check
     * @return if text is not null and is not empty
     */
    public static boolean hasText(String text) {
        return text != null && !text.isEmpty();
    }

    /**
     * check if text is not null and is not empty
     * @param text the text to check
     * @return if text is not null and is not empty
     */
    public static boolean hasNotNullText(String text) {
        return hasText(text) && !text.trim().equalsIgnoreCase("null");
    }

    /**
     * remove all except last dot from string
     * @param s the string with dots
     * @return the string with only last dot
     */
    public static String onlyLastDot(String s) {
        // 1) remove todos os pontos que NÃO sejam o último
        String onlyLastDot = s.replaceAll("\\.(?![^.]*$)", "");
        // 2) se não houver NENHUM ponto, adiciona um no final
        return onlyLastDot.contains(".") ? onlyLastDot : onlyLastDot + ".";
    }

    /**
     * trim left not digits characters
     * @param input the string input
     * @return the string trimmed
     */
    public static String onlyDigitsLeftTrim(String input) {
        if (hasText(input)) {
            return input.replaceAll("\\D", "").replaceFirst("^0+(?!$)", "");
        }
        return input;
    }

    /**
     * equals ignore case safe checking null
     * @param a the first string
     * @param b the first string
     * @return if is equals ignore case
     */
    public static boolean equalsIgnoreCaseSafe(String a, String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }
}
