package com.cloud.producer.codegenerator.entity.columntype;

import com.cloud.producer.codegenerator.entity.ColumnEntity;
import lombok.Data;

import java.util.Map;

@Data
public abstract class Type {

    protected String key = "dataType";
    protected String codeType;
    protected String dbType;
    protected int length = 0;
    protected static Map<String, Object> columnMap;
    protected Type next;
    public static Type finalType;

    protected abstract boolean isSupport();

    public ColumnEntity fileEntity(ColumnEntity entity) {
        entity.setType(this.getCodeType());
        entity.setLength(this.getLength());
        return entity;
    }

    public Type eval() {
        return isSupport()?this: next.eval();
    }

    public static Type init(Map<String, Object> columnMap){
        Type.columnMap = columnMap;
        return new IdType();
    }
}
