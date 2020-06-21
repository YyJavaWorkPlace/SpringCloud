package com.course.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "Success";
    }
}
