package com.course.server.util.Test;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 14:21
 */
public class Director {
    /**
     * 指挥装机人员组装电脑
     *
     * @param builder
     */
    public void Construct(Builder builder) {
        builder.BuildCPU();
        builder.BuildMainboard();
        builder.BuildHD();
    }
}
