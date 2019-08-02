package com.cloud.producer.codegenerator;

import com.cloud.producer.utils.B;
import com.cloud.producer.utils.PQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/generate/")
@RestController
public class GenerateController {

    @Autowired
    private GenerateService service;

    public B list(PQuery<Map> query) {
//        service.queryTableList(query.getData())
        return null;
    }
}
