package com.course.server.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


public class CourseDto {

    /*
     *id
     */

    private String id;
    /*
     *名称
     */

    private String name;
    /*
     *概述
     */

    private String summary;
    /*
     *时长|单位秒
     */

    private Integer time;
    /*
     *价格(元)
     */

    private BigDecimal price;
    /*
     *封面
     */

    private String image;
    /*
     *级别|ONE("1","初级"),TWO("2","中级")，THREE("3","高级")
     */

    private String level;
    /*
     *收费|CHARGE("C","收费"),FREE("F","免费")
     */

    private String charge;
    /*
     *状态|PUBLISH("P","发布"),DRAFT("D","草稿")
     */

    private String status;
    /*
     *报名数量
     */

    private Integer enroll;
    /*
     *顺序
     */

    private Integer sort;
    /*
     *创建时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;
    /*
     *修改时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;
    /**
     * 课程分类
     */
    private List<CategoryDto> categorys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEnroll() {
        return enroll;
    }

    public void setEnroll(Integer enroll) {
        this.enroll = enroll;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CategoryDto> getCategorys() {
        return categorys;
    }

    public void setCategorysy(List<CategoryDto> categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", time=" + time +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", level='" + level + '\'' +
                ", charge='" + charge + '\'' +
                ", status='" + status + '\'' +
                ", enroll=" + enroll +
                ", sort=" + sort +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", categorys=" + categorys +
                '}';
    }
}