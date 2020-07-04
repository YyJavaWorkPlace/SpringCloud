package com.course.generator.server;

import com.course.generator.util.FreemarkUtil;
import freemarker.template.TemplateException;

import java.io.IOException;

public class ServerGenerator {
    static String toPath="generator\\src\\main\\java\\com\\course\\generator\\test\\";
    public static void main(String[] args) throws IOException, TemplateException {
        FreemarkUtil.initConfig("test.ftl");
        FreemarkUtil.generator(toPath+"Test.java");
    }
}
