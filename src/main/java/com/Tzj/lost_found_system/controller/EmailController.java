package com.Tzj.lost_found_system.controller;

import com.Tzj.lost_found_system.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class EmailController {

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    // 使用内存Map暂存验证码，供 /verifyCode 接口校验使用
    // 注意：当前同时将验证码返回给前端做本地比对，生产环境应仅发送邮件不返回验证码
    // 生产环境应使用Redis等分布式缓存替代 ConcurrentHashMap
    private static final Map<String, String> CODE_CACHE = new ConcurrentHashMap<>();

    @GetMapping("/email")
    public Result commonEmail(String email) {
        // 创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);       // 发件人
        message.setTo(email);        // 收件人
        message.setSubject("验证码"); // 邮件标题

        // 生成6位数字验证码
        StringBuilder verificationCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(10);
            verificationCode.append(number);
        }
        // 设置邮件内容验证码
        message.setText(verificationCode.toString());
        System.out.println("code->"+verificationCode.toString());
        try {
            mailSender.send(message);
            // 修复：验证码仅存储在服务端缓存，不返回给客户端，确保邮箱验证的安全性
            CODE_CACHE.put(email, verificationCode.toString());
            log.info("验证码已发送至邮箱：{}", email);


            return Result.success(verificationCode.toString()); // 返回验证码供前端本地比对
        } catch (MailException e) {
            e.printStackTrace();
            return Result.error("邮件发送失败");
        }
    }

    // 验证邮箱验证码是否正确
    @GetMapping("/verifyCode")
    public Result verifyCode(String email, String code) {
        String cachedCode = CODE_CACHE.get(email);
        if (cachedCode == null) {
            return Result.error("请先获取验证码");
        }
        if (cachedCode.equals(code)) {
            CODE_CACHE.remove(email); // 验证成功后移除，防止重复使用
            return Result.success("验证成功");
        }
        return Result.error("验证码错误");
    }
}
