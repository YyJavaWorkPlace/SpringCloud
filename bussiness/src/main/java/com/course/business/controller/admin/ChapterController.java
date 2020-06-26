package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.util.List;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto) {
        LOG.info("pageDto:{}"+pageDto);
        chapterService.list(pageDto);
        return pageDto;
    }
    @RequestMapping("/save")
    public ChapterDto save(@RequestBody  ChapterDto chapterDto) {
        chapterService.save(chapterDto);
        return chapterDto;
    }
}
