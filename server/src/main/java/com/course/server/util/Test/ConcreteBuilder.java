package com.course.server.util.Test;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 14:04
 */
public class ConcreteBuilder  extends Builder{
    /**
     * 创建产品示例
     */
    Computer computer = new Computer();
    //组装产品
    @Override
    public void BuildCPU() {
        computer.Add("组装CPU");
    }

    @Override
    public void BuildMainboard() {
        computer.Add("组装主板");
    }

    @Override
    public void BuildHD() {
        computer.Add("组装硬盘");
    }

    @Override
    public Computer GetComputer() {
        return computer;
    }
}
