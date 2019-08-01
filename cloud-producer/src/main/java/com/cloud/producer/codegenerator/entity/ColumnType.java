package com.cloud.producer.codegenerator.entity;

import lombok.Data;

public enum ColumnType {
    ID("columnKey", "PRI", "String"),
    INT("dataType", "dataType", "int");
    private String key;
    private String dbType;
    private String codeType;

    ColumnType(String key, String dbType, String codeType) {
        this.key = key;
        this.dbType = dbType;
        this.codeType = codeType;
    }

    private boolean isSupport(){
        return false;
    }

    public String getCodeType(){
        if (isSupport()) {
            return codeType;
        }
        return "String";
    }
}
