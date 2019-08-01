package com.cloud.producer.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.producer.entity.LogEntity;

import java.util.List;

@Repository
public interface LogDao {

    int save(LogEntity entity);

    int update(@Param("id") String id, @Param("column") String column, @Param("value") Object value);

    int delete(String id);

    List<LogEntity> queryAll();

    LogEntity queryById(String id);
}
