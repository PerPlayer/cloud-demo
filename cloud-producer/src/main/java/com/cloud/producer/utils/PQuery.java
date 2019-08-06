package com.cloud.producer.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class PQuery<T> implements Serializable {

    private PPage<T> page;

    private T data;

}
