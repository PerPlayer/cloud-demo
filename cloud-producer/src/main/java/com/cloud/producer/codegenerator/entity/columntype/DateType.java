package com.cloud.producer.codegenerator.entity.columntype;

import lombok.Data;
import org.apache.commons.collections.MapUtils;

@Data
public class DateType extends Type{

    private String dbType = "datetime";
    private String codeType = "Date";
    private int length = 0;

    public boolean isSupport(){
        if (MapUtils.isEmpty(Type.columnMap)) {
            return false;
        }
        boolean bool = dbType.equals(Type.columnMap.get(key));
        if (!bool) {
            next = new FloatType();
        }
        return bool;
    }

}
