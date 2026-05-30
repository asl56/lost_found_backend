package com.Tzj.lost_found_system.controller;

import com.Tzj.lost_found_system.pojo.Result;
import com.Tzj.lost_found_system.pojo.User;
import com.Tzj.lost_found_system.service.LoginService;
import com.Tzj.lost_found_system.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/userLogin") // 用户登录接口
    public Result login(String userName, String password) {
        User user = loginService.login(userName, password);
        System.out.println(user);
        if (user != null) {
            // 修复：JWT中仅存储用户标识信息，不存储密码（密码明文放在JWT中有安全风险，JWT payload可被base64解码）
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", user.getId());
            claims.put("uname", user.getUserName());
            claims.put("role", user.getRole());
            String jwt = JwtUtils.generateJwt(claims);
            log.info("令牌：{}", jwt);
            return Result.successAndObject(jwt, user);
        }
        return Result.error("用户名或密码错误");
    }

    @GetMapping("/isLogin") // 验证JWT是否有效的接口
    public Result isLogin(HttpServletRequest req){
        String jwt = req.getHeader("token");
        System.out.println(jwt);
        log.info("令牌：{}", jwt);
        try {
            JwtUtils.testParseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析失败");
            Result error = Result.error("NOT_ERROR");
            return error;
        }
        return Result.success();
    }
}
