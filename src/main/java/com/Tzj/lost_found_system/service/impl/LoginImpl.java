package com.Tzj.lost_found_system.service.impl;

import com.Tzj.lost_found_system.mapper.LoginMapper;
import com.Tzj.lost_found_system.pojo.User;
import com.Tzj.lost_found_system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public User login(String userName, String password) {
        // 注意：当前数据库密码为明文存储，登录时直接比对
        // 生产环境建议：先对密码进行 SHA-256 加盐哈希或使用 BCrypt 加密后再比对
        // 迁移步骤：1. 使用 PasswordUtil.hashPassword() 对已有密码批量加密
        //          2. 修改此方法为：先查询用户获取盐值，再 hash(输入密码, 盐值) 比对
        return loginMapper.login(userName, password);
    }
}
