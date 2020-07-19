package com.course.server.service;

import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseCategoryExample;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.CourseSortDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * CourseCategory 大章表业务逻辑
 */
@Service
public class CourseCategoryService {
    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 查询课程大章列表
     *
     * @return
     */
    public void list(PageDto pageDto) {
        //遇到的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtos = CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
        pageDto.setList(courseCategoryDtos);
    }

    public void save(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategory.class);
        if (StringUtils.isEmpty(courseCategoryDto.getId())) {
            this.insert(courseCategory);
        } else {
            this.update(courseCategory);
        }
    }


    private void insert(CourseCategory courseCategory) {
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    private void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 一对多操作时 更新时直接清空再新增
     * 批量插入课程所属栏目
     * 在更新或插入后 都要先删除一次 在添加新的栏目信息
     * 根据某一课程 先清空课程分类，在保存课程分类
     * 防止外层忘记加事务
     *
     * @param courseId
     * @param dtoList
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
        //先删除课程所有的分类
        CourseCategoryExample example = new CourseCategoryExample();
        CourseCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
        for (int i = 0; i < dtoList.size(); i++) {
            CategoryDto categoryDto = dtoList.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        }
    }

    /**
     * @param courseId 课程ID
     * @return 返回课程分类列表
     */
    public List<CourseCategoryDto> listByCourse(String courseId) {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        CourseCategoryExample.Criteria criteria = courseCategoryExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategories = courseCategoryMapper.selectByExample(courseCategoryExample);
        return CopyUtil.copyList(courseCategories, CourseCategoryDto.class);

    }

}
