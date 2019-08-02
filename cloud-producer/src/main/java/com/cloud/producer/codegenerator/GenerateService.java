package com.cloud.producer.codegenerator;

import com.cloud.producer.dao.TableDao;
import com.cloud.producer.utils.PPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class GenerateService {

    @Autowired
    private TableDao tableDao;

    public PPage<Map> queryTableList(String tableName, PPage pg){
        Page<Map> page = PageHelper.startPage(pg.getCurrent(), pg.getSize(), true);
        List<Map<String, Object>> tables = tableDao.queryTableList(tableName);
        pg.setCurrent(page.getPageNum());
        pg.setPage(page.getPages());
        pg.setTotal(page.getTotal());
        pg.setList(tables);
        return pg;
    }

    public byte[] generateCode(String tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        Arrays.asList(tableNames.split(",")).forEach(tableName->{
            Map<String, Object> table = tableDao.queryTable(tableName);
            List<Map<String, Object>> columns = tableDao.queryColumns(tableName);
            Generator.generate(table, columns, zip);
        });
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

}
