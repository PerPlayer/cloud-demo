package com.cloud.producer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "crawler_log")
@Data
@NoArgsConstructor
public class LogEntity {

    /**  */
    @Id
    @Column(name = "id", length = 32 )
    private String id;

    /** 用户名 */
    @Column(name = "username", length = 2000 )
    private String username;

    /** 用户操作 */
    @Column(name = "operation", length = 2000 )
    private String operation;

    /** 请求方法 */
    @Column(name = "method", length = 2000 )
    private String method;

    /** 请求参数 */
    @Column(name = "params", length = 2000 )
    private String params;

    /** 执行时长(毫秒) */
    @Column(name = "time", length = 11 )
    private int time;

    /** IP地址 */
    @Column(name = "ip", length = 2000 )
    private String ip;

    /** 创建时间 */
    @Column(name = "create_date")
    private Date createDate;

    /**  */
    @Column(name = "create_time")
    private Date createTime;

    /**  */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**  */
    @Column(name = "status", length = 11 )
    private int status;

    /**  */
    @Column(name = "version", length = 11 )
    private int version;

}
