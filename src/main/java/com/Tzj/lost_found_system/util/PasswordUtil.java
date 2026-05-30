package com.Tzj.lost_found_system.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码加密工具类
 * 使用 SHA-256 + 随机盐值对密码进行哈希，替代原先的明文存储方式
 * 注意：生产环境建议升级为 BCrypt（org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder）
 */
public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16; // 盐值长度（字节）

    /**
     * 对密码进行加盐哈希
     * @param plainPassword 明文密码
     * @param salt 盐值（Base64编码）
     * @return 哈希后的密码（Base64编码）
     */
    public static String hashPassword(String plainPassword, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedBytes = md.digest(plainPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密算法不可用", e);
        }
    }

    /**
     * 生成随机盐值
     * @return Base64编码的盐值
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 验证密码
     * @param plainPassword 用户输入的明文密码
     * @param hashedPassword 数据库中存储的哈希密码
     * @param salt 数据库中存储的盐值
     * @return 密码是否匹配
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword, String salt) {
        String computedHash = hashPassword(plainPassword, salt);
        return computedHash.equals(hashedPassword);
    }
}
