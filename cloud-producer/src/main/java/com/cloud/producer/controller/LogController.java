package com.cloud.producer.controller;

import com.cloud.producer.model.LogModel;
import com.cloud.producer.service.LogService;
import com.cloud.producer.utils.B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("log/")
public class LogController {

    @Autowired
    private LogService service;

    @RequestMapping("save")
    public B save(@RequestBody LogModel model){
        service.save(model);
        return B.ok().put("id", model.getId());
    }

    @RequestMapping("update")
    public B update(@RequestParam(value = "id") String id,
                    @RequestParam(value = "column") String column,
                    @RequestParam(value = "value") String value) {
        int re = service.update(id, column, value);
        return re==1?B.ok():B.error();
    }

    @RequestMapping("delete")
    public B delete(@RequestParam(value = "id") String id) {
        int re = service.delete(id);
        return re==1?B.ok():B.error();
    }

    @RequestMapping("query/all")
    public B queryAll() {
        List<LogModel> models = service.queryAll();
        return B.ok().put("data", models);
    }

    @RequestMapping("query/id")
    public B queryById(@RequestParam(value = "id") String id) {
            LogModel model = service.queryById(id);
        return B.ok().put("data", model);
    }
}
