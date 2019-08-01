package com.cloud.producer.codegenerator.entity;

import lombok.Data;

@Data
public class ColumnEntity {

    private String columnName;

    private String fieldName;

    private String comment;

    private String type;

    private int length;

    private String key;

}
