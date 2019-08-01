package com.cloud.producer.codegenerator.entity.columntype;

import com.cloud.producer.codegenerator.entity.ColumnEntity;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

@Data
public class IdType extends Type{

    private String dbType = "PRI";
    private String key = "columnKey";
    private String codeType = "String";
    private int length = 32;

    public boolean isSupport(){
        if (MapUtils.isEmpty(Type.columnMap)) {
            return false;
        }
        boolean bool = dbType.equals(Type.columnMap.get(key));
        if (!bool) {
            next = new StringType();
        }
        return bool;
    }

    @Override
    public ColumnEntity fileEntity(ColumnEntity entity) {
        entity.setKey("@Id");
        entity.setType(codeType);
        entity.setLength(length);
        return entity;
    }

}
