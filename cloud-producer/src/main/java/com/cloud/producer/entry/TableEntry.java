package com.cloud.producer.entry;

import lombok.Data;
import org.apache.velocity.context.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Data
public class TableEntry implements Context {

    private String packagePath;

    private String tableName;

    private String entryName;

    private String comment;

    private List<ColumnEntry> columns;

    private Class clazz = TableEntry.class;

    @Override
    public Object put(String key, Object value) {
        try {
            Method method = clazz.getMethod(key, value.getClass());
            method.invoke(this, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public Object get(String key) {
        for (Method method : clazz.getMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("get")&&methodName.contains(key.substring(1, key.length()))) {
                try {
                    return method.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return null==get((String)key);
    }

    @Override
    public Object[] getKeys() {
        return new Object[0];
    }

    @Override
    public Object remove(Object key) {
        return null;
    }
}
