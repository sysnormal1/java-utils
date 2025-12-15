package com.sysnormal.libs.utils.database;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseUtils {


    public abstract boolean indexExists(Connection conn, String tableName, String constraintName) throws SQLException;

    public abstract boolean foreignKeyExists(Connection conn,String tableName,String fkName) throws SQLException;
}