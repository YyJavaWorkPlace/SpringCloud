package com.course.server.service;

import com.course.server.domain.CourseContent;
import com.course.server.domain.CourseContentExample;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * CourseContent 大章表业务逻辑
 */
@Service
public class CourseContentService {
    @Resource
    private CourseContentMapper courseContentMapper;

    /**
     * 查询课程大章列表
     *
     * @return
     */
    public void list(PageDto pageDto) {
        //遇到的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseContentExample courseContentExample = new CourseContentExample();

        List<CourseContent> courseContentList = courseContentMapper.selectByExample(courseContentExample);
        PageInfo<CourseContent> pageInfo = new PageInfo<>(courseContentList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseContentDto> courseContentDtos = CopyUtil.copyList(courseContentList, CourseContentDto.class);
        pageDto.setList(courseContentDtos);
    }

    public void save(CourseContentDto courseContentDto) {
        CourseContent courseContent = CopyUtil.copy(courseContentDto, CourseContent.class);
        if (StringUtils.isEmpty(courseContentDto.getId())) {
            this.insert(courseContent);
        } else {
            this.update(courseContent);
        }
    }

    private void insert(CourseContent courseContent) {
        courseContent.setId(UuidUtil.getShortUuid());
        courseContentMapper.insert(courseContent);
    }

    private void update(CourseContent courseContent) {
        courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
    }

    public void delete(String id) {
        courseContentMapper.deleteByPrimaryKey(id);
    }
}
