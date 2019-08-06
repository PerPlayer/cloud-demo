package com.cloud.consumer.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PQuery<T> implements Serializable {

    private PPage<T> page;

    private T data;

    public PQuery(){
        page = new PPage<T>();
    }

    public void setPage(int page){
        this.page.setCurrent(page);
    }

    @ApiModelProperty("查询表名，支持模糊搜索")
    public void setTableName(T tableName){
        this.data = tableName;
    }

}
