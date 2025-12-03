package com.sysnormal.libs.utils.database;

import com.sysnormal.libs.utils.ReflectionUtils;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * jpa reflection utils
 * @author Alencar
 * @version 1.0.0
 */
public final class JpaReflectionUtils {

    /**
     * private constructor avoid instantiate this class
     */
    private JpaReflectionUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Resolve o tipo real da entidade referenciada em um campo JPA com @JoinColumn.
     *
     * @param field        O campo anotado com @JoinColumn
     * @param entityClass  A classe concreta que contém (ou herda) o campo
     * @return com a classe da entidade referenciada (ex: CampaignKpi.class)
     */
    public static Optional<Class<?>> resolveEntityType(Field field, Class<?> entityClass) {
        try {
            // Se for um campo normal (não genérico)
            if (!ReflectionUtils.isGeneric(field)) {
                return Optional.of(field.getType());
            }

            // Caso o campo esteja numa superclasse genérica (ex: BaseEntityModel<T>)
            Type genericSuperclass = entityClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType pt) {
                // pega o tipo real substituído no parâmetro genérico
                Type[] typeArgs = pt.getActualTypeArguments();

                if (typeArgs.length > 0) {
                    Type actual = typeArgs[0];
                    if (actual instanceof Class<?> clazz) {
                        return Optional.of(clazz);
                    } else {
                        // fallback se o tipo vier como TypeName
                        return Optional.of(Class.forName(actual.getTypeName()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Obtém o nome da tabela da entidade referenciada (usando @Table, se existir).
     *
     * @param entityType Classe da entidade
     * @return Nome da tabela
     */
    public static String resolveTableName(Class<?> entityType) {
        Table table = entityType.getAnnotation(Table.class);
        return (table != null && !table.name().isBlank())
                ? table.name()
                : entityType.getSimpleName();
    }


}