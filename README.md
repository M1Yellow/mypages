## 背景

- 憎恶了首页动态穿插 `垃圾广告` ？
- 厌倦了大数据 `个性推荐` ？
- 担心沉迷浪费 `宝贵时间` ？
- 又不想错过 `优质内容` ？
- 可曾想过自定义 `社交主页` ？



<br/>

## 简介

**mypages** 是一个致力于聚合多社交平台用户主页的小型单页应用。

主要业务功能包括，平台管理、平台观点管理、关注用户管理、用户类型管理、用户标签管理、检查用户内容更新、同步用户头像等。



Q：用 `浏览器书签` 按平台分组，把用户主页添加书签，就可以实现聚合多社交平台用户主页了吧，为什么还要花费这么多时间精力写一个项目啊？

A：最开始也是用的浏览器书签分组，也确实挺好用，正因为经常用到，有一天就萌生了自己做一个书签应用的念头💡

- 浏览器书签只是基础功能，可以在书签的基础上，新增头像、简介、标签、更新提醒等功能
- 做一个自己的项目脚手架，能更好地理解、运用、熟练各大技术框架组件
- 追求极简社交体验，关注创作者本身有品质/感兴趣的内容，而不是平台算法、信息茧房的推送投喂



<br/>

## 目录结构
```
mypages -- 项目目录
├── document -- 文档文件
│   └── sql -- 数据库脚本目录
│       └── mypages.sql -- 数据库初始脚本
├── mypages-admin -- 后台管理模块
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
│   │   │       ├── mapper -- MyBatisPlus mapper xml
│   │   │       ├── META-INF
│   │   │       ├── public -- 公共资源，公开访问
│   │   │       ├── static -- 静态资源，项目内部引用
│   │   │       ├── application-dev.yml -- 开发环境配置
│   │   │       ├── application-prod.yml -- 生产环境配置
│   │   │       └── application.yml -- 全局配置文件
│   │   └── test -- 测试资源
│   │       └── java
│   │           └── cn
│   │               └── m1yellow
│   │                   └── mypages
│   │                       ├── MpCodeGenerator.java -- MyBatisPlus Generator 代码生成
│   │                       ├── MypagesAdminApplicationTests.java -- 项目测试类
│   │                       └── MyTest.java -- 自定义测试类
│   ├── .gitignore
│   ├── docker-compose.yml
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md
├── mypages-common -- 基础共用模块
├── mypages-generator -- 代码生成模块
├── mypages-security -- 安全控制模块
├── mypages-user -- 用户模块
├── mypages-visit -- 访问模块
├── .gitignore
├── LICENSE
├── pom.xml
└── README.md
```



<br/>

## 项目演示

由于是前后端分离，目前后端项目只是提供接口服务。

前端项目 👉 **[mypages-web](https://github.com/M1Yellow/mypages-web)**



<br/>

## 技术选型

| 技术             | 说明                  | 官网                                             |
|----------------|---------------------|------------------------------------------------|
| SpringBoot     | 容器+MVC框架 `2.7.18`   | https://spring.io/projects/spring-boot/        |
| SpringSecurity | 认证授权框架 `5.7.11`     | https://spring.io/projects/spring-security/    |
| MyBatisPlus    | ORM、分页、代码生成 `3.5.7` | https://baomidou.com/                          |
| MySQL 	        | 关系型数据库 `5.7.36`     | https://www.mysql.com/                         |
| Redis	         | 数据缓存中间件 `6.2.7`     | https://redis.io/download/                     |
| Druid          | 数据库连接池 `1.2.23`     | https://github.com/alibaba/druid/              |
| JWT            | JWT登录支持 `0.12.6`    | https://github.com/jwtk/jjwt/                  |
| Docker         | 虚拟应用容器 `26.1.1`     | https://www.docker.com/                        |
| Nginx          | 静态资源服务器 `1.20.1`    | https://www.nginx.com/                         |
| Tomcat         | Web应用服务器 `9.0.83`   | https://tomcat.apache.org/                     |
| Jenkins        | 自动化部署工具 `2.60.3`    | https://github.com/jenkinsci/jenkins/          |
| Swagger        | 文档生成工具 `3.0.0`      | https://github.com/swagger-api/swagger-ui/     |
| OSS            | 对象存储服务 `3.18.0`     | https://github.com/aliyun/aliyun-oss-java-sdk/ |

> 项目真实使用的组件版本**尽量不要公布**，优先选择比较新的、用的人多的、网上没公布漏洞的版本，
> 出现问题方便找解决方案，也可以在一定程度上防范漏洞入侵。
> 后续还需要定期关注组件漏洞信息，及时更新到合适的版本，或者更换其他同类型组件。

<br/>

## 开发环境与工具

| 工具          | 说明                   | 官网                                                        |
|-------------|----------------------|-----------------------------------------------------------|
| JDK         | Java开发环境 `1.8`       | https://www.oracle.com/technetwork/java/javase/downloads/ |
| Maven       | 项目构建、jar包管理 `3.6.3`  | https://maven.apache.org/                                 |
| IDEA        | 日常开发工作环境 `2020.3.4`  | https://www.jetbrains.com/idea/download/                  |
| Xshell+Xftp | 服务器连接管理工具 `7.0 Free` | https://www.netsarang.com/en/xshell-download/             |
| Navicat     | 数据库连接管理工具 `16.3`     | https://www.navicat.com/en/download/navicat-premium/      |
| ProcessOn   | 流程图绘制工具 `web`        | https://www.processon.com/                                |
| Postman     | API接口调试工具 `latest`   | https://www.postman.com/                                  |
| Typora      | Markdown编辑器 `latest` | https://typora.io/                                        |



<br/>

## 部署运行

1. 下载并安装开发环境和工具
2. 克隆项目到本地，用 IDEA 打开项目（选择打开 `mypages` 文件夹即可）
3. 配置 Maven 环境（镜像地址、仓库目录），并在 IDEA 设置使用自己下载的 Maven
4. 创建数据库（mypages -> utf8mb4、utf8mb4_general_ci），再执行数据库脚本（mypages.sql），初始化数据
5. 修改对应环境的 `application-xxx.yml` 配置文件，配置 MySQL、Redis 等中间件连接信息
6. 从入口启动类 `MypagesAdminApplication` 启动项目



<br/>
