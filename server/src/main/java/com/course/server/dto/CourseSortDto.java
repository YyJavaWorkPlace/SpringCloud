package com.course.server.dto;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/19 12:02
 *
 */
public class CourseSortDto {
    /**
     * ID
     */
    private String id;
    /**
     * 当前排序
     */
    private Integer oldSort;
    /**
     * 新排序
     */
    private Integer newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getOldSort() {
        return oldSort;
    }

    public void setOldSort(Integer oldSort) {
        this.oldSort = oldSort;
    }

    public Integer getNewSort() {
        return newSort;
    }

    public void setNewSort(Integer newSort) {
        this.newSort = newSort;
    }
}
