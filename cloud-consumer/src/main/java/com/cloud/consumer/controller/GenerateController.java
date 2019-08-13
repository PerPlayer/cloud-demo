package com.cloud.consumer.controller;

import com.cloud.consumer.service.feign.GenerateService;
import com.cloud.consumer.utils.B;
import com.cloud.consumer.utils.PQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "Generator Api", tags = "代码生成控制器", basePath = "/generate/")
@RequestMapping("/generate/")
@RestController
public class GenerateController {

    @Autowired
    private GenerateService service;

    @ApiOperation(value = "list", notes = "查询表详情")
    @GetMapping("list")
    public B list(PQuery<String> pQuery) {
        B b = service.list(pQuery);
        return b;
    }

    @ApiOperation(value = "code", notes = "生成指定表代码")
    @GetMapping("code")
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
