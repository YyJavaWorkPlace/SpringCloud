package com.course.generator.server;

import com.course.generator.util.DbUtil;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.transform.sax.SAXResult;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ServerGenerator {
    static String MODULE = "business";
    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    static String toDtoPath = "server\\src\\main\\java\\com\\course\\server\\dto\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\com\\course\\business\\controller\\admin\\";
    static String generatorConfigPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws IOException, TemplateException, SQLException, DocumentException {
        // 读取XML文件
        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        //读取xml文件到Document中
        Document doc = reader.read(file);
        //获取xml文件的根节点
        Element rootElement = doc.getRootElement();
        //读取context借点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个"table"节点
        tableElement=contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName= tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain=Domain.substring(0,1).toLowerCase()+Domain.substring(1);
        System.out.println("表:"+tableElement.attributeValue("tableName"));
        System.out.println("Domian:"+tableElement.attributeValue("domainObjectName"));
        String module = MODULE;
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);
//        FreemarkUtil.initConfig("service.ftl");
//        FreemarkUtil.generator(toServicePath + Domain + "Service.java", map);
//
        FreemarkUtil.initConfig("controller.ftl");
        FreemarkUtil.generator(toControllerPath + Domain + "Controller.java", map);

//        //生成dto
//        FreemarkUtil.initConfig("dto.ftl");
//        FreemarkUtil.generator(toDtoPath + Domain + "Dto.java", map);
    }

    /**
     * 获取所有JAVA类型 使用Set去重
     *
     * @param fieldList
     * @return
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
