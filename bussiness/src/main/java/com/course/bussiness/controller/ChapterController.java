package com.course.bussiness.controller;

import com.course.server.domain.Chapter;
import com.course.server.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/chapter")
    public List<Chapter> findall(){
        return chapterService.list();
    }
}
