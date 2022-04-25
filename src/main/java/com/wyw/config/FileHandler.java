package com.wyw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 鱼酥不是叔
 * */
@Configuration
public class FileHandler  implements WebMvcConfigurer {
    @Value("${company.img.path}")
    String comImgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "file:/"+comImgPath,
                "classpath:/META-INF/resources/",
                "classpath:/resources/",
                "classpath:/static/",
                "classpath:/public/",
                "classpath:/static/upload/"
        );
    }
}