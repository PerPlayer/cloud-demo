package com.cloud.producer.entry;

import lombok.Data;

@Data
public class ColumnEntry {

    private String columnName;

    private String fieldName;

    private String type;

    private int length;

    private String key;
}
