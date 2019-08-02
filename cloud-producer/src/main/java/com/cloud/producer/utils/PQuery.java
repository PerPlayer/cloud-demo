package com.cloud.producer.utils;

import lombok.Data;

@Data
public class PQuery<T> {

    private PPage<T> page;

    private T data;

}
