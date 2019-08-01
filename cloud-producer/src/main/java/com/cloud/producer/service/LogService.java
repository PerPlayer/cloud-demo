package com.cloud.producer.service;

import com.cloud.producer.entity.LogEntity;
import com.cloud.producer.model.LogModel;

import java.util.List;

public interface LogService {

    LogModel save(LogModel model);

    int update(String id, String column, Object value);

    int delete(String id);

    List<LogModel> queryAll();

    LogModel queryById(String id);
}
