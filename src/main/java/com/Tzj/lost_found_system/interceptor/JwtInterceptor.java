package com.Tzj.lost_found_system.interceptor;

import com.Tzj.lost_found_system.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 认证拦截器
 * 拦截所有请求，对携带 token 的请求进行校验，并将用户信息注入请求属性。
 * 未携带 token 的请求仍然放行（兼容前端部分页面未统一使用 request.js 发送请求的情况）。
 * 前端路由守卫 + 后端关键接口鉴权已足够保障基本安全。
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS 预检请求直接放行（解决跨域预检问题）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String requestURI = request.getRequestURI();

        // 从请求头中获取 token（前端 Axios 拦截器在 Authorization 头注入 JWT）
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            // 兼容：也尝试从 token 头获取
            token = request.getHeader("token");
        }

        // 没有 token 的请求直接放行（不做强制拦截，交由前端路由守卫和控制层自行判断）
        if (token == null || token.isEmpty()) {
            return true;
        }

        try {
            // 解析并校验 JWT 令牌
            Claims claims = JwtUtils.testParseJwt(token);
            // 将用户信息存入请求属性，供后续Controller使用
            request.setAttribute("userId", claims.get("uid"));
            request.setAttribute("userName", claims.get("uname"));
            request.setAttribute("role", claims.get("role"));
            log.debug("JWT校验通过，用户ID: {}", claims.get("uid"));
            return true;
        } catch (Exception e) {
            // 令牌无效时记录日志但仍放行，避免因过期 token 导致页面完全不可用
            log.warn("JWT令牌解析失败但放行，URI: {}，错误: {}", requestURI, e.getMessage());
            return true;
        }
    }
}
