package com.course.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/21 23:14
 * @desc SpringBoot静态资源配置 静态资源包含图片 CSS JS
 * /f 是一个请求标识 静态资源
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**").addResourceLocations("file:F:/uploadVideo/course/");
    }
}
