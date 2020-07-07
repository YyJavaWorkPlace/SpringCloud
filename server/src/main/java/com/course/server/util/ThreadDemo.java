package com.course.server.util;

import static java.lang.Thread.sleep;

public class ThreadDemo implements Runnable {
    private String name;
    private int i = 0;
    public ThreadDemo(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name+"线程运行："+i);
            try {
                sleep((int) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
