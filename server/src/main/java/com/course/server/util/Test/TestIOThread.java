package com.course.server.util.Test;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 17:57
 */
public class TestIOThread {
    public static void main(String[] args) {
        CopyFile cf1 = new CopyFile("C:\\Users\\Fight\\Desktop\\三资报表测试数据\\新建文本文档.txt","C:\\Users\\Fight\\Desktop\\三资报表测试数据\\新建文本文档2.txt");
        cf1.start();
    }
}
