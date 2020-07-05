package com.course.generator.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbUtil {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/courseimooc";
            String user = "courseimooc";
            String password = "123456";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 获得表注解
     *
     * @param tableName
     * @return
     */
    public static String getTableComment(String tableName) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select table_comment from information_schema.tables where TABLE_NAME='" + tableName + "'");
        String tableNameCH = "";
        if (rs != null) {
            while (rs.next()) {
                tableNameCH = rs.getString("table_comment");
                break;
            }
            rs.close();
        }
        statement.close();
        conn.close();
        System.out.println("表名:" + tableNameCH);
        return tableNameCH;
    }

    /**
     * 获取表的列
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public static List<Field> getColumnByTableName(String tableName) throws SQLException {
        List<Field> fieldList = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show full columns from " + tableName + "");
        if (rs != null) {
            while (rs.next()) {
                Field field = new Field();
                String type = rs.getString("Type");
                String comment = rs.getString("Comment");
                String nullAble = rs.getString("Null"); //YES NO
                field.setName(rs.getString("Field"));
                field.setNameHump(lineToHump(rs.getString("Field")));
                field.setNameBigHump(lineToBigHump(rs.getString("Field")));
                field.setType(type);
                field.setJavaType(DbUtil.sqlTypeToJavaType(type));
                field.setComment(comment);
                if (comment.contains("|")) {
                    field.setNameCn(comment.substring(0, comment.indexOf("|")));
                } else {
                    field.setNameCn(comment);
                }
                field.setNullAble("YES".equals(nullAble));
                if (type.toUpperCase().contains("varchar".toUpperCase())) {
                    String lengthStr = type.substring(type.indexOf("(") + 1, type.length() - 1);
                    field.setLength(Integer.valueOf(lengthStr));
                } else {
                    field.setLength(0);
                }
                if (comment.contains("枚举")) {
                    field.setEnums(true);
                    //以课程等级为例子 从注释中的枚举得到Course_LEVEL
//                    int start = comment.indexOf("[");
//                    int end = comment.indexOf("]");
//                    String enumsName = comment.substring(start + 1, end);
//                    String enumsConst = EnumGenerator.toUnderline(enumsName);
//                    field.setEnumsConst(enumsConst);
                } else {
                    field.setEnums(false);
                }
                fieldList.add(field);
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("列信息：" + fieldList);
        return fieldList;
    }

    /**
     * 下划线转小驼峰
     *
     * @param field
     * @return
     */
    private static String lineToHump(String field) {
        //生成Pattern对象并且编译一个简单的正则表达式
        Pattern linePattern = Pattern.compile("_(\\w)");
        field = field.toLowerCase();
        //用Pattern类的matcher()方法生成一个Matcher对象
        Matcher matcher = linePattern.matcher(field);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        //最后一次匹配工作后剩余的字符串添加到一个StringBuffer对象里。
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转大驼峰
     *
     * @param field
     * @return
     */
    private static String lineToBigHump(String field) {
        String s = lineToHump(field);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 数据库类型转换为JAVA类型
     *
     * @param sqlType
     * @return
     */
    private static String sqlTypeToJavaType(String sqlType) {
        if (sqlType.toUpperCase().contains("varchar".toUpperCase())
                || sqlType.toUpperCase().contains("char".toUpperCase())
                || sqlType.toUpperCase().contains("text".toUpperCase())) {
            return "String";
        } else if (sqlType.toUpperCase().contains("datetime".toUpperCase())) {
            return "Date";
        } else if (sqlType.toUpperCase().contains("int".toUpperCase())) {
            return "Integer";
        } else if (sqlType.toUpperCase().contains("long".toUpperCase())) {
            return "Long";
        } else if (sqlType.toUpperCase().contains("decimal".toUpperCase())) {
            return "BigDecimal";
        } else {
            return "String";
        }

    }

}