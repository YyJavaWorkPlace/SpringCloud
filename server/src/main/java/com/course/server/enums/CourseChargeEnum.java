package com.course.server.enums;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/8 18:07
 */
public enum CourseChargeEnum {
    /**
     * 收费
     */
    CHARGE("C", "收费"),
    /**
     * 免费
     */
    FREE("F", "免费");

    private String code;

    private String desc;

    CourseChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
