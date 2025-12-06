package com.sysnormal.libs.utils.database.migration;


import com.sysnormal.libs.utils.ReflectionUtils;
import jakarta.persistence.EmbeddedId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * class is abstraction of base creation table statments
 * @author Alencar
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class BaseCreationTableStatement {

    private static final Logger logger = LoggerFactory.getLogger(BaseCreationTableStatement.class);

    protected PersistentClass persistentEntity;
    protected Metadata metadata;
    protected Class<?> clazz;
    protected Table table;
    protected String tableName;
    protected ArrayList<Field> clazzFields = new ArrayList<Field>();
    protected List<Column> tableColumns = new ArrayList<Column>();
    protected String createTable;
    protected ArrayList<String> addUniqueConstraints = new ArrayList<String>();
    protected ArrayList<String> addForeignKeys = new ArrayList<String>();
    protected ArrayList<String> addIndexes = new ArrayList<String>();
    protected boolean preexists = false;

    public BaseCreationTableStatement(PersistentClass persistentEntity, Metadata metadata) {
        this.persistentEntity = persistentEntity;
        this.metadata = metadata;
        this.createStatements();
    }

    public void createStatements() {
        if (persistentEntity != null) {
            clazz = persistentEntity.getMappedClass();
            clazzFields = ReflectionUtils.getAllFields(clazz);
            table = persistentEntity.getTable();
            tableName = table.getName();
            tableColumns = table.getColumns().stream().toList();

            ArrayList<Field> newClazzFields = new ArrayList<>();
            for(Field field : clazzFields) {
                newClazzFields.add(field);
                EmbeddedId embeddedId = field.getAnnotation(EmbeddedId.class);
                if (embeddedId != null) {
                    ArrayList<Field> idClassFields = ReflectionUtils.getAllFields(field.getType());
                    for(int i = idClassFields.size()-1;i > -1; i--) {
                        if(idClassFields.get(i).getAnnotation(jakarta.persistence.Column.class) != null) {
                            newClazzFields.addFirst(idClassFields.get(i));
                        }
                    }
                }
            }
            clazzFields = newClazzFields;

            buildCreateTableStatement();
            buildAddUniqueConstraintsStatement();
            buildAddForeignKeyConstraintsStatement();
            buildAddIndexesStatement();
        }
    }

    public String checkColumnStatmentContainsOrAdd(String columnStatement, String columnDefinition, String toCheckOrAdd) {
        if (!columnStatement.toLowerCase().contains(" " + toCheckOrAdd)) {
            if (StringUtils.hasText(columnDefinition)) {
                if (!columnDefinition.toLowerCase().contains(" " + toCheckOrAdd)) {
                    columnStatement += " " + toCheckOrAdd;
                } else {
                    columnStatement += " " + columnDefinition;
                }
            } else {
                columnStatement += " " + toCheckOrAdd;
            }
        }
        return columnStatement;
    }

    public abstract void buildCreateTableStatement();

    public abstract void buildAddUniqueConstraintsStatement();

    public abstract void buildAddForeignKeyConstraintsStatement();

    public abstract void buildAddIndexesStatement();


}
