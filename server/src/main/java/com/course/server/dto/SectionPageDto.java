package com.course.server.dto;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/11 18:46
 */
public class SectionPageDto extends PageDto {
    private String courseId;
    private String chapterId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        return "SectionPageDto{" +
                "chapterId='" + chapterId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
