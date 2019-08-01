package com.cloud.producer.codegenerator.entity.columntype;

import com.cloud.producer.codegenerator.entity.ColumnEntity;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

@Data
public class LongType extends Type{

    private String dbType = "bigint";
    private String key = "dataType";
    private String codeType = "long";
    private int length = 20;

    public boolean isSupport(){
        if (MapUtils.isEmpty(Type.columnMap)) {
            return false;
        }
        boolean bool = dbType.equals(Type.columnMap.get(key));
        if (!bool) {
            next = new DoubleType();
        }
        return bool;
    }
    public ColumnEntity fileEntity(ColumnEntity entity) {
        entity.setType(codeType);
        entity.setLength(length);
        return entity;
    }

}
