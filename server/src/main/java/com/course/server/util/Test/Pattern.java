package com.course.server.util.Test;

import java.nio.file.DirectoryIteratorException;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 14:19
 */
public class  Pattern {
    public static void main(String[] args) {
        //逛了很久终于发现一家合适的电脑店
        //找到该店的老板和装机人员
        //将一个复杂的对象的构建与表示分离。
        Director director = new Director();
        Builder builder = new ConcreteBuilder();
        //沟通需求后 老板叫装机人员去装电脑
        director.Construct(builder);
        //装完后 组装人员搬来组装好的电脑
        Computer computer = builder.GetComputer();
        //组装人员展示电脑给小成看
        computer.Show();
    }
}
