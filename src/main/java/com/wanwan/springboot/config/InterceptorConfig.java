package com.wanwan.springboot.config;

import com.wanwan.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author：玩玩
 * @since：2024/2/2 12:07
 * @description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                //拦截所有请求，通过判断token来决定是否需要登陆
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/users/login",
                        "/api/users/register",
                        "/api/**/export",
                        "/api/**/import",
                        "/api/files/**",
                        "/api/roles",
                        "/api/swagger-resources/**",
                        "/api/webjars/**",
                        "/api/v2/**",
                        "/api/swagger-ui.html/**",

                        "/api/home/slider/enable",
                        "/api/articles/**",
                        "/api/leavewords/**",
                        "/api/category/**",
                        "/api/comments/**"

                );

    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

}
