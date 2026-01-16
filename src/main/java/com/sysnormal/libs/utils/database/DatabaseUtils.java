package com.sysnormal.libs.utils.database;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import tools.jackson.databind.JsonNode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseUtils {


    public abstract boolean indexExists(Connection conn, String tableName, String constraintName) throws SQLException;

    public abstract boolean foreignKeyExists(Connection conn,String tableName,String fkName) throws SQLException;

    public static String detectQueryType(String query) {
        String result = "select";
        if (StringUtils.hasText(query)) {
            if (query.trim().toLowerCase().indexOf("insert") == 0) {
                result = "insert";
            } else if (query.trim().toLowerCase().indexOf("update") == 0) {
                result = "update";
            } else if (query.trim().toLowerCase().indexOf("delete") == 0) {
                result = "delete";
            }
        }
        return result;
    }


    public static <E> Specification<E> fromWhere(JsonNode where) {
        return (root, query, cb) -> buildPredicate(where, root, cb);
    }

    private static Predicate buildPredicate(
            JsonNode node,
            Root<?> root,
            CriteriaBuilder cb
    ) {
        List<Predicate> predicates = new ArrayList<>();
        try {
            if (!node.isObject()) {
                throw new IllegalArgumentException("Where clause must be a JSON object");
            }

            node.propertyNames().forEach(key -> {
                JsonNode value = node.get(key);

                switch (key) {
                    case "$and", "and", "&" -> {
                        if (value.isArray() && !value.isEmpty()) {
                            predicates.add(cb.and(parseLogicalArray(value, root, cb)));
                        }
                    }
                    case "$or", "or", "|", "||" -> {
                        if (value.isArray() && !value.isEmpty()) {
                            predicates.add(cb.or(parseLogicalArray(value, root, cb)));
                        }
                    }

                    default -> predicates.add(parseFieldPredicate(key, value, root, cb));
                }
            });
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }


    private static Predicate[] parseLogicalArray(
            JsonNode arrayNode,
            Root<?> root,
            CriteriaBuilder cb
    ) {
        List<Predicate> predicates = new ArrayList<>();

        arrayNode.forEach(n ->
                predicates.add(buildPredicate(n, root, cb))
        );

        return predicates.toArray(new Predicate[0]);
    }

    private static String normalizeLikeValue(String raw) {
        if (!StringUtils.hasText(raw)) {
            return "%";
        }

        boolean startsWithPercent = raw.startsWith("%");
        boolean endsWithPercent = raw.endsWith("%");

        String core = raw;

        // remove % externos para tratar o conteúdo interno
        if (startsWithPercent) core = core.substring(1);
        if (endsWithPercent && core.length() > 0) core = core.substring(0, core.length() - 1);

        // escapa curingas internos
        core = core
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");

        StringBuilder result = new StringBuilder();

        if (startsWithPercent) result.append("%");
        else result.append("%");

        result.append(core);

        if (endsWithPercent) result.append("%");
        else result.append("%");

        return result.toString();
    }


    private static Predicate parseFieldPredicate(
            String field,
            JsonNode operations,
            Root<?> root,
            CriteriaBuilder cb
    ) {
        Path<?> path = root.get(field);

        // ✅ CASO 1: valor direto → eq implícito
        if (!operations.isObject()) {
            return cb.equal(path, parseValue(path, operations));
        }

        // ✅ CASO 2: objeto com operadores
        List<Predicate> predicates = new ArrayList<>();

        operations.propertyNames().forEach(op -> {
            JsonNode value = operations.get(op);

            predicates.add(
                    switch (op) {
                        case "$eq", "eq", "=" -> cb.equal(path, parseValue(path, value));
                        case "$ne", "ne", "<>", "!=" -> cb.notEqual(path, parseValue(path, value));
                        case "$like", "like" -> cb.like(path.as(String.class),normalizeLikeValue(value.asText()),'\\');
                        case "$gt", "gt", ">" -> cb.greaterThan(
                                path.as(Comparable.class),
                                (Comparable) parseValue(path, value)
                        );
                        case "$gte", "gte", ">=" -> cb.greaterThanOrEqualTo(
                                path.as(Comparable.class),
                                (Comparable) parseValue(path, value)
                        );
                        case "$lt", "lt", "<" -> cb.lessThan(
                                path.as(Comparable.class),
                                (Comparable) parseValue(path, value)
                        );
                        case "$lte", "lte", "<=" -> cb.lessThanOrEqualTo(
                                path.as(Comparable.class),
                                (Comparable) parseValue(path, value)
                        );
                        case "$in", "in" -> {
                            CriteriaBuilder.In<Object> in = cb.in(path);
                            value.forEach(v -> in.value(parseValue(path, v)));
                            yield in;
                        }
                        default -> throw new IllegalArgumentException(
                                "Unsupported operator: " + op
                        );
                    }
            );
        });

        return cb.and(predicates.toArray(new Predicate[0]));
    }


    private static Object parseValue(Path<?> path, JsonNode value) {
        Class<?> javaType = path.getJavaType();

        if (javaType.equals(Long.class)) return value.asLong();
        if (javaType.equals(Integer.class)) return value.asInt();
        if (javaType.equals(Boolean.class)) return value.asBoolean();
        if (javaType.equals(String.class)) return value.asText();

        // fallback (ex: enums, dates tratadas fora)
        return value.asText();
    }

}