package com.course.server.service;

import com.course.server.converter.Chapter2ChapterDTOConverter;
import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Chapter 大章表业务逻辑
 */
@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    /**
     * 查询课程大章列表
     *
     * @return
     */
    public void list(PageDto pageDto) {
        //遇到的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(Chapter2ChapterDTOConverter.convert(chapterList));
    }

    public void save(ChapterDto chapterDto) {
        Chapter chapter = Chapter2ChapterDTOConverter.convert(chapterDto);
        if (StringUtils.isEmpty(chapterDto.getId())) {
            this.insert(chapter);
        } else {
            this.update(chapter);
        }
    }

    private void insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }
}
