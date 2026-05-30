package com.Tzj.lost_found_system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j

public class  JwtUtils {

    // JWT 签名密钥（生产环境应通过配置文件或环境变量注入，避免硬编码）
    private static String signKey = "lost_found_system_secret_key_2024";
    // JWT 过期时间：24小时（毫秒），原值为 832000000ms 约9.6天过长，调整为合理时长
    private static Long expire = 86400000L;


    public static String generateJwt(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS256, signKey)
                .compact();


    }
    public static Claims testParseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

}
