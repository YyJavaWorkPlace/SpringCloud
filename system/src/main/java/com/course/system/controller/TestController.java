package com.course.system.controller;

import com.course.server.domain.Test;
import com.course.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * 接口
 */
@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @RequestMapping(value="/test",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Test> test()
    {
        return testService.list();
    }
}
