package com.cloud.producer.codegenerator;

import com.cloud.producer.utils.B;
import com.cloud.producer.utils.PPage;
import com.cloud.producer.utils.PQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/api/generate/")
@RestController
public class GenerateController {

    @Autowired
    private GenerateService service;

    @RequestMapping("list")
    public B list(@RequestBody PQuery<String> query) {
        PPage<Map> mapPPage = service.queryTableList(query.getData(), query.getPage());
        return B.ok().put("page", mapPPage);
    }

    @RequestMapping("code")
    public byte[] code(@RequestBody PQuery<String> query) {
        byte[] data = service.generateCode(query.getData());
        return data;
    }
}
