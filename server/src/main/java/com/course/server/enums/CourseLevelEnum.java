package com.course.server.enums;

/**
 * 课程水平等级枚举类
 *
 * @author yk
 * @version 1.0
 * @date 2020/7/8 17:59
 */
public enum CourseLevelEnum {
    /**
     * 课程水平初级
     */
    ONE("1", "初级"),
    /**
     * 课程水平中级
     */
    TWO("2", "中级"),
    /**
     * 课程水平高级
     */
    THREE("3", "高级");

    private String code;
    private String desc;

    CourseLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
