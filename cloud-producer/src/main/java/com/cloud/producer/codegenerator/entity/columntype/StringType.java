package com.cloud.producer.codegenerator.entity.columntype;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

@Data
public class StringType extends Type{

    private String dbType = "varchar,mediumtext";
    private String key = "dataType";
    private String codeType = "String";
    private int length = 2000;

    public boolean isSupport(){
        if (MapUtils.isEmpty(Type.columnMap)) {
            return false;
        }
        boolean bool = Lists.newArrayList(dbType.split(",")).contains(Type.columnMap.get(key));
        if (!bool) {
            next = new IntType();
        }
        return bool;
    }

}
