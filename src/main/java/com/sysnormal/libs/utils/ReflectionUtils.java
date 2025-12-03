package com.sysnormal.libs.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Reflection utils
 *
 * @author Alencar
 * @version 1.0.0
 */
public final class ReflectionUtils {

    /**
     * private constructor avoid instantiate this class
     */
    private ReflectionUtils() {
        throw new UnsupportedOperationException("Utility class");
    }


    /**
     * get all fields including ascendant inherited fields of a type
     * @param type the class to get all fields
     * @return the list of all fields
     */
    public static ArrayList<Field> getAllFields(Class<?> type) {
        ArrayList<Field> fields = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>(); // to avoid duplicates
        for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
            Field[] currentFields = c.getDeclaredFields();
            if (c.equals(type)) {
                for (int i = 0; i < currentFields.length; i++) {
                    if (!fieldNames.contains(currentFields[i].getName())) {
                        fieldNames.add(currentFields[i].getName());
                        fields.add(currentFields[i]);
                    }
                }
            } else {
                for (int i = currentFields.length-1; i > -1; i--) {
                    if (!fieldNames.contains(currentFields[i].getName())) {
                        fieldNames.add(currentFields[i].getName());
                        fields.add(0,currentFields[i]);
                    }
                }
            }
        }
        return fields;
    }


    /**
     * get all methods including ascendant inherited methods of a type
     * @param type the class to get all methods
     * @return the list of all methods
     */
    public static ArrayList<Method> getAllMethods(Class<?> type) {
        ArrayList<Method> methods = new ArrayList<>();
        Set<String> methodNames = new HashSet<>(); // to avoid duplicates
        for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
            Method[] currentMethods = c.getDeclaredMethods();
            if (c.equals(type)) {
                for (int i = 0; i < currentMethods.length; i++) {
                    if (!methodNames.contains(currentMethods[i].getName())) {
                        methodNames.add(currentMethods[i].getName());
                        methods.add(currentMethods[i]);
                    }
                }
            } else {
                for (int i = currentMethods.length-1; i > -1; i--) {
                    if (!methodNames.contains(currentMethods[i].getName())) {
                        methodNames.add(currentMethods[i].getName());
                        methods.add(0,currentMethods[i]);
                    }
                }
            }
        }
        return methods;
    }


    /**
     * check if field is generic
     * @param field the field to check
     * @return if field is generic
     */
    public static boolean isGeneric(Field field) {
        Type type = field.getGenericType();
        return type instanceof TypeVariable<?>;
    }
}
