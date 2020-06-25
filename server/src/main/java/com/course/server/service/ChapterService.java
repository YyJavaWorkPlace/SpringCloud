package com.course.server.service;

import com.course.server.converter.Chapter2ChapterDTOConverter;
import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

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
     * @return
     */
    public List<ChapterDto> list() {
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        return Chapter2ChapterDTOConverter.convert(chapterList);
    }
}
