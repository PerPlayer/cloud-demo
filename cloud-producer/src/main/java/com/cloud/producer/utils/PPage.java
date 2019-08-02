package com.cloud.producer.utils;

import lombok.Data;

import java.util.List;

@Data
public class PPage<T> {
    private int page = 1;
    private int current = 1;
    private int size = 10;
    private long total = 0;
    private List<T> list;
}
