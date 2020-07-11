package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * Section 大章表业务逻辑
 */
@Service
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;
    @Resource
    private CourseService courseService;

    /**
     * 查询课程大章列表
     *
     * @return
     */
    public void list(SectionPageDto sectionPageDto) {
        //遇到的第一个select语句会进行分页
        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (!StringUtils.isEmpty(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        if (!StringUtils.isEmpty(sectionPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        sectionExample.setOrderByClause("sort asc");

        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtos = CopyUtil.copyList(sectionList, SectionDto.class);
        sectionPageDto.setList(sectionDtos);
    }

    /**
     * 一次操作会更新或修改多张表，一般为了保证数据一致,需要增加事务处理。
     * 插入或更新 [小节表] 都要去[更新课程表]中的课程时长
     * throw new Exception("抛出Exception时事务无效");
     * 自定义异常一般可以选择继承RuntimeException
     * 一个类的内部方法相互调用 methodA调用methodB methodB事务不起作用，Spring 的事务
     * 处理是利用AOP生成动态代理 内部方法调用时不经过代理类所以事务不生效
     * @param sectionDto
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    private void insert(Section section) {
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    private void update(Section section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
