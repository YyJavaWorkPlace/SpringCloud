package com.course.server.util.Test;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 16:54
 */
public class Race {
    public static void main(String[] args) {
        Thread rabbit = new RabbitAndTurtle();
        Thread turtle=new RabbitAndTurtle();
        rabbit.setName("兔子");
        turtle.setName("乌龟");
        rabbit.start();
        turtle.start();
    }

}
