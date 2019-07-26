package com.cloud.producer.entry;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "crawler_entry")
@Data
@NoArgsConstructor
public class crawlerEntry {

    @Id
    @Column(name = "id", length = 32 )
    private String id;
    
    @Column(name = "create_time")
    private Date createTime;
    
    @Column(name = "modify_time")
    private Date modifyTime;
    
    @Column(name = "status", length = 4 )
    private int status;
    
    @Column(name = "version", length = 4 )
    private int version;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "task_id")
    private String taskId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "weight", length = 4 )
    private int weight;

}
