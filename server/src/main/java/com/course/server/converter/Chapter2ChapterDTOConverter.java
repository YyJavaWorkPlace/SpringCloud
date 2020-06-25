package com.course.server.converter;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 封装转换类型
 */
public class Chapter2ChapterDTOConverter {
    public static ChapterDto convert(Chapter chapter) {
        ChapterDto dto = new ChapterDto();
        BeanUtils.copyProperties(chapter, dto);
        return dto;
    }

    /**
     * lamda 转换数据传输层对象
     * @param chapterList
     * @return
     */
    public static List<ChapterDto> convert(List<Chapter> chapterList) {
        return chapterList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
