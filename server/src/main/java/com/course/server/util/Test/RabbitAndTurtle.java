package com.course.server.util.Test;

import com.course.server.util.ThreadDemo;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 16:43
 * 龟兔赛跑多线程案例
 */
public class RabbitAndTurtle extends Thread {
    public int distance = 30;
    static boolean flag = true;
    public int predistance = 0;

    @Override
    public void run() {
        double ran = Math.random();
        String name = Thread.currentThread().getName();
        while (flag) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (name.equals("乌龟")) {
                if (Math.random() < 1) {
                    predistance += 1;
                    System.out.println(name + "我跑了:" + predistance + "米");
                    if (predistance ==distance) {
                        System.out.println("---------乌龟胜利--------");
                        flag = false;
                        break;
                    }
                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (name.equals("兔子")) {
                if (Math.random() < 0.3) {
                    predistance += 2;
                    System.out.println(name + "我跑了:" + predistance + "米");
                    if (predistance == distance) {
                        System.out.println("-------兔子赢了------");
                        flag = false;
                        break;
                    }
                }
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
