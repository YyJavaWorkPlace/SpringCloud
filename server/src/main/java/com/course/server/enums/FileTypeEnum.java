package com.course.server.enums;

/**
 * 文件类型枚举类
 *
 * @author yk
 * @version 1.0
 * @date 2020/7/25 23:44
 */
public enum FileTypeEnum {
    /**
     * 课程类型文件
     */
    COURSE("C", "课程"),
    /**
     * 讲师信息文件
     */
    TEACHER("T", "讲师");

    private String code;
    private String desc;

    FileTypeEnum(String code, String desc) {
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

    public static FileTypeEnum getByCode(String code) {
        for (FileTypeEnum e : FileTypeEnum.values()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }
}
