# *SpringBoot+VUE项目*

## 1.目的

​	后台管理

## 2.开发工具

​	idea、mysql、navicat、apifox、google浏览器、

## 3.开发环境

​	jdk1.8.0_333、mysql5.7.43、npm10.2.3、springboot2.6.13

## 4.遇到的问题

### 4.1跨域

问题描述：

```
localhost/:1 Access to fetch at 'http://localhost:8080/user/page?pageNum=1&pageSize=2' from origin 'http://localhost:8081' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource. If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled.
```

原因：



解决方法：

```java
@Configuration
public class CorsConfig {
    private static final long MAX_AGE = 24 * 60 *60;
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        corsConfiguration.addAllowedOrigin("http://localhost:8081");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(MAX_AGE);
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
```

### 4.2整合swagger报错

问题描述：

```
org.springframework.context.ApplicationContextException: Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
```

原因：

```text
Springfox 假设 Spring MVC 的路径匹配策略是 ant-path-matcher，而 Spring Boot 2.6以上版本的默认匹配策略是 path-pattern-matcher，这就造成了上面的报错。
```

解决方法：

```yml
spring :
  mvc:
    pathmatch:
      matching-strategy: ant-path-matcher
```

