package com.cloud.producer.service;

import com.cloud.producer.dao.TableDao;
import com.cloud.producer.entry.ColumnEntry;
import com.cloud.producer.entry.TableEntry;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class GenerateService {

    @Autowired
    private TableDao tableDao;

    public byte[] generate(String tableName){
        List<Map<String, Object>> tables = tableDao.queryTableList(tableName);
        tables.forEach(table->{
            TableEntry tableEntry = new TableEntry();
            tableEntry.setPackagePath("com.cloud.producer.entry");
            tableEntry.setTableName((String)table.get("tableName"));
            tableEntry.setEntryName(getFiledName((String)table.get("tableName")));
            tableEntry.setComment((String)table.get("tableComment"));
            List<Map<String, Object>> columns = tableDao.queryColumns(tableEntry.getTableName());

            ArrayList<ColumnEntry> columnEntries = Lists.<ColumnEntry>newArrayList();
            columns.forEach(map->{
                columnEntries.add(convertColumn(map));
            });
            tableEntry.setColumns(columnEntries);

            VelocityContext velocityContext = new VelocityContext(tableEntry);
            Template template = Velocity.getTemplate("src/main/resources/template/entry.vm");
            StringWriter writer = new StringWriter();
            template.merge(velocityContext, writer);
            System.out.println(writer.toString());
            try {
                FileWriter fileWriter = new FileWriter("src/main/java/com/cloud/producer/entry/"+tableEntry.getEntryName()+".java");
                fileWriter.write(writer.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return null;
    }

    private void printMap(Map<String, Object> crawler) {
        System.out.println("------------------------------");
        for (Map.Entry<String, Object> entry : crawler.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
//        System.out.println("tableName: " + crawler.get("tableName"));
    }

    private ColumnEntry convertColumn(Map<String, Object> columnMap) {
        ColumnEntry columnEntry = new ColumnEntry();
        if ("PRI".equals(columnMap.get("columnKey"))) {
            columnEntry.setKey("@Id");
            columnEntry.setType("String");
            columnEntry.setLength(32);
        } else if ("int".equals(columnMap.get("dataType"))) {
            columnEntry.setType("int");
            columnEntry.setLength(4);
        } else if (Lists.newArrayList("mediumtext", "varchar").contains(columnMap.get("dataType"))) {
            columnEntry.setType("String");
        } else if ("datetime".equals(columnMap.get("dataType"))) {
            columnEntry.setType("Date");
        }
        columnEntry.setColumnName((String)columnMap.get("columnName"));
        columnEntry.setFieldName(getFiledName((String)columnMap.get("columnName")));
        return columnEntry;
    }

    private String getFiledName(String columnName) {
        StringBuilder sb = new StringBuilder();
        String[] parts = columnName.split("_");
        IntStream.range(0, parts.length).forEach(i->{
            if (i == 0) {
                sb.append(parts[i]);
            }else{
                String part = parts[i];
                sb.append(part.substring(0, 1).toUpperCase());
                sb.append(part.substring(1, part.length()));
            }
        });
        return sb.toString();
    }

}
