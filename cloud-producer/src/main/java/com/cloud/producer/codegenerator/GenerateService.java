package com.cloud.producer.codegenerator;

import com.alibaba.fastjson.JSON;
import com.cloud.producer.codegenerator.entity.ColumnEntity;
import com.cloud.producer.codegenerator.entity.ColumnType;
import com.cloud.producer.codegenerator.entity.TableEntity;
import com.cloud.producer.codegenerator.entity.columntype.Type;
import com.cloud.producer.dao.TableDao;
import com.google.common.collect.Lists;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class GenerateService {

    @Autowired
    private TableDao tableDao;
    String packagePath = "com.cloud.producer";

    String tableData = "[{\"createTime\":1551851882000,\"engine\":\"MyISAM\",\"tableComment\":\"\",\"tableName\":\"crawler_entry\"}]\n";
    String columnsData = "[{\"columnComment\":\"\",\"columnKey\":\"PRI\",\"columnName\":\"id\",\"dataType\":\"varchar\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"create_time\",\"dataType\":\"datetime\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"modify_time\",\"dataType\":\"datetime\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"status\",\"dataType\":\"int\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"version\",\"dataType\":\"int\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"content\",\"dataType\":\"mediumtext\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"task_id\",\"dataType\":\"varchar\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"title\",\"dataType\":\"varchar\",\"extra\":\"\"},{\"columnComment\":\"\",\"columnKey\":\"\",\"columnName\":\"weight\",\"dataType\":\"int\",\"extra\":\"\"}]\n";

    public byte[] generate(String tableName){

        List<String> templates = getTemplates();
        List<Map<String, Object>> tables = Lists.newArrayList(tableDao.queryTable(tableName));
        tables.forEach(table->{
            TableEntity tableEntity = new TableEntity();
            tableEntity.setPackagePath(packagePath);
            tableEntity.setTableName((String)table.get("tableName"));
            tableEntity.setEntityName(getEntityName((String)table.get("tableName")));
            tableEntity.setComment((String)table.get("tableComment"));
            List<Map<String, Object>> columns = tableDao.queryColumns(tableEntity.getTableName());

            System.out.println(JSON.toJSONString(tables));
            System.out.println(JSON.toJSONString(columns));

            ArrayList<ColumnEntity> columnEntries = Lists.<ColumnEntity>newArrayList();
            columns.forEach(map->{
                columnEntries.add(convertColumn(map));
            });
            tableEntity.setColumns(columnEntries);

            templates.forEach(templateName->{
                //目标内容
                String content = mergeTemplate(tableEntity, templateName);
                createFile(tableEntity, templateName, content);
            });

        });
        return null;
    }

    private void createFile(TableEntity tableEntry, String templateName, String content) {
        //entity.java.vm
        String lastNode = templateName.substring(0, templateName.indexOf("."));//截取entity
        String suffix = templateName.substring(0, templateName.indexOf(".",
                templateName.indexOf(".") + 1));//截取entity.java
        try {
            String path = prepareDirectory(lastNode);
            String fileName = getFileName(tableEntry, suffix);
            FileWriter fileWriter = new FileWriter(path + fileName);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(TableEntity tableEntry, String suffix) {
        StringBuilder fileName = new StringBuilder();
        if (suffix.startsWith("mapper")) {
            return fileName.append("mapper-").append(tableEntry.getEntityName().toLowerCase()).append(".xml").toString();
        }
        fileName.append(tableEntry.getEntityName()).append(firstCharToUpperCase(suffix));
        return fileName.toString();
    }

    private String prepareDirectory(String lastNode) {
        StringBuilder pathBuilder = new StringBuilder();
        switch (lastNode) {
            case "mapper":{
                pathBuilder.append("src/main/resources/mapper/");
            }
            break;
            case "test":{
                pathBuilder.append("src/test/java/");
            }
            break;
            default:{
                pathBuilder.append("src/main/java/");
                pathBuilder.append(packagePath.replaceAll("\\.", "/")).append("/");
                if (lastNode.equals("impl")) {
                    pathBuilder.append("service/");
                }
                pathBuilder.append(lastNode).append("/");
            }
        }
        String path = pathBuilder.toString();
        File dir = new File(path);
        if (!dir.exists()&&!dir.mkdirs()) {
            System.out.println("创建目录失败");
        }
        return path;
    }

    private String mergeTemplate(TableEntity tableEntry, String templateName) {
        VelocityContext velocityContext = new VelocityContext(tableEntry);
        Template template = Velocity.getTemplate("src/main/resources/template/" + templateName, "UTF-8");
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        String content = writer.toString();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private List<String> getTemplates(){
        ArrayList<String> templates = Lists.newArrayList();
        templates.add("entity.java.vm");
        templates.add("model.java.vm");
        templates.add("mapper.xml.vm");
        templates.add("dao.java.vm");
        templates.add("service.java.vm");
        templates.add("impl.java.vm");
        templates.add("test.java.vm");
        templates.add("controller.java.vm");
        return templates;
    }

    /**
     * 提取列名
     * @param columnMap
     * @return
     */
    private ColumnEntity convertColumn(Map<String, Object> columnMap) {
        ColumnEntity columnEntity = new ColumnEntity();
        //适配列
        Type.init(columnMap).eval().fileEntity(columnEntity);
        columnEntity.setComment((String)columnMap.get("columnComment"));
        columnEntity.setColumnName((String)columnMap.get("columnName"));
        columnEntity.setFieldName(getFiledName((String)columnMap.get("columnName")));
        return columnEntity;
    }

    /**
     * 返回驼峰表名，会删除第一个"_"之前的字符串
     * @param tableName
     * @return
     */
    private String getEntityName(String tableName) {
        if (tableName.contains("_")) {
            tableName = tableName.substring(tableName.indexOf("_"));
        }
        return firstCharToUpperCase(getFiledName(tableName));
    }

    /**
     * 返回驼峰字符串
     * @param columnName
     * @return
     */
    private String getFiledName(String columnName) {
        StringBuilder sb = new StringBuilder();
        String[] parts = columnName.split("_");
        IntStream.range(0, parts.length).forEach(i->{
            if (i == 0) {
                sb.append(parts[i]);
            }else{
                sb.append(firstCharToUpperCase(parts[i]));

            }
        });
        return sb.toString();
    }

    /**
     * 首字母转大写
     * @param str
     * @return
     */
    private String firstCharToUpperCase(String str){
        if (str == null) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
