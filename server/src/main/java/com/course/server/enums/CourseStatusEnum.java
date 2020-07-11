package com.course.server.enums;

/**
 * 课程状态枚举类
 *
 * @author yk
 * @version 1.0
 * @date 2020/7/8 17:55
 */
public enum CourseStatusEnum {
    /**
     * 课程已发布状态
     */
    PUBLISH("P", "发布"),
    /**
     * 课程草稿状态
     */
    DRAFT("D", "草稿");
    private String code;
    private String desc;

    CourseStatusEnum(String code, String desc) {
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
