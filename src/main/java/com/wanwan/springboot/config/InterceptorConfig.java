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
                        "/user/login",
                        "/user/register",
                        "/**/export",
                        "/**/import",
                        "/file/**",
                        "/role",
                        "/swagger-resources/**"
                        ,"/webjars/**"
                        ,"/v2/**"
                        ,"/swagger-ui.html/**"

                );

    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

}
