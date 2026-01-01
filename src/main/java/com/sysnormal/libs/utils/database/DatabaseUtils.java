package com.sysnormal.libs.utils.database;

import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;

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
}