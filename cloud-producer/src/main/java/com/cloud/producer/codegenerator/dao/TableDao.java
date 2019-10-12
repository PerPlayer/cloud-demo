package com.cloud.producer.codegenerator.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TableDao {

    List<Map<String, Object>> queryTableList(@Param("tableName") String tableName);

    Map<String, Object> queryTable(String tableName);

    List<Map<String, Object>> queryColumns(String tableName);
}
