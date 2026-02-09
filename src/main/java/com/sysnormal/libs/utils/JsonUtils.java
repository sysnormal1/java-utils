package com.sysnormal.libs.utils;

import com.sysnormal.libs.utils.database.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.JsonNode;

import java.util.*;
import java.util.function.Function;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final Set<String> TRUE_STRINGS = Set.of(
            "true", "1", "yes", "y", "sim", "s", "verdadeiro"
    );

    private static final Set<String> FALSE_STRINGS = Set.of(
            "false", "0", "no", "n", "nao", "não", "falso"
    );

    public static <T> Optional<T> get(
            JsonNode parent,
            String fieldName,
            Function<JsonNode, T> converter
    ) {
        if (parent == null || parent.isNull()) {
            return Optional.empty();
        }

        JsonNode node = parent.get(fieldName);

        if (node == null || node.isNull()) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(converter.apply(node));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public static <T> List<T> jsonArrayToList(JsonNode arrayNode, Function<JsonNode, T> mapper) {
        if (arrayNode == null || !arrayNode.isArray() || arrayNode.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>(arrayNode.size());

        for (JsonNode node : arrayNode) {
            list.add(mapper.apply(node));
        }

        return list;
    }

    public static byte jsonNodeTo01(JsonNode node) {

        if (node == null || node.isNull()) {
            return 0;
        }

        if (node.isBoolean()) {
            return (byte) (node.asBoolean() ? 1 : 0);
        }

        if (node.isNumber()) {
            return (byte) (node.asDouble() != 0.0 ? 1 : 0);
        }

        if (node.isTextual()) {
            String value = node.asText().trim().toLowerCase();

            if (TRUE_STRINGS.contains(value)) return 1;
            if (FALSE_STRINGS.contains(value)) return 0;

            return 0;
        }

        if (node.isArray()) {
            return (byte) (node.size() > 0 ? 1 : 0);
        }

        if (node.isObject()) {
            return (byte) (node.size() > 0 ? 1 : 0);
        }

        return 0;
    }


}
