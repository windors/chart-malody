# Chart-Malody

我的毕设，80分，开源 Malody V 私人谱面服务器，实现了 [Malody Store API]([Mugzone / Malody_Store_API · GitLab](https://gitlab.com/mugzone_team/malody_store_api))（202112）中绝大部分内容，并提供了网页端供数据维护。

---

## 使用

》》》》》》》》》》

---

## 说明

### 系统构成

整体分为 `文件系统` 和 `Malody商城` ，`Malody商城` 有由 `Malody-API ` 和 `Malody-Web` 组成。

![image-20220607161337228](https://github.com/windors/chart-malody/blob/master/imgs/image-20220607161337228.png)

`File-System` ：负责文件存储，通过向外提供获取上传Key（id），并向指定Key（id）对指定文件进行某些操作。



`Malody-API`   ：负责实现 `Malody Store API` 。

`Malody-Web`   ：负责对系统中核心数据进行维护，为用户提供可视化界面。

### 技术栈

开发环境：Java   Maven   Idea

数据库：MySQL   Redis

框架：Spring Boot   Mybatis Plus

预计访问量小，故采用前后端不分离，使用 `Thymeleaf` 统一页面风格

