package com.course.server.util;

public class Main {
    public static void main(String[] args) {
        ThreadDemo threadDemo1=new ThreadDemo("线程1");
        threadDemo1.run();
        ThreadDemo threadDemo2=new ThreadDemo("线程2");
        threadDemo2.run();
    }
}
