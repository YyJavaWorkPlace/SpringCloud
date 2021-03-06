package com.course.business.controller.admin;

import com.course.server.domain.CourseCategory;
import com.course.server.dto.*;
import com.course.server.exception.ValidatorException;
import com.course.server.service.CourseCategoryService;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {
    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程";
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseCategoryService coursecategoryService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        //保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);
        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        return responseDto;
    }

    /**
     * 查找课程分类记录
     *
     * @param courseId
     * @return
     */
    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable("courseId") String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategoryDto> dtoList = coursecategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

    /**
     * 查询课程id
     *
     * @param courseId
     * @return
     */
    @PostMapping("/find-content/{courseId}")
    public ResponseDto findContent(@PathVariable("courseId") String courseId) {
        ResponseDto responseDto = new ResponseDto();
        CourseContentDto content = courseService.findContent(courseId);
        responseDto.setContent(content);
        return responseDto;
    }

    /**
     * 保存、更新课程内容
     *
     * @param courseContentDto
     * @return
     */
    @PostMapping("/save-content")
    public ResponseDto saveContent(@RequestBody CourseContentDto courseContentDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.saveContent(courseContentDto);
        return responseDto;
    }

    @PostMapping("/sort-course")
    public ResponseDto sortCourse(@RequestBody CourseSortDto courseSortDto) {
        LOG.info("更新排序");
        ResponseDto responseDto = new ResponseDto();
        courseService.updateSort(courseSortDto);
        return responseDto;
    }
}
