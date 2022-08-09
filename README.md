# mypages

**我的主页我定义！**<br>
Define the homepage of your interest.


## 前言
> 兴趣成果驱动，理论概念驱不动。

这个项目是个人兴趣驱动，主要用于在业余休息的时候，只用花少量的时间，就能快捷有效地查看和了解自己感兴趣领域的信息动态。
与此同时，也能运用及熟练各大主流技术框架，提升技术水平。
目前整合的技术不多，业务功能也还在完善和更新。


## 项目背景
- 憎恶了首页动态穿插`垃圾广告`？
- 厌倦了大数据`个性推荐`？
- 担心沉迷浪费`宝贵时间`？
- 可曾想过自定义`社交主页`？
<br>

你是否也怀念`简洁`、`纯粹`、`清静`的美好？不想错过有品质的用户内容？不想被无关繁杂内容扰乱视线和思绪？<br>
现在，有一个致力于**追求极简社交体验**，及**获取有效、有品质信息资源**的项目破土萌芽了，如果你也有同样的想法和兴趣，志同道合的朋友，来~~喝两杯~~一起交流开发啊！


## 项目介绍
**mypages** 项目是一个致力于聚合多社交平台用户主页的小型单页应用。
主要为平台管理、平台观点管理、关注用户管理、用户类型管理、用户标签管理、检查用户内容更新、同步用户头像昵称等业务功能提供 restful 接口支持。


## 目录结构
```
mypages -- 项目目录
├── document -- 文档文件
│   └── sql -- 数据库脚本目录
│       └── mypages.sql -- 数据库初始脚本
├── mypages-admin -- 后端主项目模块
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── cn
│   │   │   │       └── m1yellow
│   │   │   │           └── mypages
│   │   │   │               ├── bo -- 业务数据封装对象（bussiness object），用于方法调用参数封装
│   │   │   │               ├── config -- 项目配置类
│   │   │   │               ├── constant -- 静态常量
│   │   │   │               ├── controller -- 控制层
│   │   │   │               ├── dto -- 数据传输封装对象（data transfer object），用于请求参数封装
│   │   │   │               ├── entity -- 实体类
│   │   │   │               ├── mapper -- 实体映射
│   │   │   │               ├── service -- 服务类，里面还有 impl 实现类目录
│   │   │   │               ├── util -- 工具类
│   │   │   │               ├── vo -- 页面显示信息封装对象（view boject），后台数据经过脱敏等处理之后，页面展示
│   │   │   │               ├── MypagesAdminApplication.java -- 项目启动类
│   │   │   │               └── ServletInitializer.java -- Tomcat 部署启动类
│   │   │   └── resources -- 项目资源目录
│   │   │       ├── config -- 配置文件
│   │   │       │   └── logback-spring.xml -- 日志配置
│   │   │       ├── messages
│   │   │       ├── META-INF
│   │   │       │   └── MANIFEST.MF
│   │   │       ├── public -- 公共资源，公开访问
│   │   │       ├── static -- 静态资源，项目内部引用
│   │   │       ├── templates -- 模板资源
│   │   │       ├── application-dev.yml -- 开发环境配置
│   │   │       ├── application-prod.yml -- 生产环境配置
│   │   │       └── application.yml -- 主配置文件，全局生效，生产、开发、测试环境通用配置
│   │   └── test -- 测试资源
│   │       └── java
│   │           └── cn
│   │               └── m1yellow
│   │                   └── mypages
│   │                       ├── MpCodeGenerator.java -- MyBatisPlus Generator 代码生成
│   │                       ├── MypagesAdminApplicationTests.java -- 项目测试类
│   │                       └── MyTest.java -- 自定义测试类
│   ├── docker-compose.yml
│   ├── Dockerfile
│   └── pom.xml
├── mypages-excavation -- 数据发掘模块
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   └── resources
│   │   └── test
│   │       └── java
│   ├── pom.xml
│   └── README.md
├── mypages-generator -- 代码生成模块
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   └── resources
│   │   └── test
│   │       └── java
│   ├── pom.xml
│   └── README.md
├── mypages-god -- 用户模块
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   └── resources
│   │   └── test
│   │       └── java
│   ├── pom.xml
│   └── README.md
├── mypages-security -- 安全控制模块
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   └── resources
│   │   └── test
│   │       └── java
│   ├── pom.xml
│   └── README.md
├── LICENSE
├── pom.xml
└── README.md
```


## 项目演示
由于是前后端分离项目，目前后端项目只提供接口服务。<br>
前端项目 `mypages-web` 主页效果预览：[欢迎光临](https://github.com/M1Yellow/mypages-web)


## 技术选型
| 技术                 | 说明                           | 官网                                            |
| -------------------- | ----------------------------- | ---------------------------------------------- |
| SpringBoot           | 容器+MVC框架 `2.3.10.RELEASE`  | https://spring.io/projects/spring-boot/        |
| Spring Security      | 认证权限框架 `2.3.10.RELEASE`   | https://spring.io/projects/spring-security/    |
| MyBatisPlus          | ORM、分页、代码生成 `3.4.2`      | https://baomidou.com/                          |
| MySQL 	           | 关系型数据库 `5.7.34`           | https://www.mysql.com/                         |
| Redis	               | 数据缓存中间件 `6.0.14`          | https://redis.io/download/                    |
| Druid                | 数据库连接池 `1.2.8`             | https://github.com/alibaba/druid/             |
| JWT                  | JWT登录支持 `0.9.1`             | https://github.com/jwtk/jjwt/                 |
| Docker               | 虚拟应用容器 `20.10.1`           | https://www.docker.com/                       |
| Nginx                | 静态资源服务器 `1.19.10`         | https://www.nginx.com/                         |
| Tomcat               | Web应用服务器 `9.0.45`          | https://tomcat.apache.org/                     |
| Jenkins              | 自动化部署工具 `2.60.3`          | https://github.com/jenkinsci/jenkins/          |
| Swagger-UI           | 文档生成工具 `2.9.2`            | https://github.com/swagger-api/swagger-ui/     |
| OSS                  | 对象存储服务 `3.12.0`           | https://github.com/aliyun/aliyun-oss-java-sdk/ |


## 开发环境与工具
| 工具           | 说明                          | 官网                                                       |
| ------------- | ----------------------------- | --------------------------------------------------------- |
| JDK           | Java开发环境 `1.8`              | https://www.oracle.com/technetwork/java/javase/downloads/ |
| Maven         | 项目构建、jar包管理 `3.6.3`      | https://maven.apache.org/                                 |
| IDEA          | 日常开发工作环境 `2020.3.4`      | https://www.jetbrains.com/idea/download/                  |
| Xshell+Xftp   | 远程服务器连接管理工具 `7.0 Free` | https://www.netsarang.com/en/xshell-download/             |
| Navicat       | 数据库连接管理工具 `15.0`        | https://www.navicat.com/en/download/navicat-premium/      |
| ProcessOn     | 流程图绘制工具 `web`            | https://www.processon.com/                                |
| Postman       | API接口调试工具 `latest`        | https://www.postman.com/                                  |
| Typora        | Markdown编辑器 `0.11.18`       | https://typora.io/                                        |


## 部署运行
1. 下载并安装开发环境和工具
2. 克隆项目到本地，用 IDEA 打开项目（选择打开 `mypages` 文件夹即可）
3. 配置 Maven 环境（镜像地址、仓库目录），并在 IDEA 设置使用自己下载的 Maven
4. 创建数据库，执行数据库脚本（mypages.sql），初始化数据
5. 修改对应环境的 `application-xxx.yml` 配置文件，配置 MySQL、Redis 等中间件连接信息
6. 从入口启动类 `MypagesAdminApplication` 启动项目


> **初始-数据-说明**<br>
> 其中对各个平台的观点看法仅仅是个人观点，可能有些片面、偏激，或是认知错误，不理会便是了，做好自己的事已不易。


## 项目参考
| 项目           | 作者                 | 地址                                         | 说明                         |
| ------------- | ------------------- | -------------------------------------------- | --------------------------- |
| mall          | macrozheng          | https://github.com/macrozheng/mall/          | 代码结构、文档规范             |
