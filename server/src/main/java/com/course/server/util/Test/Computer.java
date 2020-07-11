package com.course.server.util.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 14:11
 */
public class Computer {
    private List<String> parts = new ArrayList<String>();

    public void Add(String part) {
        parts.add(part);
    }

    public void Show() {
        parts.forEach(System.out::println);
        System.out.println("电脑组装完成,请验收");
    }
}
