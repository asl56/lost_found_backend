<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.6.13-brightgreen?logo=springboot" />
  <img src="https://img.shields.io/badge/MyBatis-2.2.2-blue" />
  <img src="https://img.shields.io/badge/MySQL-8.0-orange?logo=mysql" />
  <img src="https://img.shields.io/badge/JWT-auth-purple" />
  <img src="https://img.shields.io/badge/license-MIT-green" />
</p>

<h1 align="center">🔍 失物招领平台 — 后端</h1>
<p align="center">基于 Spring Boot + MyBatis + MySQL 的 RESTful API 服务<br/>为失物招领系统提供完整的后端支持</p>

---

## ✨ 核心功能

- 🔐 **JWT 认证**：登录生成令牌，拦截器统一校验
- 👥 **用户管理**：注册/登录 · 角色管理 · 状态控制
- 📦 **失物管理**：发布/搜索/编辑/删除 · 状态流转 · 审核机制
- 🎯 **招领管理**：发布/搜索/编辑/删除 · 状态流转
- 💬 **联系留言**：失主与拾主在线沟通
- 📢 **公告管理**：平台公告发布与展示
- 📊 **数据统计**：ECharts 图表数据接口
- 📧 **邮件服务**：邮箱验证码发送与校验
- 📁 **文件上传**：图片上传与下载

---

## 🛠 技术栈

| 类别 | 技术 |
|------|------|
| 框架 | Spring Boot 2.6.13 |
| ORM | MyBatis + MyBatis XML 动态 SQL |
| 数据库 | MySQL 8.0 |
| 认证 | JWT (io.jsonwebtoken) |
| 分页 | PageHelper |
| 邮件 | Spring Mail (163 SMTP) |
| 工具 | Lombok · Jackson |

---

## 📁 项目结构

```
src/main/java/com/Tzj/lost_found_system/
├── config/         # WebMvc 配置（拦截器注册）
├── controller/     # REST 控制器层
├── service/        # 业务逻辑接口
│   └── impl/       #   业务逻辑实现
├── mapper/         # MyBatis Mapper 接口
├── pojo/           # 实体类（User/Lost/Found/Contact...）
├── interceptor/    # JWT 认证拦截器
├── handler/        # 全局异常处理器
└── util/           # 工具类（JWT / 密码加密）
src/main/resources/
├── application.yml # 应用配置
└── com/Tzj/.../mapper/  # MyBatis XML 映射文件
```

---

## 🚀 快速开始

### 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 8.0

### 安装运行

```bash
# 1. 导入数据库
mysql -u root -p < lost_found_system.sql

# 2. 修改 application.yml 中的数据库连接信息
#    spring.datasource.url / username / password

# 3. 启动项目
mvn spring-boot:run

# 或打包运行
mvn clean package -DskipTests
java -jar target/LostFound.jar
```

服务启动后运行在 **http://localhost:8080**

---

## 🔌 API 概览

| 模块 | 端点示例 | 说明 |
|------|----------|------|
| 登录 | `/userLogin` | 用户登录（返回 JWT） |
| 用户 | `/addUser` `/getUserAll` `/editUser` | 用户 CRUD |
| 失物 | `/getLost` `/addLost` `/editLost` `/deleteLost` | 失物管理 |
| 招领 | `/getFound` `/addFound` `/editFound` `/deleteFound` | 招领管理 |
| 联系 | `/getContact` `/addContact` `/deleteContact` | 留言管理 |
| 公告 | `/getNotice` `/addNotice` `/deleteNotice` | 公告管理 |
| 反馈 | `/getFeedBack` `/addFeedBack` | 用户反馈 |
| 邮件 | `/email` `/verifyCode` | 验证码服务 |
| 文件 | `/upload` `/download` | 图片上传下载 |

> 除白名单接口外，所有请求需携带 `Authorization` 头（JWT 令牌）

---

## 🔗 关联项目

- 🎨 **前端界面**：[lost_found_frontend](https://github.com/asl56/lost_found_frontend) — Vue 2 + Element UI

---

## 📄 License

MIT © TangZiJun
