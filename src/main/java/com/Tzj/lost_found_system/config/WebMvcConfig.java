package com.Tzj.lost_found_system.config;

import com.Tzj.lost_found_system.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类
 * 注册 JWT 认证拦截器，使所有请求（除白名单外）都必须携带有效 JWT 令牌
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/userLogin",   // 登录接口
                        "/email",       // 获取验证码
                        "/verifyCode",  // 验证验证码
                        "/upload",      // 文件上传
                        "/download",    // 文件下载（图片等静态资源需要未登录也能访问）
                        "/error"        // Spring Boot 错误页面
                );
    }
}
