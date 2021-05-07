package com.zzyy.outh_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhouy262
 * @date 2021/5/7 11:58
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * springboot 无法直接访问静态资源，需要放开资源访问路径。 添加静态资源文件，外部可以直接访问地址
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
         * addResourceHandler是访问路径前缀，参数可设置多个，以逗号分隔，例：/image/**,/css/**
         * addResourceLocations是对应的资源路径，也可以设置多个。
         */
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
    }
}
