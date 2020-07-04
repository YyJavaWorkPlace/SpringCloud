package com.course.generator.server;

import com.course.generator.util.FreemarkUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class ServerGenerator {
    static String MODULE="business";
    static String toServicePath="server\\src\\main\\java\\com\\course\\server\\service\\";
    static String toControllerPath=MODULE+"\\src\\main\\java\\com\\course\\business\\controller\\admin\\";
    public static void main(String[] args) throws IOException, TemplateException {
        String Domain = "Section";
        String domain="section";
        String tableNameCn="小节";
        String module=MODULE;
        Map<String,Object> map= new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("tableNameCn",tableNameCn);
        map.put("module",module);
        FreemarkUtil.initConfig("service.ftl");
        FreemarkUtil.generator(toServicePath+Domain+"Service.java",map);

        FreemarkUtil.initConfig("controller.ftl");
        FreemarkUtil.generator(toControllerPath+Domain+"Controller.java",map);
    }
}
