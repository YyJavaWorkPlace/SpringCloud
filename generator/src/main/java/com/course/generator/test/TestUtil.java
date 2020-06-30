package com.course.generator.test;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestUtil {
    static String ftlPath="generator\\src\\main\\java\\com\\course\\generator\\test\\";
    static String toPath="generator\\src\\main\\java\\com\\course\\generator\\test\\";

    public static void main(String[] args) throws IOException, TemplateException {
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        //读取那个具体的模板
        Template template = cfg.getTemplate("test.ftl");
        //根据模板生成什么文件
        FileWriter fw = new FileWriter(toPath+"Test.java");
        BufferedWriter bw = new BufferedWriter(fw);
        template.process(null,bw);
        bw.flush();
        fw.close();
    }
}
