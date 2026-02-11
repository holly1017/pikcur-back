package com.pikcurchu.pikcur.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // "모든 경로(/**)에 대해"
                                .allowedOrigins("http://localhost:3000") // "이 손님(리액트 서버)은 허용해라"
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // "이런 요청들은 다 허용하고"
                                .allowedHeaders("*") // "헤더에 뭘 달고 오든 신경 쓰지 마"
                                .allowCredentials(true) // "쿠키 같은 것도 허용"
                                .maxAge(3600);
        }

        // @Override
        // public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // String userDir = System.getProperty("user.dir");

        // registry.addResourceHandler("/images/goods/**")
        // .addResourceLocations("file:" + userDir + "/uploads/images/goods/");
        // registry.addResourceHandler("/images/question/**")
        // .addResourceLocations("file:" + userDir + "/uploads/images/question/");
        // registry.addResourceHandler("/images/answer/**")
        // .addResourceLocations("file:" + userDir + "/uploads/images/answer/");
        // registry.addResourceHandler("/images/profile/**")
        // .addResourceLocations("file:" + userDir + "/uploads/images/profile/");
        // }
}