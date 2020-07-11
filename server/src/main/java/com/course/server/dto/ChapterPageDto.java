package com.course.server.dto;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/11 17:12
 */
public class ChapterPageDto extends PageDto {
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "ChapterPageDto{" +
                "courseId='" + courseId + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
