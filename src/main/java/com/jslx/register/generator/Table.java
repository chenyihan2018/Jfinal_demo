package com.jslx.register.generator;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Table extends com.jfinal.plugin.activerecord.Table {
    private String name;
    private String[] primaryKey = null;
    private Map<String, Class<?>> columnTypeMap;
    private Class<? extends Model<?>> modelClass;
    
    public Table(String name, Class<? extends Model<?>> modelClass) {
        super(name, modelClass);
        if (StrKit.isBlank(name)) {
            throw new IllegalArgumentException("Table name can not be blank.");
        } else if (modelClass == null) {
            throw new IllegalArgumentException("Model class can not be null.");
        } else {
            this.name = name.trim();
            this.modelClass = modelClass;
        }
    }
    
    public Table(String name, String primaryKey, Class<? extends Model<?>> modelClass) {
        super(name, modelClass);
        if (StrKit.isBlank(name)) {
            throw new IllegalArgumentException("Table name can not be blank.");
        } else if (StrKit.isBlank(primaryKey)) {
            throw new IllegalArgumentException("Primary key can not be blank.");
        } else if (modelClass == null) {
            throw new IllegalArgumentException("Model class can not be null.");
        } else {
            this.name = name.trim();
            this.setPrimaryKey(primaryKey.trim());
            this.modelClass = modelClass;
        }
    }
    
    void setPrimaryKey(String primaryKey) {
        String[] arr = primaryKey.split(",");
        
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = arr[i].trim();
        }
        
        this.primaryKey = arr;
    }
    
    public void setColumnTypeMap(Map<String, Class<?>> columnTypeMap) {
        if (columnTypeMap == null) {
            throw new IllegalArgumentException("columnTypeMap can not be null");
        } else {
            this.columnTypeMap = columnTypeMap;
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    void setColumnType(String columnLabel, Class<?> columnType) {
        this.columnTypeMap.put(columnLabel, columnType);
    }
    
    @Override
    public Class<?> getColumnType(String columnLabel) {
        return (Class) this.columnTypeMap.get(columnLabel);
    }
    
    @Override
    public boolean hasColumnLabel(String columnLabel) {
        return this.columnTypeMap.containsKey(columnLabel);
    }
    
    @Override
    public String[] getPrimaryKey() {
        return this.primaryKey;
    }
    
    @Override
    public Class<? extends Model<?>> getModelClass() {
        return this.modelClass;
    }
    
    @Override
    public Map<String, Class<?>> getColumnTypeMap() {
        return Collections.unmodifiableMap(this.columnTypeMap);
    }
    
    @Override
    public Set<Map.Entry<String, Class<?>>> getColumnTypeMapEntrySet() {
        return this.columnTypeMap.entrySet();
    }
}
