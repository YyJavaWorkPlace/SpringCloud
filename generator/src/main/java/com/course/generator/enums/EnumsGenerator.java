package com.course.generator.enums;

import com.course.server.enums.SectionChargeEnum;

import java.io.*;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/7 18:50
 */
public class EnumsGenerator {
    /**
     * JS文件输出路径
     */
    static String path = "admin\\public\\static\\js\\enums.js";
    private static Pattern pattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线，第一位是下划线
     * 如：SectionChargeEnum 变成 _section_charge_enum
     *
     * @param str
     * @return
     */
    private static StringBuffer underLine(String str) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            //将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb;
    }

    /**
     * 功能：驼峰转大写下划线，并去掉_ENUM
     * 如：SectionChargeEnum 变 _section_charge_enum 变成 SECTION_CHARGE
     * replaceAll 匹配不到还是返回原字符串
     * 这里直接预判都有_enum
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        String sb = underLine(str).toString();
        return sb.substring(1, sb.length()).replaceAll("_enum", "").toUpperCase();
    }

    /**
     * 写文件
     * 高效缓冲流读写速度是非常快的
     * --------------------------------------------------------------
     * 👉字节缓冲流 （高效流）BufferedInputStream BufferedOutputStream
     * 字节流 InputStream OutputStream 基类
     * 👉字节文件操作流 FileInputStream FileOutputStream
     * ------------------------------------------------------------
     * 👉字符缓冲流 BufferedReader BufferedWriter
     * 字符流 Reader Writer 基类
     * 👉字符转换流 InputStreamReader OutputStreamWriter  直接子类 默认字符编码 FileWriter FileWriter
     * try resource finally 语法糖
     *
     * @param sb
     */
    public static void writeJs(StringBuffer sb) {
        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));) {
            System.out.println("-----准备写入文件------");
            System.out.println("路径为:" + path);
            bufferedWriter.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("已经关闭流");
        }
    }

    private static void toJson(Class clazz, StringBuffer bufferObject, StringBuffer bufferArray) throws Exception {
        String key = toUnderline(clazz.getSimpleName());
        toJson(clazz, key, bufferObject, bufferArray);
    }
    /**
     * 使用反射生成js中固定格式的数据
     *
     * @param clazz        枚举类
     * @param key          测试KEY、CHARGE
     * @param bufferObject 测试KEY = {CHARGEE:{key: "C", value: "收费"}, FREE: {key: "F", value: "免费"}};
     * @param bufferArray  CHARGE = [{key: "C", value: "收费"}, {key: "F", value: "免费"}];
     * @throws Exception
     */
    private static void toJson(Class clazz, String key, StringBuffer bufferObject, StringBuffer bufferArray) throws Exception {
        Object[] enumConstants = clazz.getEnumConstants();
        Method name = clazz.getMethod("name");
        Method getDesc = clazz.getMethod("getDesc");
        Method getCode = clazz.getMethod("getCode");
        //开始生成对象 SECTION_CHARGE =
        bufferObject.append(key).append("={");
        for (int i = 0; i < enumConstants.length; i++) {
            Object obj = enumConstants[i];
            if (getCode == null) {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(name.invoke(obj)).append("\", value: \"").append(getDesc.invoke(obj)).append("\"}");
            } else {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(getCode.invoke(obj)).append("\", value: \"").append(getDesc.invoke(obj)).append("\"}");
            }
            if (i < enumConstants.length - 1) {
                bufferObject.append(",");
            }
        }
        bufferObject.append("};\r\n");

        // 开始生成数组
        bufferArray.append(key).append("=[");
        for (int i = 0; i < enumConstants.length; i++) {
            Object object = enumConstants[i];
            if (getCode != null) {
                bufferArray.append("{key:\"").append(getCode.invoke(object)).append("\", value: \"").append(getDesc.invoke(object)).append("\"}");
            } else {
                bufferArray.append("{key:\"").append(name.invoke(object)).append("\", value: \"").append(getDesc.invoke(object)).append("\"}");
            }
            if (i < enumConstants.length - 1) {
                bufferArray.append(",");
            }
        }
        bufferArray.append("];\r\n");
    }

    public static void main(String[] args) {
        StringBuffer bufferObject = new StringBuffer();
        StringBuffer bufferArray = new StringBuffer();
        long begin = System.currentTimeMillis();
        try {
            toJson(SectionChargeEnum.class, bufferObject, bufferArray);
            StringBuffer buffer = bufferObject.append("\r\n").append(bufferArray);
            writeJs(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行耗时:" + (end - begin) + " 毫秒");
    }

}
