package com.cloud.producer.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class LogModel {

    /**  */
    private String id;

    /** 用户名 */
    private String username;

    /** 用户操作 */
    private String operation;

    /** 请求方法 */
    private String method;

    /** 请求参数 */
    private String params;

    /** 执行时长(毫秒) */
    private int time;

    /** IP地址 */
    private String ip;

    /** 创建时间 */
    private Date createDate;

    /**  */
    private Date createTime;

    /**  */
    private Date modifyTime;

    /**  */
    private int status;

    /**  */
    private int version;


}
