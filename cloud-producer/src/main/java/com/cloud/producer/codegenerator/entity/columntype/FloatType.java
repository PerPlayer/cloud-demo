package com.cloud.producer.codegenerator.entity.columntype;

import com.cloud.producer.codegenerator.entity.ColumnEntity;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

@Data
public class FloatType extends Type{

    private String dbType = "float";
    private String key = "dataType";
    private String codeType = "float";
    private int length = 12;

    public boolean isSupport(){
        if (MapUtils.isEmpty(Type.columnMap)) {
            return false;
        }
        boolean bool = dbType.equals(Type.columnMap.get(key));
        if (!bool) {
            next = new LongType();
        }
        return bool;
    }
    public ColumnEntity fileEntity(ColumnEntity entity) {
        entity.setType(codeType);
        entity.setLength(length);
        return entity;
    }

}
