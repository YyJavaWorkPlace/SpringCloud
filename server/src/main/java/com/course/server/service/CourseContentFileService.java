package com.course.server.service;

import com.course.server.domain.CourseContentFile;
import com.course.server.domain.CourseContentFileExample;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseContentFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * CourseContentFile 大章表业务逻辑
 */
@Service
public class CourseContentFileService {
    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * 查询课程大章列表
     *
     * @return
     */
    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample courseContentFileExample = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = courseContentFileExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> courseContentFileList = courseContentFileMapper.selectByExample(courseContentFileExample);
        return CopyUtil.copyList(courseContentFileList, CourseContentFileDto.class);
    }

    public void save(CourseContentFileDto courseContentFileDto) {
        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto, CourseContentFile.class);
        if (StringUtils.isEmpty(courseContentFileDto.getId())) {
            this.insert(courseContentFile);
        } else {
            this.update(courseContentFile);
        }
    }

    private void insert(CourseContentFile courseContentFile) {
        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }

    private void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }
}
