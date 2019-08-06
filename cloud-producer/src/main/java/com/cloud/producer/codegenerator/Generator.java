package com.cloud.producer.codegenerator;

import com.cloud.producer.codegenerator.entity.ColumnEntity;
import com.cloud.producer.codegenerator.entity.TableEntity;
import com.cloud.producer.codegenerator.entity.columntype.Type;
import com.google.common.collect.Lists;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Generator {

    private static String packagePath = "com.cloud.producer";

    public static void generate(Map<String, Object> table, List<Map<String, Object>> columns, ZipOutputStream zip){
        List<String> templates = getTemplates();

        TableEntity tableEntity = new TableEntity();
        tableEntity.setPackagePath(packagePath);
        tableEntity.setTableName((String)table.get("tableName"));
        tableEntity.setEntityName(getEntityName((String)table.get("tableName")));
        tableEntity.setComment((String)table.get("tableComment"));

        ArrayList<ColumnEntity> columnEntries = Lists.<ColumnEntity>newArrayList();
        columns.forEach(map->{
            columnEntries.add(convertColumn(map));
        });
        tableEntity.setColumns(columnEntries);

        templates.forEach(templateName->{
            //目标内容
            String content = mergeTemplate(tableEntity, templateName);
            ZipEntry entry = new ZipEntry(getPath(templateName) + getFileName(tableEntity, templateName));

            try {
                zip.putNextEntry(entry);
                zip.write(content.getBytes());
                zip.closeEntry();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
//            createFile(tableEntity, templateName, content);
        });
    }

    private static void createFile(TableEntity tableEntry, String templateName, String content) {
        try {
            String path = prepareDirectory(templateName);
            String fileName = getFileName(tableEntry, templateName);
            FileWriter fileWriter = new FileWriter(path + fileName);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileName(TableEntity tableEntry, String templateName) {
        //entity.java.vm 截取entity.java
        String suffix = templateName.substring(0, templateName.indexOf(".",
                templateName.indexOf(".") + 1));
        StringBuilder fileName = new StringBuilder();
        if (suffix.startsWith("mapper")) {
            return fileName.append("mapper-").append(tableEntry.getEntityName().toLowerCase()).append(".xml").toString();
        }
        fileName.append(tableEntry.getEntityName()).append(firstCharToUpperCase(suffix));
        return fileName.toString();
    }

    private static String prepareDirectory(String templateName) {
        String path = getPath(templateName);
        File dir = new File(path);
        if (!dir.exists()&&!dir.mkdirs()) {
            System.out.println("创建目录失败");
        }
        return path;
    }

    private static String getPath(String templateName) {
        //entity.java.vm 截取entity
        String lastNode = templateName.substring(0, templateName.indexOf("."));
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
        return pathBuilder.toString();
    }

    private static String mergeTemplate(TableEntity tableEntry, String templateName) {

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        VelocityContext velocityContext = new VelocityContext(tableEntry);
        Template template = Velocity.getTemplate(/*"src/main/resources/"+*/"template/" + templateName, "UTF-8");
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

    private static List<String> getTemplates(){
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
    private static ColumnEntity convertColumn(Map<String, Object> columnMap) {
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
    private static String getEntityName(String tableName) {
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
    private static String getFiledName(String columnName) {
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
    private static String firstCharToUpperCase(String str){
        if (str == null) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
