package com.cloud.consumer.controller;

import com.cloud.consumer.service.feign.GenerateService;
import com.cloud.consumer.utils.B;
import com.cloud.consumer.utils.PQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/generate/")
@RestController
public class GenerateController {

    @Autowired
    private GenerateService service;

    @RequestMapping("list")
    public B list(PQuery<String> pQuery) {
        B b = service.list(pQuery);
        return b;
    }

    @RequestMapping("code")
    public void code(String tables, HttpServletResponse response) throws IOException {
        PQuery<String> query = new PQuery<>();
        query.setData(tables);
        byte[] data = service.code(query);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
