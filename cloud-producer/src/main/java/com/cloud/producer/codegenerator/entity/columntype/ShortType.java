package com.cloud.producer.codegenerator.entity.columntype;

import com.cloud.producer.codegenerator.entity.ColumnEntity;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

@Data
public class ShortType extends Type{

    private String dbType = "smallint";
    private String key = "dataType";
    private String codeType = "short";
    private int length = 4;

    public boolean isSupport(){
        if (MapUtils.isEmpty(Type.columnMap)) {
            return false;
        }
        boolean bool = dbType.equals(Type.columnMap.get(key));
        if (!bool) {
            throw new RuntimeException("类型不匹配: " + Type.columnMap.get("dataType"));
        }
        return bool;
    }
    public ColumnEntity fileEntity(ColumnEntity entity) {
        entity.setType(codeType);
        entity.setLength(length);
        return entity;
    }

}
