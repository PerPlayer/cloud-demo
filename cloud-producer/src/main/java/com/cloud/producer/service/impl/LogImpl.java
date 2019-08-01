package com.cloud.producer.service.impl;

import com.cloud.producer.dao.LogDao;
import com.cloud.producer.entity.LogEntity;
import com.cloud.producer.model.LogModel;
import com.cloud.producer.service.LogService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LogImpl implements LogService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogDao dao;

    @Override
    public LogModel save(LogModel model) {
        LogEntity entity = new LogEntity();
        model.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        BeanUtils.copyProperties(model, entity);
        int count = dao.save(entity);
        if (count < 1) {
            throw new RuntimeException("保存失败");
        }
        return model;
    }

    @Override
    public int update(String id, String column, Object value) {
        return dao.update(id, column, value);
    }

    @Override
    public int delete(String id) {
        return dao.delete(id);
    }

    @Override
    public List<LogModel> queryAll() {
        List<LogEntity> entities = dao.queryAll();
        ArrayList<LogModel> models = Lists.newArrayList();
        entities.forEach(entity -> {
            models.add(toModel(entity));
        });
        return models;
    }

    @Override
    public LogModel queryById(String id) {
        return toModel(dao.queryById(id));
    }

    private LogModel toModel(LogEntity entity) {
            LogModel model = new LogModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
