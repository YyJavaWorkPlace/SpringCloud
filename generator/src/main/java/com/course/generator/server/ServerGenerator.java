package com.course.generator.server;

import com.course.generator.util.DbUtil;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ServerGenerator {
    static String MODULE = "business";
    static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    static String toDtoPath = "server\\src\\main\\java\\com\\course\\server\\dto\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\com\\course\\business\\controller\\admin\\";

    public static void main(String[] args) throws IOException, TemplateException, SQLException {
        String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";
        String module = MODULE;
        List<Field> fieldList = DbUtil.getColumnByTableName(domain);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("module", module);
        map.put("fieldList",fieldList);
        map.put("typeSet", typeSet);
        FreemarkUtil.initConfig("service.ftl");
        FreemarkUtil.generator(toServicePath + Domain + "Service.java", map);

        FreemarkUtil.initConfig("controller.ftl");
        FreemarkUtil.generator(toControllerPath + Domain + "Controller.java", map);

        //生成dto
        FreemarkUtil.initConfig("dto.ftl");
        FreemarkUtil.generator(toDtoPath + Domain + "Dto.java", map);
    }

    /**
     * 获取所有JAVA类型 使用Set去重
     * @param fieldList
     * @return
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i<fieldList.size();i++){
            Field field=fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
