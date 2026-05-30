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
 * 拦截所有非白名单接口，校验请求头中的 JWT token 是否有效。
 * 前端已统一使用 request.js 携带 Authorization 头，拦截器恢复严格校验模式。
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

        // 从请求头中获取 token（前端 request.js 拦截器在 Authorization 头注入 JWT）
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            // 兼容：也尝试从 token 头获取
            token = request.getHeader("token");
        }

        if (token == null || token.isEmpty()) {
            log.warn("请求缺少JWT令牌，URI: {}，客户端IP: {}", requestURI, request.getRemoteAddr());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录或登录已过期\"}");
            return false;
        }

        try {
            // 解析并校验 JWT 令牌
            Claims claims = JwtUtils.testParseJwt(token);
            // 将用户信息存入请求属性，供后续Controller使用
            request.setAttribute("userId", claims.get("uid"));
            request.setAttribute("userName", claims.get("uname"));
            request.setAttribute("role", claims.get("role"));
            return true;
        } catch (Exception e) {
            log.warn("JWT令牌解析失败，URI: {}，错误: {}", requestURI, e.getMessage());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"令牌无效或已过期，请重新登录\"}");
            return false;
        }
    }
}
