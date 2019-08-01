package com.cloud.producer.codegenerator.entity.columntype;

import com.cloud.producer.codegenerator.entity.ColumnEntity;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

@Data
public class IntType extends Type{

    private String dbType = "int";
    private String key = "dataType";
    private String codeType = "int";
    private int length = 11;

    public boolean isSupport(){
        if (MapUtils.isEmpty(Type.columnMap)) {
            return false;
        }
        boolean bool = dbType.equals(Type.columnMap.get(key));
        if (!bool) {
            next = new DateType();
        }
        return bool;
    }
    public ColumnEntity fileEntity(ColumnEntity entity) {
        entity.setType(codeType);
        entity.setLength(length);
        return entity;
    }

}
