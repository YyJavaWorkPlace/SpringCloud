package com.course.server.mapper.my;

import org.apache.ibatis.annotations.Param;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/11 21:11
 */
public interface MyChapterMapper {
   int updateTime(String courseId);
}
