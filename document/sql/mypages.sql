/*
 Navicat Premium Data Transfer

 Source Server         : mpages
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : 47.107.28.122:3306
 Source Schema         : mypages

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 07/06/2024 12:31:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `config_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置项键',
  `config_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置项的值',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述说明',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'cache_control_max_age', '86400', '24*60*60 一天', b'0', '2021-05-15 16:24:24', '2021-05-15 16:59:24');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限路径',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限描述',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_url`(`url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '/following/add', '添加关注', '添加关注用户', b'0', '2021-05-08 09:57:34', '2021-05-08 10:05:11');
INSERT INTO `sys_permission` VALUES (2, '/following/removeRelation', '移除关注', '移除关注用户', b'0', '2021-05-08 09:58:07', '2021-05-08 10:05:13');
INSERT INTO `sys_permission` VALUES (3, '/following/syncOne', '同步用户信息', '同步关注用户信息', b'0', '2021-05-08 09:58:43', '2021-05-08 10:05:01');
INSERT INTO `sys_permission` VALUES (4, '/following/syncBatch', '批量同步用户信息', '批量同步关注用户信息', b'0', '2021-05-08 10:01:16', '2021-05-08 10:05:04');
INSERT INTO `sys_permission` VALUES (5, '/remark/add', '添加标签', '添加标签', b'0', '2021-05-08 10:02:38', '2021-05-08 10:02:38');
INSERT INTO `sys_permission` VALUES (6, '/remark/removeBelongs', '移除所有标签', '移除用户所有标签', b'0', '2021-05-08 10:03:08', '2021-05-08 10:03:49');
INSERT INTO `sys_permission` VALUES (7, '/remark/removeOne', '移除单个标签', '移除用户单个标签', b'0', '2021-05-08 10:03:35', '2021-05-08 10:03:51');
INSERT INTO `sys_permission` VALUES (8, '/type/add', '添加类型', '添加类型', b'0', '2021-05-08 10:05:47', '2021-05-08 10:05:47');
INSERT INTO `sys_permission` VALUES (9, '/type/remove', '移除类型', '移除类型', b'0', '2021-05-08 10:06:09', '2021-05-08 10:06:09');
INSERT INTO `sys_permission` VALUES (10, '/type/list', '类型列表', '获取类型列表', b'0', '2021-05-08 10:06:34', '2021-05-08 10:06:34');
INSERT INTO `sys_permission` VALUES (11, '/opinion/add', '添加观点', '添加观点', b'0', '2021-05-08 10:06:58', '2021-05-08 10:06:58');
INSERT INTO `sys_permission` VALUES (12, '/opinion/remove', '移除观点', '移除观点', b'0', '2021-05-08 10:07:16', '2021-05-08 10:07:16');
INSERT INTO `sys_permission` VALUES (13, '/platform/add', '添加平台', '添加平台', b'0', '2021-05-08 10:07:38', '2021-05-08 10:07:38');
INSERT INTO `sys_permission` VALUES (14, '/platform/remove', '移除平台', '移除平台', b'0', '2021-05-08 10:07:54', '2021-05-08 10:07:54');
INSERT INTO `sys_permission` VALUES (15, '/platform/list', '平台列表', '获取平台列表', b'0', '2021-05-08 10:08:15', '2021-05-08 10:21:31');
INSERT INTO `sys_permission` VALUES (16, '/platform-relation/add', '添加用户平台关联', '添加用户平台关联', b'0', '2021-05-08 10:09:02', '2021-05-08 10:09:44');
INSERT INTO `sys_permission` VALUES (17, '/platform-relation/remove', '移除用户平台关联', '移除用户平台关联', b'0', '2021-05-08 10:09:34', '2021-05-08 10:09:34');
INSERT INTO `sys_permission` VALUES (18, '/platform/baseList', '平台基础列表', '平台基础列表', b'0', '2021-05-08 15:01:00', '2021-05-08 15:01:24');
INSERT INTO `sys_permission` VALUES (19, '/home/platformList', '平台首页数据', '平台首页数据', b'0', '2021-05-11 19:55:06', '2021-05-11 20:07:48');
INSERT INTO `sys_permission` VALUES (20, '/following-relation/add', '新增或修改关注用户关联', '新增或修改关注用户关联', b'0', '2021-05-12 19:43:29', '2021-05-12 19:43:29');
INSERT INTO `sys_permission` VALUES (21, '/sys/delRedisCache', '按KEY删除redis缓存', '按KEY删除redis缓存', b'0', '2021-05-15 13:30:13', '2021-05-15 13:30:13');
INSERT INTO `sys_permission` VALUES (22, '/sys/properties', '全局参数列表', '全局参数列表', b'0', '2021-05-15 13:30:33', '2021-05-15 13:30:33');
INSERT INTO `sys_permission` VALUES (23, '/user/detail', '获取用户详细信息', '获取用户详细信息', b'0', '2021-05-17 12:38:45', '2021-05-17 12:38:45');
INSERT INTO `sys_permission` VALUES (24, '/following/list', '获取关注用户列表', '获取关注用户列表', b'0', '2021-05-18 08:14:57', '2021-05-18 08:14:57');
INSERT INTO `sys_permission` VALUES (25, '/opinion/list', '获取观点列表', '获取观点列表', b'0', '2021-05-18 08:15:19', '2021-05-18 08:15:19');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色代码，英文，用于程序业务处理',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名，用于页面显示',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色描述，用于补充提示说明',
  `count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用于统计对应该角色的用户数量，目前暂未使用',
  `status` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色状态，0-正常；1-禁用，默认0',
  `sort_no` tinyint(3) UNSIGNED NOT NULL DEFAULT 5 COMMENT '优先级由低到高：1-10，默认5',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '系统管理员', '技术负责人或主管领导', 0, 0, 5, b'0', '2021-05-08 09:32:39', '2021-05-08 09:36:24');
INSERT INTO `sys_role` VALUES (2, 'user', '平台用户', '普通平台用户', 0, 0, 5, b'0', '2021-05-08 10:10:57', '2021-05-08 10:10:57');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联角色id',
  `permission_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联权限id',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 1, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (2, 1, 2, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (3, 1, 3, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (4, 1, 4, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (5, 1, 5, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (6, 1, 6, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (7, 1, 7, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (8, 1, 8, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (9, 1, 9, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (10, 1, 10, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (11, 1, 11, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (12, 1, 12, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (13, 1, 13, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (14, 1, 14, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (15, 1, 15, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (16, 1, 16, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (17, 1, 17, b'0', '2021-05-08 10:15:56', '2021-05-08 10:15:56');
INSERT INTO `sys_role_permission` VALUES (32, 2, 1, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (33, 2, 2, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (34, 2, 3, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (35, 2, 4, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (36, 2, 5, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (37, 2, 6, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (38, 2, 7, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (39, 2, 8, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (40, 2, 9, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (41, 2, 10, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (42, 2, 11, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (43, 2, 12, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (44, 2, 15, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (45, 2, 16, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (46, 2, 17, b'0', '2021-05-08 10:21:23', '2021-05-08 10:21:23');
INSERT INTO `sys_role_permission` VALUES (47, 1, 18, b'0', '2021-05-11 19:55:33', '2021-05-11 19:55:33');
INSERT INTO `sys_role_permission` VALUES (48, 1, 19, b'0', '2021-05-11 19:55:44', '2021-05-11 19:55:44');
INSERT INTO `sys_role_permission` VALUES (49, 2, 18, b'0', '2021-05-11 19:55:51', '2021-05-11 19:55:51');
INSERT INTO `sys_role_permission` VALUES (50, 2, 19, b'0', '2021-05-11 19:55:57', '2021-05-11 19:55:57');
INSERT INTO `sys_role_permission` VALUES (51, 1, 20, b'0', '2021-05-12 19:43:54', '2021-05-12 19:43:54');
INSERT INTO `sys_role_permission` VALUES (52, 2, 20, b'0', '2021-05-12 19:44:00', '2021-05-12 19:44:00');
INSERT INTO `sys_role_permission` VALUES (53, 1, 21, b'0', '2021-05-15 13:30:57', '2021-05-15 13:30:57');
INSERT INTO `sys_role_permission` VALUES (54, 1, 22, b'0', '2021-05-15 13:31:03', '2021-05-15 13:31:03');
INSERT INTO `sys_role_permission` VALUES (55, 1, 23, b'0', '2021-05-17 12:39:00', '2021-05-17 12:39:00');
INSERT INTO `sys_role_permission` VALUES (56, 2, 23, b'0', '2021-05-18 02:41:42', '2021-05-18 02:41:42');
INSERT INTO `sys_role_permission` VALUES (57, 1, 24, b'0', '2021-05-18 08:15:53', '2021-05-18 08:15:53');
INSERT INTO `sys_role_permission` VALUES (58, 1, 25, b'0', '2021-05-18 08:16:00', '2021-05-18 08:16:00');
INSERT INTO `sys_role_permission` VALUES (59, 2, 24, b'0', '2021-05-18 08:16:06', '2021-05-18 08:16:06');
INSERT INTO `sys_role_permission` VALUES (60, 2, 25, b'0', '2021-05-18 08:16:18', '2021-05-18 08:16:18');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联角色id',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, b'0', '2021-05-08 10:12:07', '2021-05-08 10:12:07');
INSERT INTO `sys_user_role` VALUES (2, 2, 2, b'0', '2021-05-08 10:12:16', '2021-05-08 10:12:16');

-- ----------------------------
-- Table structure for user_base
-- ----------------------------
DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `profile_photo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '形象照片（头像）',
  `gender` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '性别，1-男；0-女，默认1',
  `lock_time` datetime NULL DEFAULT NULL COMMENT '锁定时间，null-未锁定；当前时间之前-锁定；当前时间之后-待锁定',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_name`(`user_name`) USING BTREE,
  UNIQUE INDEX `uk_mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_base
-- ----------------------------
INSERT INTO `user_base` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, '/images/user-profile-photo/img-33307974ebe0ea92e27c48df28268546.jpg', 1, NULL, b'0', '2021-04-23 06:23:16', '2021-05-17 01:51:28');
INSERT INTO `user_base` VALUES (2, 'test', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, 1, NULL, b'0', '2021-04-23 06:23:16', '2021-05-07 07:28:50');

-- ----------------------------
-- Table structure for user_check_update
-- ----------------------------
DROP TABLE IF EXISTS `user_check_update`;
CREATE TABLE `user_check_update`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id（冗余，方便关联查询）',
  `following_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户关注表的id',
  `new_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '新动态数量',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId_followingId`(`user_id`, `following_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '检查关注用户更新表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_check_update
-- ----------------------------

-- ----------------------------
-- Table structure for user_following
-- ----------------------------
DROP TABLE IF EXISTS `user_following`;
CREATE TABLE `user_following`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `platform_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联平台id',
  `user_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联用户来源平台的id或标识',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `main_page` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主页地址',
  `profile_photo` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '形象照片（头像）',
  `signature` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `is_user` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否为用户，1-是用户；0-不是，默认1',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_platformId_userKey`(`platform_id`, `user_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 308 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关注表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_following
-- ----------------------------
INSERT INTO `user_following` VALUES (1, 2, 'v-technology', '知识区', 'https://www.bilibili.com/v/knowledge/', '/images/user-profile-photo/02a0c09a6317e16c4077df10ab3780c2906f8f24.png', '点亮好奇心，在知识海洋里乘风破浪~', b'0', b'0', '2021-01-02 10:39:40', '2021-07-13 17:17:53');
INSERT INTO `user_following` VALUES (2, 2, 'v-life', '生活区', 'https://www.bilibili.com/v/life', '/images/user-profile-photo/c1e19150b5d1e413958d45e0e62f012e3ee200af.png', '衣食住行、柴米油盐', b'0', b'0', '2021-01-02 10:42:26', '2021-11-29 16:44:27');
INSERT INTO `user_following` VALUES (3, 2, '520155988', '所长林超', 'https://space.bilibili.com/520155988/video', '/images/user-profile-photo/bc1a14a6c811b02ef30e9e69a7eb343a677287db.jpg', '薛定谔的眨眼.看科技&商业原理 公主号其他同名~合作联系：suozhang03', b'1', b'0', '2021-01-02 10:46:37', '2021-01-02 10:46:37');
INSERT INTO `user_following` VALUES (4, 2, '496085430', '无趣的二姐', 'https://space.bilibili.com/496085430/video', '/images/user-profile-photo/94a960d95f08c141b97c8cd217166221999dae35.jpg', '分享我看到的世界，文案发在公众号: 无趣的二姐', b'1', b'0', '2021-01-02 10:48:07', '2021-01-02 10:48:07');
INSERT INTO `user_following` VALUES (5, 2, '250111460', '蜡笔和小勋', 'https://space.bilibili.com/250111460/video', '/images/user-profile-photo/8a8812e0a9bb3adda90044ef48830584e1efe7a2.jpg', '讲点有益的，拍点有趣的 I 微博@蜡笔和小勋 I 邮箱：labidakashe@163.com(蜡笔打卡社)', b'1', b'0', '2021-01-02 11:19:57', '2021-01-02 11:19:57');
INSERT INTO `user_following` VALUES (6, 2, '386364189', '雪颖Shae', 'https://space.bilibili.com/386364189/video', '/images/user-profile-photo/ae1381713c2e5de1262c558eba55d486d9901e37.jpg', '真我心理主理人，一个你心灵成长路上的陪伴者。', b'1', b'0', '2021-01-02 11:20:50', '2021-01-02 11:20:50');
INSERT INTO `user_following` VALUES (7, 2, '37663924', '硬核的半佛仙人', 'https://space.bilibili.com/37663924/video', '/images/user-profile-photo/aaf33dced1941af0946f37c62f4b48fcaba9c9a2.jpg', '喜欢小仙女', b'1', b'0', '2021-01-02 11:23:45', '2021-01-02 11:23:45');
INSERT INTO `user_following` VALUES (8, 2, '303740257', '牛顿顿顿', 'https://space.bilibili.com/303740257/video', '/images/user-profile-photo/ccec7bf50aa4e269a5122c945ec5a93c67b5ff4c.jpg', '诗人，斗士，被称作：东半球第二正经の男人', b'1', b'0', '2021-01-02 11:34:24', '2021-01-02 11:34:24');
INSERT INTO `user_following` VALUES (9, 2, '512574759', '公孙田浩', 'https://space.bilibili.com/512574759/video', '/images/user-profile-photo/54e991d0369df2dcb864c798bb9c637128c0a73d.jpg', '用数据和事实呈现另一个互联网世界', b'1', b'0', '2021-01-02 11:35:25', '2021-01-02 11:35:25');
INSERT INTO `user_following` VALUES (10, 2, '387460933', '营养师顾中一', 'https://space.bilibili.com/387460933/video', '/images/user-profile-photo/a9a045485d2aa2e52f5a3333eccdd55b9e816bb6.jpg', '清华大学公共卫生硕士，北京营养师协会理事，科学传播副高职称，入选福布斯中国50位意见领袖榜', b'1', b'0', '2021-01-02 11:36:39', '2021-05-24 08:08:06');
INSERT INTO `user_following` VALUES (11, 2, '19577966', '李子柒', 'https://space.bilibili.com/19577966/video', '/images/user-profile-photo/82d27965dae3b2fe9e52780c6309c7b37ad4cbf2.jpg', '李家有女，人称子柒。 新浪微博：李子柒 邮箱：liziqistyle@163.com', b'1', b'0', '2021-01-02 11:37:54', '2021-01-02 11:37:54');
INSERT INTO `user_following` VALUES (12, 2, '298317405', '我是马小坏', 'https://space.bilibili.com/298317405/video', '/images/user-profile-photo/103f3c8864f98cb6b8d6df4f972514b1a0e91595.jpg', '分享美食，更是分享对生活的一种态度，更多精彩欢迎关注头条号：我是马小坏，微信公众号：我是马小坏，微博:马小坏频道', b'1', b'0', '2021-01-02 11:38:46', '2021-05-20 07:59:25');
INSERT INTO `user_following` VALUES (13, 2, '66391032', '帅soserious', 'https://space.bilibili.com/66391032/video', '/images/user-profile-photo/40a663bb18e9064a97901b96aaf7d84d8056e98b.jpg', '#健身路上有我陪伴❤️ 微博:帅soserious', b'1', b'0', '2021-01-02 11:40:11', '2021-01-02 11:40:11');
INSERT INTO `user_following` VALUES (14, 2, '156858999', '曼巴yelomamba', 'https://space.bilibili.com/156858999/video', '/images/user-profile-photo/8746a93dc37530e8b7d2439b06954a27b1120e6d.jpg', '好身材又健康的秘密都在这里 美国运动协会ACE私教认证 商务合作请加：OKAY24DJX（请备注来意哦）', b'1', b'0', '2021-01-02 11:40:43', '2021-01-02 11:40:43');
INSERT INTO `user_following` VALUES (15, 2, '37889997', '冰寒哥', 'https://space.bilibili.com/37889997/video', '/images/user-profile-photo/9901d2301367671e8a201068e1f1d7221ad9d339.jpg', '同济大学皮肤学在读博士，《听肌肤的话》系列作者，《药妆品》主译。无干货，不冰寒。', b'1', b'0', '2021-01-02 11:42:25', '2021-01-02 11:42:25');
INSERT INTO `user_following` VALUES (16, 2, '456404164', '皮科医生魏小博', 'https://space.bilibili.com/456404164/video', '/images/user-profile-photo/ac989cda390065228126fe0e44db74906ca0741d.jpg', '北京某三甲医院皮肤科医生 北京大学皮肤性病学博士\n尽量不要私信问诊，实在回复不过来，大家有问题还是及时就医诊治，以免延误病情', b'1', b'0', '2021-01-02 11:43:40', '2021-01-02 11:43:40');
INSERT INTO `user_following` VALUES (17, 2, '77266891', '深读视频', 'https://space.bilibili.com/77266891/video', '/images/user-profile-photo/ca5812ac9eee536c268ac3d8ab4dd54dfc62d3ee.jpg', '关注公众号“深读视频”~让医学不再难懂！', b'1', b'0', '2021-01-02 11:45:29', '2021-01-02 11:45:29');
INSERT INTO `user_following` VALUES (18, 2, '402576555', '画渣花小烙', 'https://space.bilibili.com/402576555/video', '/images/user-profile-photo/9827d2901925e8efaf27fbf077e13668f749798a.jpg', '一个画画儿的，画一些可爱不严肃的知识漫画！感谢你的喜欢！商务VX：huaxiaolao666（请注明品牌，感谢）', b'1', b'0', '2021-01-02 11:47:06', '2024-05-16 02:48:14');
INSERT INTO `user_following` VALUES (19, 2, '456691117', 'Freya飞呀', 'https://space.bilibili.com/456691117/video', '/images/user-profile-photo/1be0d49467a78352f6098d811065e0353878968e.jpg', 'wb同名｜不看私信  \n偷偷收藏温暖｜慢慢提升幸福值｜勇敢用生活治愈自己\n', b'1', b'0', '2021-01-02 11:47:50', '2021-01-02 11:47:50');
INSERT INTO `user_following` VALUES (20, 2, '28794030', '起床大萌萌', 'https://space.bilibili.com/28794030/video', '/images/user-profile-photo/235aea61e952d200ed3bbfcddd342d9eba6a5634.jpg', '起床迎接美好的生活吧 ✨vx：pmwu01', b'1', b'0', '2021-01-02 11:48:28', '2021-01-02 11:48:28');
INSERT INTO `user_following` VALUES (21, 2, '4548018', '扎双马尾的丧尸', 'https://space.bilibili.com/4548018/video', '/images/user-profile-photo/5be61949369dd844cc459eab808da151d8c363d2.gif', '改名啦！原昵称已转移到小号：丧妹有点跳 ⋆商务sombie96⋆', b'1', b'0', '2021-01-02 11:49:07', '2021-05-20 06:23:51');
INSERT INTO `user_following` VALUES (22, 2, '20259914', '稚晖君', 'https://space.bilibili.com/20259914/video', '/images/user-profile-photo/cb9ef82714507e6bda707dac216da94c97d70037.jpg', '保持热爱，奔赴星海。', b'1', b'0', '2021-01-02 11:50:36', '2021-01-02 11:50:36');
INSERT INTO `user_following` VALUES (23, 2, '430777205', '达尔闻', 'https://space.bilibili.com/430777205/video', '/images/user-profile-photo/ab81b1454dbf711fb28d76da053a676f288a89e4.jpg', '逆天小姐姐只讲技术，不撩汉！WeChat: 达尔闻说', b'1', b'0', '2021-01-02 11:51:25', '2021-01-02 11:51:25');
INSERT INTO `user_following` VALUES (24, 2, '12590', 'epcdiy', 'https://space.bilibili.com/12590/video', '/images/user-profile-photo/f288604112016e93ca224c4c2c58980a6cd6ba25.png', '央企程序员，商务合作VX：Im_double_cloud，加我请注明产品或者目的，不要只说商务合作。', b'1', b'0', '2021-01-02 11:52:02', '2021-01-02 11:52:02');
INSERT INTO `user_following` VALUES (25, 3, '5a66a94524eb450993a83d59a2854a45', '微博热搜榜', 'https://s.weibo.com/top/summary?cate=realtimehot', '/images/user-profile-photo/1f883b5711ad41e4a0504f5d4e6beaa40dvsxvbt.png', '随时随地发现新鲜事（新孩子）', b'0', b'0', '2021-01-02 12:05:12', '2024-06-03 12:06:32');
INSERT INTO `user_following` VALUES (26, 3, '2803301701', '人民日报', 'https://m.weibo.cn/u/2803301701', '/images/user-profile-photo/0033ImPzly8h8vgemh8kxj60sa0sadgw02.jpg', '《人民日报》法人微博 人民日报法人微博。参与、沟通、记录时代。', b'1', b'0', '2021-01-02 12:06:41', '2024-05-10 16:41:49');
INSERT INTO `user_following` VALUES (27, 3, '2286908003', '人民网', 'https://m.weibo.cn/u/2286908003', '/images/user-profile-photo/002uLDeXly8glmohn698dj60j60j6q3b02.jpg', '人民网法人微博 报道全球 传播中国', b'1', b'0', '2021-01-02 12:07:23', '2021-01-02 12:07:23');
INSERT INTO `user_following` VALUES (28, 3, '1974576991', '环球时报', 'https://m.weibo.cn/u/1974576991', '/images/user-profile-photo/0029D7FZly8h8vg4kqwr4j60go0got9i02.jpg', '《环球时报》社有限公司官方微博 报道多元世界    解读复杂中国', b'1', b'0', '2021-01-02 12:07:58', '2024-05-10 16:42:16');
INSERT INTO `user_following` VALUES (29, 3, '2656274875', '央视新闻', 'https://m.weibo.cn/u/2656274875', '/images/user-profile-photo/002TLsr9ly8hmxa6f543uj60go0gowfl02.jpg', '中央广播电视总台央视新闻官方账号 “央视新闻”是中央广播电视总台新闻新媒体旗舰账号，是总台重大新闻、突发事件和重要报道的首发平台。', b'1', b'0', '2021-01-02 12:08:38', '2024-05-10 16:42:26');
INSERT INTO `user_following` VALUES (30, 3, '1784473157', '中国新闻网', 'https://m.weibo.cn/u/1784473157', '/images/user-profile-photo/001WLsZ7ly8h0ou9n3isxj605k05k74602.jpg', '中国新闻网法人微博 北京中新网信息科技有限公司', b'1', b'0', '2021-01-02 12:09:17', '2021-01-02 12:09:17');
INSERT INTO `user_following` VALUES (31, 3, '1774057271', '生命时报', 'https://m.weibo.cn/u/1774057271', '/images/user-profile-photo/001W3LkXly8h8w4pp4azmj60e80e8q3b02.jpg', '《生命时报》社有限公司官方微博 人民日报主管，环球时报主办，一家具有国际视野的大众健康媒体。更多精彩关注微信“LT0385”。', b'1', b'0', '2021-01-02 12:10:01', '2024-05-10 16:42:02');
INSERT INTO `user_following` VALUES (32, 3, '5044281310', '澎湃新闻', 'https://m.weibo.cn/u/5044281310', '/images/user-profile-photo/005vnhZYly8ftjmwo0bx4j308c08cq32.jpg', '澎湃新闻官方微博 有内涵的时政新媒体', b'1', b'0', '2021-01-02 12:11:17', '2021-01-02 12:11:17');
INSERT INTO `user_following` VALUES (33, 3, '1496814565', '封面新闻', 'https://m.weibo.cn/u/1496814565', '/images/user-profile-photo/593793e5ly8gdi6sa5seej20gq0f50sm.jpg', '封面新闻华西都市报官方微博 欢迎关注封面新闻', b'1', b'0', '2021-01-02 12:13:50', '2021-01-02 12:13:50');
INSERT INTO `user_following` VALUES (34, 3, '1684012053', '财经杂志', 'https://m.weibo.cn/u/1684012053', '/images/user-profile-photo/645ffc15ly8gib1q0vevgj20u00u2403.jpg', '《财经》杂志官方微博 独立 独家 独到', b'1', b'0', '2021-01-02 12:14:56', '2021-01-02 12:14:56');
INSERT INTO `user_following` VALUES (35, 3, '1642634100', '新浪科技', 'https://m.weibo.cn/u/1642634100', '/images/user-profile-photo/61e89b74ly1gdiq06vjw9j20c60c4aac.jpg', '新浪科技官方微博 新浪科技是中国最有影响力的TMT产业资讯及数码产品服务平台。让我们带你观察世界变化，看清行业趋势！', b'1', b'0', '2021-01-02 12:15:35', '2021-01-02 12:15:35');
INSERT INTO `user_following` VALUES (36, 3, '2970452952', '李子柒', 'https://m.weibo.cn/u/2970452952', '/images/user-profile-photo/b10d83d8jw8f53xpxjlhaj20ku0kut9k.jpg', '李子柒品牌创始人 邮箱：liziqistyle@163.com', b'1', b'0', '2021-01-03 09:27:55', '2021-01-03 09:27:55');
INSERT INTO `user_following` VALUES (37, 3, '6089150236', '马小坏频道', 'https://m.weibo.cn/u/6089150236', '/images/user-profile-photo/006E5s8Aly8hnqskubvpmj30m80m876j.jpg', '美食博主 美食视频自媒体 工作微：kjxnweixin', b'1', b'0', '2021-01-03 09:54:37', '2021-01-03 09:54:37');
INSERT INTO `user_following` VALUES (38, 3, '5786902874', '夏厨陈二十', 'https://m.weibo.cn/u/5786902874', '/images/user-profile-photo/006jDfN8ly1horbnz7pt2j30u00u0jvb.jpg', '美食博主 美食视频自媒体 治愈系中华美食传播者    合作微信：XTZPC888', b'1', b'0', '2021-01-03 09:56:48', '2021-01-03 09:56:48');
INSERT INTO `user_following` VALUES (39, 3, '3136788225', '好煮艺', 'https://m.weibo.cn/u/3136788225', '/images/user-profile-photo/baf79701ly8fjwwtmvwtij20ku0kuwf8.jpg', '美食作家 作品《美食健康谱》美食点评团成员 美食作家，专注美食分享。关注@好煮艺，学得好厨艺。微博合作推广请联系微 信：ldtg2018', b'1', b'0', '2021-01-03 09:57:45', '2021-01-03 09:57:45');
INSERT INTO `user_following` VALUES (40, 3, '6257740340', '夏妈厨房', 'https://m.weibo.cn/u/6257740340', '/images/user-profile-photo/006PuQ7Oly8gdi6fzmux7j30u00u0mzt.jpg', '知名美食博主 微博故事红人 美食视频自媒体 微博原创视频博主 商务合作：XM16009', b'1', b'0', '2021-01-03 09:58:29', '2021-01-03 09:58:29');
INSERT INTO `user_following` VALUES (41, 3, '2010999701', '营养师张淋琳', 'https://m.weibo.cn/u/2010999701', '/images/user-profile-photo/77dd6b95ly8fz7ftj5uv6j20u00u0dj5.jpg', '一级公共营养师 美容师 健康管理师 陕西省养生协会副会长 知名健康养生博主 健康视频自媒体 原陕西省养生协会副会长，国家一级营养师，健康管理师，中医美容师，CCTV1、CCTV7、BTV生活、深圳卫视、旅游卫视、河北卫视等多家电视台特邀嘉宾！', b'1', b'0', '2021-01-03 10:00:56', '2021-01-03 10:00:56');
INSERT INTO `user_following` VALUES (42, 3, '2949338000', '本叔就是本切鸣', 'https://m.weibo.cn/u/2949338000', '/images/user-profile-photo/afcb5390jw8f4a5rbzov0j21kw2dcb29.jpg', '知名摄影博主 微博原创视频博主 男摄一枚/摄影前后期讲师/微信 ccremix 约拍 合作 请备注', b'1', b'0', '2021-01-03 10:01:22', '2021-01-03 10:01:22');
INSERT INTO `user_following` VALUES (43, 3, '2630646082', 'photoshop资源库', 'https://m.weibo.cn/u/2630646082', '/images/user-profile-photo/9ccc7942gw1enacnterhdj2050050jrk.jpg', '知名创意博主 微博知名设计美学博主 超话主持人（资源君超话） 设计！理想，让库粉们都成为设计师！', b'1', b'0', '2021-01-03 10:02:20', '2021-01-03 10:02:20');
INSERT INTO `user_following` VALUES (44, 3, '6414205745', '回形针PaperClip', 'https://m.weibo.cn/u/6414205745', '/images/user-profile-photo/00705lVnly8fm007um5jdj31jk1jk420.jpg', '泛科普视频自媒体 你的当代生活说明书。商务合作：paperclip@foxmail.com', b'1', b'0', '2021-01-03 10:03:19', '2021-01-03 10:03:19');
INSERT INTO `user_following` VALUES (45, 3, '7284299679', 'Freya飞呀', 'https://m.weibo.cn/u/7284299679', '/images/user-profile-photo/007WYb6vly8gdjbp50o3tj30e80e8gmb.jpg', '微博VLOG博主 我用尽全力过着平凡的一生♥️', b'1', b'0', '2021-01-03 10:04:16', '2021-01-03 10:04:16');
INSERT INTO `user_following` VALUES (46, 3, '6054601231', '基金小达人', 'https://m.weibo.cn/u/6054601231', '/images/user-profile-photo/006BKumzly8ftg4ak2ylmj30ro0roq59.jpg', '财经博主 微博基金合作作者 微博原创视频博主 头条文章作者 分享基金投资技巧，解答基金投资问题。', b'1', b'0', '2021-01-03 10:05:08', '2021-01-03 10:05:08');
INSERT INTO `user_following` VALUES (47, 3, '6032474791', '圖盗', 'https://m.weibo.cn/u/6032474791', '/images/user-profile-photo/006AfEgvjw8f871afe26cj30yi0xb766.jpg', '颜值博主 看看街上的帅哥美女是如何搭配衣服的 ...', b'1', b'0', '2021-01-03 10:06:04', '2021-01-03 10:06:04');
INSERT INTO `user_following` VALUES (48, 3, 'fac95b1a3b404e28aace410f06c9da8a', '深圳', 'https://m.weibo.cn/p/cardlist?containerid=2306570043_114.08595_22.547&extparam=map__', '/images/user-profile-photo/65ab8a79484048b5817d3898d241c596vrx97y76.jfif', '深圳周边 打卡记录', b'0', b'0', '2021-01-03 10:09:19', '2021-01-03 10:09:19');
INSERT INTO `user_following` VALUES (49, 3, '8f40e38b366e46e19407498fc4eb1c64', '长沙', 'https://m.weibo.cn/p/cardlist?containerid=2306570043_112.98228_28.19409&extparam=map__', '/images/user-profile-photo/24433698f26e43ec977db2036ba98adf0cfqx1y6.jfif', '长沙周边 打卡记录', b'0', b'0', '2021-01-03 10:12:34', '2021-01-03 10:12:34');
INSERT INTO `user_following` VALUES (50, 3, '3807059740', 'HeyUke_', 'https://m.weibo.cn/u/3807059740', '/images/user-profile-photo/e2eb1f1cly8gg2j0bqcakj20u00u0jwg.jpg', '～', b'1', b'0', '2021-01-03 10:14:55', '2021-01-03 10:14:55');
INSERT INTO `user_following` VALUES (51, 3, '3176592573', '可爱小火-', 'https://m.weibo.cn/u/3176592573', '/images/user-profile-photo/bd56f4bdly8hp0tq48hqpj20by0byaaq.jpg', '蜜蜂的膝盖', b'1', b'0', '2021-01-03 10:17:20', '2021-01-03 10:17:20');
INSERT INTO `user_following` VALUES (52, 3, '3802350894', '关于赵灵', 'https://m.weibo.cn/u/3802350894', '/images/user-profile-photo/e2a3452ely8g3b0o16c01j20u00u0gnz.jpg', '52062', b'1', b'0', '2021-01-03 10:22:35', '2021-01-03 10:22:35');
INSERT INTO `user_following` VALUES (53, 2, '217108321', '陈暖央LunaSea', 'https://space.bilibili.com/217108321/dynamic', '/images/user-profile-photo/7eb2951d1ff33da1900b2423319cc899e434d82c.jpg', '一个爱健身的正经UP主，喜欢健身的关注我，不喜欢的我再想想办法~ 围脖：@陈暖央', b'1', b'0', '2021-04-07 16:45:41', '2021-05-20 08:04:37');
INSERT INTO `user_following` VALUES (54, 2, '29959830', 'Topbook', 'https://space.bilibili.com/29959830/video', '/images/user-profile-photo/1f0ff00ad152f286f1dc47af2aadc0abfe221921.jpg', '让工具回归工具，让你成为你「合作微信：yangfafala」。', b'1', b'0', '2021-04-07 16:55:27', '2021-04-07 16:55:27');
INSERT INTO `user_following` VALUES (55, 2, '547173382', '杨真直', 'https://space.bilibili.com/547173382/video', '/images/user-profile-photo/8a0a9f138771c304319cda85e4506b46cc714612.jpg', '公众号:杨真直 找我！商务微信：yangzz-001', b'1', b'0', '2021-04-07 16:56:25', '2021-04-07 16:56:25');
INSERT INTO `user_following` VALUES (56, 3, '2045254855', '__雷雨_', 'https://m.weibo.cn/u/2045254855', '/images/user-profile-photo/79e81cc7ly8gky4ig7rwhj20e80e874q.jpg', '知名旅游博主 有一个taobao店 🤷‍♀️', b'1', b'0', '2021-04-07 16:58:55', '2021-04-07 16:58:55');
INSERT INTO `user_following` VALUES (57, 3, '6488142313', 'LEIYU衣服好好看', 'https://m.weibo.cn/u/6488142313', '/images/user-profile-photo/00755AcFly8gmf6hdn42wj30e80e8t92.jpg', ' 淘宝店铺名：LEIYU STUDIO', b'1', b'0', '2021-04-07 17:00:19', '2021-04-07 17:00:19');
INSERT INTO `user_following` VALUES (58, 3, '7038906058', '用户刘妍汐', 'https://m.weibo.cn/u/7038906058', '/images/user-profile-photo/007Gmx0Sly8gb76xljde2j30ij0ijwef.jpg', ' 𝗠𝗼𝗱𝗲𝗹▫️𝗡𝗮𝗶𝗹𝗬𝗼𝗸𝗲▫️𝗖𝗧𝗕𝗨设计系大三在读▫️重庆女孩🌇', b'1', b'0', '2021-04-07 17:06:25', '2021-04-07 17:06:25');
INSERT INTO `user_following` VALUES (59, 2, '36416153', '周酷仔', 'https://space.bilibili.com/36416153/video', '/images/user-profile-photo/5508d4ca1d0739bf12afd4d6e997301c5b234b71.jpg', '快乐传播机', b'1', b'0', '2021-04-07 17:07:57', '2021-04-07 17:07:57');
INSERT INTO `user_following` VALUES (60, 3, '6864574333', 'ZlzYJh', 'https://m.weibo.cn/u/6864574333', '/images/user-profile-photo/007uz3mRly8gi6f6ew8txj30e80e8glu.jpg', ' 🎊🎊🎊', b'1', b'0', '2021-04-07 17:40:48', '2021-04-07 17:40:48');
INSERT INTO `user_following` VALUES (61, 3, '2289940200', '毛然-', 'https://m.weibo.cn/u/2289940200', '/images/user-profile-photo/887db6e8ly8gfjpt338arj20e80e8q3n.jpg', '摄影博主 约拍请私信', b'1', b'0', '2021-04-07 17:42:16', '2021-04-07 17:42:16');
INSERT INTO `user_following` VALUES (62, 3, '2731696573', '丧妹有点跳', 'https://m.weibo.cn/u/2731696573', '/images/user-profile-photo/a2d261bdly8gn6iidp8esj20ru0rvjt5.jpg', '微博vlog博主 🙋2.5次元半蠢不萌的妹子一只。🏡万年宅女🎐惬意慵懒的up主🙌一个想当裁缝能唱歌的舞者🙇合作事宜请私信~', b'1', b'0', '2021-04-07 17:45:30', '2021-04-07 17:45:30');
INSERT INTO `user_following` VALUES (63, 3, '2882083237', '陈暖央', 'https://m.weibo.cn/u/2882083237', '/images/user-profile-photo/abc919a5ly8gf3n6ovy7rj20u00u0tba.jpg', '暴走的萝莉品牌创始人 知名运动博主 TMall搜索：【暴走的萝莉】，工作洽谈发邮箱：nuan@s-loli.com 【ins:chennuanyang】', b'1', b'0', '2021-04-07 17:55:32', '2021-04-07 17:55:32');
INSERT INTO `user_following` VALUES (64, 3, '3920631851', '张饱饱baby', 'https://m.weibo.cn/u/3920631851', '/images/user-profile-photo/004hkzrBly8gnxtmbnfodj60u00u0ack02.jpg', '健身撰稿人 一位爱美妆的专业健身辣妹，关注我一起来健身变美吧！商务合作加VX：zxhdy13', b'1', b'0', '2021-04-20 16:54:55', '2021-04-20 16:54:55');
INSERT INTO `user_following` VALUES (65, 3, '1958509675', '豆豆波波茶是九九的最爱呀QAQ', 'https://m.weibo.cn/u/1958509675', '/images/user-profile-photo/74bc7c6bly8gy2xat2i9aj20e80e8js6.jpg', '', b'1', b'0', '2021-04-20 17:08:55', '2022-02-22 08:37:42');
INSERT INTO `user_following` VALUES (68, 2, '14803693', '林林Eileen', 'https://space.bilibili.com/14803693/video', '/images/user-profile-photo/38affc856e5c055bbac6e0cb7510a6619dd96cb5.jpg', '每周六20点更新!林林总总不定时更新！没过审的放微博/油管：王林林Eileen  商务请私信', b'1', b'0', '2021-06-30 13:01:34', '2021-06-30 13:01:34');
INSERT INTO `user_following` VALUES (69, 3, '1714361850', '酒酒九儿_', 'https://m.weibo.cn/u/5512266800', '/images/user-profile-photo/00612Up2ly8gurx5xyp29j60e80e80sz02.jpg', '摄影博主 深圳野生“女摄影”🤗 客片就是样片！一直都拍普通人！发现每个人的美！', b'1', b'0', '2021-07-01 06:19:42', '2021-10-07 09:10:50');
INSERT INTO `user_following` VALUES (70, 3, '5774026418', '-nighty-night-', 'https://m.weibo.cn/u/5774026418', '/images/user-profile-photo/006iLe2mly8gv6mh1jy4cj60e80e8wfh02.jpg', '麻辣拌窗口钉子户', b'1', b'0', '2021-10-07 09:11:44', '2021-10-07 09:11:45');
INSERT INTO `user_following` VALUES (71, 3, '6451935868', '夏安与秋', 'https://m.weibo.cn/u/6451935868', '/images/user-profile-photo/0072DFfKly8gifm216whxj30ig0igmym.jpg', '英雄联盟官方解说 哔哩哔哩电竞签约艺人 天天开心', b'1', b'0', '2021-11-07 03:34:25', '2021-11-07 03:34:27');
INSERT INTO `user_following` VALUES (72, 3, '5686050433', '徐谢顶不谢顶', 'https://m.weibo.cn/u/5686050433', '/images/user-profile-photo/006cO5sRly8gvewe60vzaj60e80e80te02.jpg', '是个沙雕', b'1', b'0', '2022-01-09 17:39:21', '2022-01-09 17:39:23');
INSERT INTO `user_following` VALUES (73, 3, '2119117274', 'L的零次方', 'https://m.weibo.cn/u/2119117274', '/images/user-profile-photo/7e4f29daly8gi38josoqrj20kv0kvgn8.jpg', '…', b'1', b'0', '2022-01-12 17:15:05', '2022-01-12 17:15:08');
INSERT INTO `user_following` VALUES (74, 3, '5662689907', '猫想Eliot', 'https://m.weibo.cn/u/5662689907', '/images/user-profile-photo/006be4kbly8gwenezmeqhj30e80e8dgj.jpg', '努力把我妈她女儿活好', b'1', b'0', '2022-01-13 03:33:46', '2022-01-13 03:33:48');
INSERT INTO `user_following` VALUES (75, 3, '3666306991', '每天都要来杯咖啡_', 'https://m.weibo.cn/u/3666306991', '/images/user-profile-photo/da8767afly8gyvt4ux98aj20u00u0jt8.jpg', '歐陽娜娜粉絲後援會💖 及 易烊千璽事業粉🌟✨💫   嘮嗑daily life🤎🤍🖤', b'1', b'0', '2022-01-13 12:43:45', '2022-01-13 12:43:48');
INSERT INTO `user_following` VALUES (76, 3, '5706360071', '是正英呀', 'https://m.weibo.cn/u/5706360071', '/images/user-profile-photo/006ebiVFly8guowyaijfqj60e80e8dg802.jpg', '深大18播本 174|50   🎵📕：是正英🐷呀   好好生活☺️  未来可期💕', b'1', b'0', '2022-01-13 19:50:43', '2022-01-13 19:50:49');
INSERT INTO `user_following` VALUES (77, 3, '5706360071', '是正英呀', 'https://m.weibo.cn/u/5706360071', '/images/user-profile-photo/006ebiVFly8guowyaijfqj60e80e8dg802.jpg', '深大18播本 174|50   🎵📕：是正英🐷呀   好好生活☺️  未来可期💕', b'1', b'0', '2022-01-13 19:50:48', '2022-01-13 19:50:51');
INSERT INTO `user_following` VALUES (78, 3, '3107592701', '猫寒l', 'https://m.weibo.cn/u/3107592701', '/images/user-profile-photo/b93a19fdly8gcccarqzazj20n10n1ta8.jpg', '一般心情极好或极坏时才会回私信', b'1', b'0', '2022-01-14 14:24:35', '2022-01-14 14:24:37');
INSERT INTO `user_following` VALUES (79, 3, '5618675864', '沐淋小雅', 'https://m.weibo.cn/u/5618675864', '/images/user-profile-photo/0068foggly8gqq4kurzfoj30u00u0gq9.jpg', '', b'1', b'0', '2022-01-15 03:43:05', '2022-01-15 03:43:06');
INSERT INTO `user_following` VALUES (80, 3, '2103801044', '纪念我的白牙', 'https://m.weibo.cn/u/2103801044', '/images/user-profile-photo/7d6574d4jw1e8qgp5bmzyj2050050aa8.jpg', '你做不到的，时间会做到。', b'1', b'0', '2022-01-15 03:47:31', '2022-01-15 03:47:32');
INSERT INTO `user_following` VALUES (83, 3, '3085570393', 'xxxxfirstblood', 'https://m.weibo.cn/u/3085570393', '/images/user-profile-photo/b7ea1159jw8faa5vb6s3lj20e70e83zt.jpg', '', b'1', b'0', '2022-01-15 04:14:32', '2022-01-15 04:14:34');
INSERT INTO `user_following` VALUES (84, 3, '5873688171', '羚小柴Antelope', 'https://m.weibo.cn/u/5873688171', '/images/user-profile-photo/006pvoBZly8gycajshye2j30e80e23z8.jpg', '留🇬🇧四年，国际商务硕士毕业，现居深圳  一个碎碎念的频道 老年机🈚️微信，不看私信', b'1', b'0', '2022-01-15 04:18:56', '2022-01-15 04:18:57');
INSERT INTO `user_following` VALUES (85, 3, '2159411762', '-巴巴', 'https://m.weibo.cn/u/2159411762', '/images/user-profile-photo/80b60232ly8glklxijj3hj20e80e80td.jpg', '', b'1', b'0', '2022-01-17 17:35:07', '2022-01-17 17:35:08');
INSERT INTO `user_following` VALUES (86, 3, '1870685385', '-洋妞i', 'https://m.weibo.cn/u/1870685385', '/images/user-profile-photo/6f8064c9ly8g262b998cxj20u00u0tao.jpg', '别偷看我', b'1', b'0', '2022-01-17 17:44:28', '2022-01-17 17:44:29');
INSERT INTO `user_following` VALUES (87, 3, '3964354718', '芋圆加七熹', 'https://m.weibo.cn/u/3964354718', '/images/user-profile-photo/004ki1L8ly8gurlws2gdrj60e80e8wet02.jpg', '算是半个有趣的人', b'1', b'0', '2022-01-18 04:25:32', '2022-01-18 04:25:34');
INSERT INTO `user_following` VALUES (88, 3, '5890699887', 'love00h', 'https://m.weibo.cn/u/5890699887', '/images/user-profile-photo/006qEM8vly8fztngk3nxlj30ro0roq4o.jpg', '我就是我，想认识就聊起来😏😏', b'1', b'0', '2022-01-18 15:46:18', '2022-01-18 15:46:19');
INSERT INTO `user_following` VALUES (89, 3, '5700818943', 'luck1tree', 'https://m.weibo.cn/u/5700818943', '/images/user-profile-photo/006dO3qDly8gyi2r122vaj30b40b4wek.jpg', '阿湫', b'1', b'0', '2022-01-21 17:02:45', '2022-01-21 17:02:49');
INSERT INTO `user_following` VALUES (90, 3, '1813791511', 'ZZZ_YJIAL', 'https://m.weibo.cn/u/1813791511', '/images/user-profile-photo/6c1c4317ly8gybuepva9wj20e80e8js5.jpg', '', b'1', b'0', '2022-01-21 17:05:47', '2022-01-21 17:05:48');
INSERT INTO `user_following` VALUES (91, 3, '5494996708', 'Ing_ing', 'https://m.weibo.cn/u/5494996708', '/images/user-profile-photo/005ZSrFaly8gyh5k115tgj30e80e8q3j.jpg', '是但求其爱', b'1', b'0', '2022-01-21 17:17:38', '2022-01-21 17:17:39');
INSERT INTO `user_following` VALUES (92, 3, '6321553189', '火树ni', 'https://m.weibo.cn/u/6321553189', '/images/user-profile-photo/006TOALjly8gk348ad0egj30u00u0djx.jpg', '不管生活有没有变好，你都要自己变得更好。', b'1', b'0', '2022-01-21 17:21:50', '2022-01-21 17:21:51');
INSERT INTO `user_following` VALUES (93, 3, '5729554265', '阿宝姐姐_i', 'https://m.weibo.cn/u/5729554265', '/images/user-profile-photo/006fKCNzly8gnxmp40u0xj30ru0ru0vw.jpg', '海蓝时见🐳  梦醒时有你', b'1', b'0', '2022-01-22 03:24:48', '2022-01-22 03:24:49');
INSERT INTO `user_following` VALUES (94, 3, '5730468346', 'Nepenthe凝露', 'https://m.weibo.cn/u/5730468346', '/images/user-profile-photo/006fOsAOly8gn9779o1g1j30ru0rudic.jpg', '故人入我梦，明我长相忆。', b'1', b'0', '2022-01-22 07:53:13', '2022-01-22 07:53:14');
INSERT INTO `user_following` VALUES (95, 3, '5756134135', '可什么乐ke', 'https://m.weibo.cn/u/5756134135', '/images/user-profile-photo/006hy9r9ly8gqabdtz230j30ro0ro40x.jpg', '单身保平安', b'1', b'0', '2022-01-23 04:16:06', '2022-01-23 04:16:07');
INSERT INTO `user_following` VALUES (96, 3, '2202799097', '草莓小火锅hhh', 'https://m.weibo.cn/u/2202799097', '/images/user-profile-photo/834c0bf9ly8gxfe4adq9fj20e80e8dg3.jpg', 'Nice2🍖u', b'1', b'0', '2022-01-24 02:15:13', '2022-01-24 02:15:14');
INSERT INTO `user_following` VALUES (97, 3, '2569851321', '傻头傻脑歪', 'https://m.weibo.cn/u/2569851321', '/images/user-profile-photo/992cd1b9ly8gynuxrcsvmj20e80e8my2.jpg', '粉红色阿婆日记本', b'1', b'0', '2022-01-24 02:38:41', '2022-01-24 02:38:42');
INSERT INTO `user_following` VALUES (98, 3, '6491295094', 'Aurora-娴', 'https://m.weibo.cn/u/6491295094', '/images/user-profile-photo/0075iOnYly8gy67r3urqqj30u00u0dkk.jpg', '比昨天聪明 比去年自由🐛', b'1', b'0', '2022-01-24 06:06:02', '2022-01-24 06:06:03');
INSERT INTO `user_following` VALUES (99, 3, '5671157256', 'Cherri涛涛', 'https://m.weibo.cn/u/5671157256', '/images/user-profile-photo/006bNB4kly8gqkl65gqn2j30n00n0q51.jpg', 'Shine bright and shine far', b'1', b'0', '2022-01-24 06:24:37', '2022-01-24 06:24:39');
INSERT INTO `user_following` VALUES (100, 3, '1954149563', '阿茱的玫瑰花', 'https://m.weibo.cn/u/1954149563', '/images/user-profile-photo/7479f4bbly8fp6ym3up0jj20ro0roq4r.jpg', 'joker', b'1', b'0', '2022-01-24 06:35:35', '2022-01-24 06:35:36');
INSERT INTO `user_following` VALUES (101, 3, '3962685149', '咔咔爱吃酸', 'https://m.weibo.cn/u/3962685149', '/images/user-profile-photo/ec31c6ddly8goquerogf8j20n00n075r.jpg', '快乐每一天', b'1', b'0', '2022-01-24 09:15:12', '2022-01-24 09:15:14');
INSERT INTO `user_following` VALUES (102, 3, '5657916272', 'betterrr_', 'https://m.weibo.cn/u/5657916272', '/images/user-profile-photo/006aU2u4ly8grienr2j7fj30oo0oodhx.jpg', '脾气很差', b'1', b'0', '2022-01-24 09:38:44', '2022-01-24 09:38:45');
INSERT INTO `user_following` VALUES (103, 3, '6662998320', '小罗热爱生活h', 'https://m.weibo.cn/u/6662998320', '/images/user-profile-photo/007gVgeQly8gmaabdartjj30n00n00ut.jpg', '欢迎来到小罗的朋友圈', b'1', b'0', '2022-01-25 18:45:15', '2022-01-25 18:45:16');
INSERT INTO `user_following` VALUES (104, 3, '5180139097', 'DEWNAY-', 'https://m.weibo.cn/u/5180139097', '/images/user-profile-photo/005EzkOBly8glvne8jojdj30e80e874z.jpg', '都是我的负能量', b'1', b'0', '2022-01-26 03:03:05', '2022-01-26 03:03:06');
INSERT INTO `user_following` VALUES (105, 3, '5088847817', '小豆子田', 'https://m.weibo.cn/u/5088847817', '/images/user-profile-photo/005yohMBly8gkczh29p8cj30u00u0407.jpg', '', b'1', b'0', '2022-01-26 17:42:53', '2022-01-26 17:42:54');
INSERT INTO `user_following` VALUES (106, 3, '2697943084', 'Aya雅雅', 'https://m.weibo.cn/u/2697943084', '/images/user-profile-photo/a0cf582cly8gwkqfunsc9j20u00u0dhr.jpg', '', b'1', b'0', '2022-01-26 18:06:06', '2022-01-26 18:06:07');
INSERT INTO `user_following` VALUES (107, 3, '6493599689', '减肥真的好难呀', 'https://m.weibo.cn/u/6493599689', '/images/user-profile-photo/0075stURly8gyk8cu2cowj30e80e80st.jpg', '及时行乐.', b'1', b'0', '2022-01-27 15:20:23', '2022-01-27 15:20:31');
INSERT INTO `user_following` VALUES (108, 3, '5063051173', '-Cindy晶', 'https://m.weibo.cn/u/5063051173', '/images/user-profile-photo/005wE2THly8g1s15zyrkej30e80e8t9b.jpg', '你我皆会找到属于自己的路', b'1', b'0', '2022-01-27 17:33:41', '2022-01-27 17:33:43');
INSERT INTO `user_following` VALUES (109, 3, '5146369524', 'Ye离心', 'https://m.weibo.cn/u/5146369524', '/images/user-profile-photo/005ChDO4ly8g8hl54d55nj30ry0rydiu.jpg', '', b'1', b'0', '2022-01-27 17:37:34', '2022-01-27 17:37:35');
INSERT INTO `user_following` VALUES (110, 3, '5342808989', 'Lqian阿', 'https://m.weibo.cn/u/5342808989', '/images/user-profile-photo/005PzSGxly8g2tz5a2kztj30e80e8tae.jpg', '要开心❤️', b'1', b'0', '2022-01-28 05:38:40', '2022-01-28 05:38:42');
INSERT INTO `user_following` VALUES (111, 3, '1774297012', '月下仙猪', 'https://m.weibo.cn/u/1774297012', '/images/user-profile-photo/69c19fb4ly8fr25psezxkj20yi0yitbe.jpg', 'Funny，Honey，Money', b'1', b'0', '2022-01-28 05:53:56', '2022-01-28 05:53:58');
INSERT INTO `user_following` VALUES (112, 3, '2947791882', '我就叫阿楚诶', 'https://m.weibo.cn/u/2947791882', '/images/user-profile-photo/afb3bc0aly8gqcgp2a29ij20ro0rowfr.jpg', '超话粉丝大咖（施展超话） 千纸鹤的小喇叭 虎虎子的龙井茶', b'1', b'0', '2022-01-28 15:26:51', '2022-01-28 15:26:51');
INSERT INTO `user_following` VALUES (113, 3, '2351719542', 'Qiulinjiejie001', 'https://m.weibo.cn/u/2351719542', '/images/user-profile-photo/8c2c6476ly8gyd0g9vayej20tx0txq5t.jpg', '废话多多，看心情更新日常～', b'1', b'0', '2022-01-29 03:29:35', '2022-01-29 03:29:37');
INSERT INTO `user_following` VALUES (114, 3, '3793847754', '邢小庆', 'https://m.weibo.cn/u/3793847754', '/images/user-profile-photo/e22185caly8gleci6u75sj20u00u0aef.jpg', '中山大学附属第一医院黄埔院区护士 致富的道路上请务必身体健康…… 热爱生活  喜欢撸铁', b'1', b'0', '2022-01-30 04:11:19', '2022-01-30 04:11:20');
INSERT INTO `user_following` VALUES (115, 3, '5737359167', '肥肉好吃嘛', 'https://m.weibo.cn/u/5737359167', '/images/user-profile-photo/006ghnd5ly8gq96ujsk26j30u00u042h.jpg', '发光吧✨✨✨✨', b'1', b'0', '2022-01-31 03:13:44', '2022-01-31 03:13:45');
INSERT INTO `user_following` VALUES (116, 3, '5707210466', '吃吃大宝', 'https://m.weibo.cn/u/5707210466', '/images/user-profile-photo/006eeS9Ily8gtstvbqhdfj30ro0ro40x.jpg', '没有微信 发肌肉照直接拉黑', b'1', b'0', '2022-01-31 03:19:52', '2022-01-31 03:19:53');
INSERT INTO `user_following` VALUES (117, 3, '5953271313', '吃甜食了吗', 'https://m.weibo.cn/u/5953271313', '/images/user-profile-photo/006uTjP3ly8gcr5a1ffohj30e80e8aaf.jpg', '单曲循环', b'1', b'0', '2022-01-31 03:30:59', '2022-01-31 03:31:00');
INSERT INTO `user_following` VALUES (118, 3, '3771399594', '麻袋女娃_246', 'https://m.weibo.cn/u/3771399594', '/images/user-profile-photo/e0cafdaaly8gtd1s1kewgj20e80e8t95.jpg', '过年了，要快乐哦！', b'1', b'0', '2022-01-31 03:36:11', '2022-01-31 03:36:12');
INSERT INTO `user_following` VALUES (119, 3, '6406579309', 'yooorou', 'https://m.weibo.cn/u/6406579309', '/images/user-profile-photo/006ZzlWlly8gy4ym78vaej30u00u0whg.jpg', '叽里咕噜', b'1', b'0', '2022-02-01 04:03:09', '2022-02-01 04:03:11');
INSERT INTO `user_following` VALUES (120, 3, '3066715511', 'Ying_xn', 'https://m.weibo.cn/u/3066715511', '/images/user-profile-photo/b6ca5d77jw1e8qgp5bmzyj2050050aa8.jpg', '努力实现自己的梦想吧！！加油！', b'1', b'0', '2022-02-01 04:11:23', '2022-02-01 04:11:24');
INSERT INTO `user_following` VALUES (121, 3, '6442097782', '夏凉20170', 'https://m.weibo.cn/u/6442097782', '/images/user-profile-photo/0071YnUWly8hb15d0pq7ij30u00u00ug.jpg', '一个普通的深漂女孩👧🏻，这里记录我的碎碎念', b'1', b'0', '2022-02-01 04:16:08', '2022-02-01 04:16:10');
INSERT INTO `user_following` VALUES (122, 3, '6508090198', '小旖困了', 'https://m.weibo.cn/u/6508090198', '/images/user-profile-photo/0076rhyKly8gwx0j15ih2j30e80e8wfa.jpg', '欢迎来到刘昊然老婆的qq空间٩(๑`^´๑)۶', b'1', b'0', '2022-02-01 04:31:30', '2022-02-01 04:31:35');
INSERT INTO `user_following` VALUES (123, 3, '6567307847', 'a余好饿', 'https://m.weibo.cn/u/6567307847', '/images/user-profile-photo/007arKM7ly8gxsecqlh2uj30e80e8gm2.jpg', '傻逼远离我', b'1', b'0', '2022-02-01 04:32:22', '2022-02-01 04:32:23');
INSERT INTO `user_following` VALUES (124, 3, '7030497101', '你的宝藏女孩儿尼', 'https://m.weibo.cn/u/7030497101', '/images/user-profile-photo/007FNfsxly8gphttqhg7hj30ru0ruafj.jpg', '✈️ ✈️✈️我超可爱呀', b'1', b'0', '2022-02-01 18:14:38', '2022-02-01 18:14:39');
INSERT INTO `user_following` VALUES (125, 3, '5602830708', '囗可呵', 'https://m.weibo.cn/u/5602830708', '/images/user-profile-photo/0067aUdely8gygpmr3lapj30u00u0436.jpg', '一只唯十二的怪咖少女', b'1', b'0', '2022-02-01 18:41:10', '2022-02-01 18:41:11');
INSERT INTO `user_following` VALUES (126, 3, '5648009810', 'XinShona', 'https://m.weibo.cn/u/5648009810', '/images/user-profile-photo/006aetmqly8gvkxprf2txj60ro0roq4q02.jpg', '下雨🌧️了 はなさき花咲', b'1', b'0', '2022-02-02 04:21:49', '2022-02-02 04:21:50');
INSERT INTO `user_following` VALUES (127, 3, '6722933389', '真的发疯别管我了', 'https://m.weibo.cn/u/6722933389', '/images/user-profile-photo/007kYK5vly8hph465j1zvj30u00u0ta1.jpg', '别关注我，发疯。会骂人！', b'1', b'0', '2022-02-02 19:52:34', '2022-02-22 07:30:30');
INSERT INTO `user_following` VALUES (128, 3, '1995022467', '易个小女子', 'https://m.weibo.cn/u/1995022467', '/images/user-profile-photo/76e9a083ly8gdq619qx2ij20e80e83ys.jpg', '', b'1', b'0', '2022-02-03 04:04:16', '2022-02-03 04:04:18');
INSERT INTO `user_following` VALUES (129, 3, '2705780011', '乐小树_', 'https://m.weibo.cn/u/2705780011', '/images/user-profile-photo/002X7aZ5ly8gv4ydjcuhwj60n00n0ta102.jpg', '太阳的奶油', b'1', b'0', '2022-02-04 03:38:46', '2022-02-04 03:38:47');
INSERT INTO `user_following` VALUES (130, 3, '2683138122', 'ai-丫头-娟子', 'https://m.weibo.cn/u/2683138122', '/images/user-profile-photo/9fed704aly8ggi56d76j2j20u00u0jsh.jpg', '📝记录每天生活的点点滴滴📷', b'1', b'0', '2022-02-04 03:42:52', '2022-02-04 03:42:53');
INSERT INTO `user_following` VALUES (131, 3, '6733122833', '刘小样1372', 'https://m.weibo.cn/u/6733122833', '/images/user-profile-photo/007lFuPnly8ggxu0sdan2j30e80e8mya.jpg', '吃和睡之间徘徊', b'1', b'0', '2022-02-07 14:55:01', '2022-02-07 14:55:04');
INSERT INTO `user_following` VALUES (132, 3, '6245537032', '纯子uuuuu', 'https://m.weibo.cn/u/6245537032', '/images/user-profile-photo/006OFDugly8gshygio08rj30ro0ro42l.jpg', '', b'1', b'0', '2022-02-07 15:02:17', '2022-02-07 15:02:20');
INSERT INTO `user_following` VALUES (133, 3, '3218567470', '怪咖t7', 'https://m.weibo.cn/u/3218567470', '/images/user-profile-photo/bfd7712ely8g4jgpkupinj20ro0rowgp.jpg', '“智者不入爱河”', b'1', b'0', '2022-02-07 15:05:53', '2022-02-07 15:05:54');
INSERT INTO `user_following` VALUES (134, 3, '2429135977', 'lAmmmhing', 'https://m.weibo.cn/u/2429135977', '/images/user-profile-photo/90c9ac69ly8gdhykvkyffj20kg0kg74y.jpg', '设计美学博主 我的设计记录和生活', b'1', b'0', '2022-02-08 03:26:50', '2022-02-08 03:26:52');
INSERT INTO `user_following` VALUES (135, 3, '6814437198', '明小筠吖', 'https://m.weibo.cn/u/6814437198', '/images/user-profile-photo/007raGpoly8gwc2fkw45jj30s00s0abb.jpg', '吃好喝好 没有烦恼', b'1', b'0', '2022-02-08 03:29:38', '2022-02-08 03:29:39');
INSERT INTO `user_following` VALUES (136, 3, '2851545117', 'sjjzdy', 'https://m.weibo.cn/u/2851545117', '/images/user-profile-photo/a9f7201dly8fxgmak0cw2j20u00u0go0.jpg', '生活不将就。', b'1', b'0', '2022-02-08 03:35:39', '2022-02-08 03:35:40');
INSERT INTO `user_following` VALUES (137, 3, '6331847800', 'BettleMe柒', 'https://m.weibo.cn/u/6331847800', '/images/user-profile-photo/006UvMRqly8gxcm7qkmftj30e80e80tg.jpg', '开心就好', b'1', b'0', '2022-02-08 03:45:10', '2022-02-08 03:45:11');
INSERT INTO `user_following` VALUES (138, 3, '5661374594', '肖家姑娘CC', 'https://m.weibo.cn/u/5661374594', '/images/user-profile-photo/006b8y9sly8fvisdaxex1j30e80e8wf3.jpg', '保持微笑 努力工作', b'1', b'0', '2022-02-08 03:48:25', '2022-02-08 03:48:26');
INSERT INTO `user_following` VALUES (139, 3, '5839860915', 'Shobibi', 'https://m.weibo.cn/u/5839860915', '/images/user-profile-photo/006ndsB5ly8gxpuun1fauj30e80e8wfd.jpg', 'Hello', b'1', b'0', '2022-02-09 15:03:13', '2022-02-09 15:03:14');
INSERT INTO `user_following` VALUES (140, 3, '5594887483', 'i日落和花', 'https://m.weibo.cn/u/5594887483', '/images/user-profile-photo/0066DzOHly8gxpvy7399ej30u00u0abw.jpg', '', b'1', b'0', '2022-02-09 17:43:54', '2022-02-09 17:43:56');
INSERT INTO `user_following` VALUES (141, 3, '5214036614', 'Gracie关心', 'https://m.weibo.cn/u/5214036614', '/images/user-profile-photo/005GRz6Kly8gxlwzjswqmj30u00u0wfv.jpg', 'you take all my inhibitions', b'1', b'0', '2022-02-10 12:19:25', '2022-02-10 12:19:28');
INSERT INTO `user_following` VALUES (142, 3, '5955093300', '不让你们找到我的微博', 'https://m.weibo.cn/u/5955093300', '/images/user-profile-photo/006v0XNWly8gczmawe1otj30e80e8t92.jpg', '平凡世界其中一个的我', b'1', b'0', '2022-02-10 15:20:06', '2022-02-10 15:20:07');
INSERT INTO `user_following` VALUES (143, 3, '3486995790', '是黄小姐呀_', 'https://m.weibo.cn/u/3486995790', '/images/user-profile-photo/cfd7554ely8g5z9n2yt7hj20ku0ku777.jpg', '分享日常、爱拍照、保持开心✨', b'1', b'0', '2022-02-10 15:25:56', '2022-02-10 15:25:59');
INSERT INTO `user_following` VALUES (144, 3, '5012303028', 'R木子柒月', 'https://m.weibo.cn/u/5012303028', '/images/user-profile-photo/005td6Zely8gfqbifvi38j30ro0rojs4.jpg', '生活如果没有柴米油盐,我想每个女人都会既温柔又美丽~ ​', b'1', b'0', '2022-02-10 15:31:28', '2022-02-10 15:31:32');
INSERT INTO `user_following` VALUES (145, 3, '6074422044', 'farewell_shark_', 'https://m.weibo.cn/u/6074422044', '/images/user-profile-photo/006D5EF6ly8gx38y28l9wj30u00u0wg7.jpg', '再见容易 再见难', b'1', b'0', '2022-02-10 15:48:51', '2022-02-10 15:48:53');
INSERT INTO `user_following` VALUES (146, 3, '7630766192', '八里喵喵016', 'https://m.weibo.cn/u/7630766192', '/images/user-profile-photo/008kpUS4ly8gye7yeszclj30ro0ro76k.jpg', '', b'1', b'0', '2022-02-11 02:33:43', '2022-02-11 02:33:45');
INSERT INTO `user_following` VALUES (147, 3, '5632635504', 'eachi', 'https://m.weibo.cn/u/5632635504', '/images/user-profile-photo/0069bXNKly8ggjopria8nj30n00n0dgq.jpg', '你所见即我，我不反驳', b'1', b'0', '2022-02-11 02:40:26', '2022-02-11 02:40:28');
INSERT INTO `user_following` VALUES (148, 3, '6232473991', '程颖Ybb', 'https://m.weibo.cn/u/6232473991', '/images/user-profile-photo/006NMPc3ly8goioqzztk6j30n00n0wg6.jpg', '', b'1', b'0', '2022-02-11 02:51:47', '2022-02-11 02:51:49');
INSERT INTO `user_following` VALUES (149, 3, '1845930974', '每天都在努力的Yiyo', 'https://m.weibo.cn/u/1845930974', '/images/user-profile-photo/6e06abdely8gxetajtfhxj20e80e8aaf.jpg', '欢迎光临我的鸡汤小屋🛖', b'1', b'0', '2022-02-13 15:09:32', '2022-02-13 15:09:36');
INSERT INTO `user_following` VALUES (150, 3, '5894359434', '大鱼等于二since', 'https://m.weibo.cn/u/5894359434', '/images/user-profile-photo/006qU89sly8gbuvxp08dcj30u00u0adr.jpg', '', b'1', b'0', '2022-02-17 03:06:02', '2022-02-17 03:06:04');
INSERT INTO `user_following` VALUES (151, 3, '2146622003', '肥肥吃胖胖_', 'https://m.weibo.cn/u/2146622003', '/images/user-profile-photo/7ff2da33ly8gyyh8dmjp5j20e80e8t9f.jpg', '是个絮絮叨叨的小号😴 别给我发私信', b'1', b'0', '2022-02-18 03:32:59', '2022-02-18 03:33:00');
INSERT INTO `user_following` VALUES (152, 3, '2344960453', 'BLUe啊-Lam', 'https://m.weibo.cn/u/2344960453', '/images/user-profile-photo/8bc541c5ly8gsnsfn3a8wj20n00n00uc.jpg', '该用户已设置来访信息登记.', b'1', b'0', '2022-02-18 03:39:12', '2022-02-18 03:39:13');
INSERT INTO `user_following` VALUES (153, 3, '3927221237', 'yiyiyiyi羊', 'https://m.weibo.cn/u/3927221237', '/images/user-profile-photo/ea14a3f5ly8ghqszczbguj20ig0igaaz.jpg', '', b'1', b'0', '2022-02-18 03:42:21', '2022-02-18 03:42:23');
INSERT INTO `user_following` VALUES (154, 3, '5821012310', '网民娜娜', 'https://m.weibo.cn/u/5821012310', '/images/user-profile-photo/006lWndkly8g8n78kzttbj30kv0kvq3s.jpg', '著名电视剧 电影观众 在知名平台都冲过会员', b'1', b'0', '2022-02-19 13:23:32', '2022-02-19 13:23:34');
INSERT INTO `user_following` VALUES (156, 3, '2261496181', '矮纠啊_', 'https://m.weibo.cn/u/2261496181', '/images/user-profile-photo/86cbb175ly8gdqe4jza99j20ro0ro40g.jpg', '谢谢你来看我', b'1', b'0', '2022-02-20 03:13:30', '2022-02-20 03:13:32');
INSERT INTO `user_following` VALUES (157, 3, '7413399709', '肆了个98', 'https://m.weibo.cn/u/7413399709', '/images/user-profile-photo/0085HRV3ly8gzedtgp1uej30e80e8jrv.jpg', 'me myself and i', b'1', b'0', '2022-02-20 03:41:04', '2022-02-20 03:41:05');
INSERT INTO `user_following` VALUES (158, 3, '5896876153', 'xx今天不困了', 'https://m.weibo.cn/u/5896876153', '/images/user-profile-photo/006r4GRHly8goyxn0b18tj30ro0ro41q.jpg', '真的睡醒了', b'1', b'0', '2022-02-20 03:45:46', '2022-02-20 03:45:48');
INSERT INTO `user_following` VALUES (159, 3, '2064438133', '带狗去海边散步', 'https://m.weibo.cn/u/2064438133', '/images/user-profile-photo/7b0cd375ly8ga5uzk4qd4j20ow0owmzg.jpg', '不卑不亢', b'1', b'0', '2022-02-20 03:48:05', '2022-02-20 03:48:08');
INSERT INTO `user_following` VALUES (160, 3, '6225566402', 'WEINA0619', 'https://m.weibo.cn/u/6225566402', '/images/user-profile-photo/006NjQdkly8gz4ebpvb0kj30u00u041v.jpg', '你的名字～', b'1', b'0', '2022-02-20 03:50:04', '2022-02-20 03:50:06');
INSERT INTO `user_following` VALUES (161, 3, '3907149988', '歪了纽扣-', 'https://m.weibo.cn/u/3907149988', '/images/user-profile-photo/e8e260a4ly8gyvj5nh52vj20n00n040e.jpg', 'gunyuandian', b'1', b'0', '2022-02-20 03:54:18', '2022-02-20 03:54:20');
INSERT INTO `user_following` VALUES (162, 3, '6637647935', '绵绵MIYA', 'https://m.weibo.cn/u/6637647935', '/images/user-profile-photo/007fcTrFly8gz9xowtvg1j30e80e8q3v.jpg', 'MIYA的蜜桃臀计划', b'1', b'0', '2022-02-20 03:56:15', '2022-02-20 03:56:16');
INSERT INTO `user_following` VALUES (163, 3, '3217204293', 'xinxinyeh', 'https://m.weibo.cn/u/3217204293', '/images/user-profile-photo/bfc2a445ly8grnntw603xj20n00n0407.jpg', '干什么都要努力💪啊', b'1', b'0', '2022-02-21 03:16:25', '2022-02-21 03:16:27');
INSERT INTO `user_following` VALUES (164, 3, '2841588601', 'Hey糖糖啊', 'https://m.weibo.cn/u/2841588601', '/images/user-profile-photo/a95f3379ly8gzjhp0ahk3j20e80e8q3e.jpg', '希望你 拥有一个有趣的灵魂', b'1', b'0', '2022-02-21 15:20:16', '2022-02-21 15:20:21');
INSERT INTO `user_following` VALUES (165, 3, '5694931339', '速写班长', 'https://m.weibo.cn/u/5694931339', '/images/user-profile-photo/006dplNhly8gqeoyjwbhxj30n10n175g.jpg', '速写60天挑战赛，置顶微博第十一期(3.1-4.29)报名中。', b'1', b'0', '2022-02-22 07:28:52', '2022-02-22 07:28:53');
INSERT INTO `user_following` VALUES (166, 3, '5352616294', '椰嗝呀Ooooooo', 'https://m.weibo.cn/u/5352616294', '/images/user-profile-photo/005Qf20Sly8gxn60wti2cj30u00u0q4p.jpg', 'jojo，小排球，家教，猎人四坑，我真是嗨到不行🤤', b'1', b'0', '2022-02-22 07:32:53', '2022-02-22 07:32:54');
INSERT INTO `user_following` VALUES (167, 3, '5609402521', '喝熬夹', 'https://m.weibo.cn/u/5609402521', '/images/user-profile-photo/0067CtQdly8gy49fvw68gj30u00u0ado.jpg', '😘️😘️😘️谢谢戳进来的你！❤️❤️❤️❤️💛️💚️💙️💜️', b'1', b'0', '2022-02-22 07:33:23', '2022-02-22 07:33:24');
INSERT INTO `user_following` VALUES (168, 3, '5195254506', '小原惠子的日常', 'https://m.weibo.cn/u/5195254506', '/images/user-profile-photo/005FAL1wly8h1vn7qphy9j30u00u0dka.jpg', '看见光 追随光 成为光 散发光✨', b'1', b'0', '2022-02-22 11:55:35', '2022-02-22 11:55:35');
INSERT INTO `user_following` VALUES (169, 3, '7312821705', '泺卜花', 'https://m.weibo.cn/u/7312821705', '/images/user-profile-photo/007YTQZbly8gz43s07e0sj30oh0ohwh3.jpg', '你的人生好似朗朗月光，岂能让他人指手画脚什麽“随处可见”', b'1', b'0', '2022-02-22 16:21:45', '2022-02-22 16:21:51');
INSERT INTO `user_following` VALUES (170, 3, '5168931978', '-白沐阳-', 'https://m.weibo.cn/u/5168931978', '/images/user-profile-photo/005DOjkCly8g4xwo1a1k1j30u00u0jt9.jpg', '', b'1', b'0', '2022-02-22 16:22:18', '2022-02-22 16:22:20');
INSERT INTO `user_following` VALUES (171, 3, '7718256072', '滚粪球的甲虫', 'https://m.weibo.cn/u/7718256072', '/images/user-profile-photo/008ql0Zaly8gvtwwm2z2wj30go0gogqv.jpg', '我：咕咕咕', b'1', b'0', '2022-02-22 16:22:37', '2022-02-22 16:22:47');
INSERT INTO `user_following` VALUES (172, 3, '1813752794', '让鱼子飞', 'https://m.weibo.cn/u/1813752794', '/images/user-profile-photo/6c1babdaly8gprd9lg0nmj20u00u0tbu.jpg', '不定时抽风', b'1', b'0', '2022-02-22 16:23:13', '2022-02-22 16:23:15');
INSERT INTO `user_following` VALUES (173, 3, '1651239771', '鼠冇病', 'https://m.weibo.cn/u/1651239771', '/images/user-profile-photo/001NKqTxly8guo079yzosj60u00u0t9902.jpg', '动漫博主 ⚠️微博废话大师⚠️', b'1', b'0', '2022-02-22 16:24:26', '2022-02-22 16:24:28');
INSERT INTO `user_following` VALUES (174, 3, '7302896775', '野生猫仔粥', 'https://m.weibo.cn/u/7302896775', '/images/user-profile-photo/007Yed3Fly8gq6qgwtmwsj30ro0roglz.jpg', '安静画画', b'1', b'0', '2022-02-22 16:24:43', '2022-02-22 16:24:44');
INSERT INTO `user_following` VALUES (175, 3, '6132224846', '狩猎吃饱睡睡醒吃', 'https://m.weibo.cn/u/6132224846', '/images/user-profile-photo/006H0bOmly8gyh3oyhoxtj30iv0ivwfr.jpg', '小朋友都是要早点睡觉滴', b'1', b'0', '2022-02-22 16:25:04', '2022-02-22 16:25:05');
INSERT INTO `user_following` VALUES (176, 3, '6987187724', '-木头箱', 'https://m.weibo.cn/u/6987187724', '/images/user-profile-photo/007CRwIcly8gyhi4skhfvj30e80e8dg6.jpg', 'B站up主:木头箱，希望大家多多关照', b'1', b'0', '2022-02-22 16:25:25', '2022-02-22 16:25:29');
INSERT INTO `user_following` VALUES (177, 3, '6355514351', 'LM一莱蒙', 'https://m.weibo.cn/u/6355514351', '/images/user-profile-photo/006W75BZly8gsxuqywh2cj30ro0rowg2.jpg', '', b'1', b'0', '2022-02-22 16:25:47', '2022-02-22 16:25:48');
INSERT INTO `user_following` VALUES (178, 3, '2079003477', '千采今天画画了吗', 'https://m.weibo.cn/u/2079003477', '/images/user-profile-photo/7beb1355ly8g1e76my5tkj20oo0oomyh.jpg', '画画和表达都需要勇敢一点', b'1', b'0', '2022-02-22 16:26:03', '2022-02-22 16:26:07');
INSERT INTO `user_following` VALUES (179, 3, '3243560923', 'ayu想食麦当当', 'https://m.weibo.cn/u/3243560923', '/images/user-profile-photo/003xvEmLly8gu2o10cqskj60qy0qyaaj02.jpg', '好想天天吃麦当当……。', b'1', b'0', '2022-02-22 16:26:21', '2022-02-22 16:26:25');
INSERT INTO `user_following` VALUES (180, 3, '5819501372', '無霱', 'https://m.weibo.cn/u/5819501372', '/images/user-profile-photo/006lQ29mjw8fdfcxcybc9j30ro0rp0wi.jpg', '', b'1', b'0', '2022-02-22 16:26:40', '2022-02-22 16:26:41');
INSERT INTO `user_following` VALUES (181, 3, '1725187401', 'mika小柴犬', 'https://m.weibo.cn/u/1725187401', '/images/user-profile-photo/66d44549ly8febws82na3j20yi0zxact.jpg', '快看漫画《明星老哥请出招！》作者。[养不了小柴犬的小柴犬]  [喜欢的人是金牛座]', b'1', b'0', '2022-02-22 16:26:56', '2022-02-22 16:26:58');
INSERT INTO `user_following` VALUES (182, 3, '5187435043', '弹壳小猪', 'https://m.weibo.cn/u/5187435043', '/images/user-profile-photo/005F3WP9ly8gpv8dcicjmj30ro0romy8.jpg', '我是荷花，快乐冲浪', b'1', b'0', '2022-02-22 16:27:12', '2022-02-22 16:27:14');
INSERT INTO `user_following` VALUES (183, 3, '6989421791', '小鸟拖地', 'https://m.weibo.cn/u/6989421791', '/images/user-profile-photo/007D0TTxly8gz2shznf5aj30n20n20ti.jpg', '', b'1', b'0', '2022-02-22 16:27:31', '2022-02-22 16:27:32');
INSERT INTO `user_following` VALUES (184, 3, '2922851241', '塑尿池', 'https://m.weibo.cn/u/2922851241', '/images/user-profile-photo/ae372ba9ly8gzixys7pwaj20sg0sgte3.jpg', 'lofter：塑尿池', b'1', b'0', '2022-02-22 16:27:47', '2022-02-22 16:27:49');
INSERT INTO `user_following` VALUES (185, 3, '5234848725', '春原套套', 'https://m.weibo.cn/u/5234848725', '/images/user-profile-photo/005IgThXly8gyuwjaa5j4j30u00u0n1d.jpg', '世界围着桃子转，CP跟着亲友走。', b'1', b'0', '2022-02-22 16:28:00', '2022-02-22 16:28:02');
INSERT INTO `user_following` VALUES (186, 3, '7384667784', '阿突突阿', 'https://m.weibo.cn/u/7384667784', '/images/user-profile-photo/0083LjqUly8gzkem2upooj30u00u0jtp.jpg', '普通普通人', b'1', b'0', '2022-02-22 16:28:13', '2022-02-22 16:28:15');
INSERT INTO `user_following` VALUES (187, 3, '5652055552', '养狗吃人', 'https://m.weibo.cn/u/5652055552', '/images/user-profile-photo/006avrQkly8gy20gr5mhtj30e80e8q3g.jpg', '做好自己的就好', b'1', b'0', '2022-02-22 16:28:29', '2022-02-22 16:28:30');
INSERT INTO `user_following` VALUES (188, 3, '6317040095', 'Yolkya_', 'https://m.weibo.cn/u/6317040095', '/images/user-profile-photo/006TvEHtly8gr8w29y1pej30n00n076i.jpg', '自由插画师', b'1', b'0', '2022-02-22 16:28:46', '2022-02-22 16:28:48');
INSERT INTO `user_following` VALUES (190, 3, '5885604126', '玄鸦花笠', 'https://m.weibo.cn/u/5885604126', '/images/user-profile-photo/006qjouOly8gxpyt09jh0j30n00n0401.jpg', '动漫博主 B站阿婆主，ID：玄鸦花笠 麻烦mxtx的粉丝离我远一点', b'1', b'0', '2022-02-22 16:29:08', '2022-02-22 16:29:29');
INSERT INTO `user_following` VALUES (191, 3, '5436431993', '阿尔开花', 'https://m.weibo.cn/u/5436431993', '/images/user-profile-photo/005VUIiZly8gtdal94okuj30ro0roq71.jpg', '一个破打游戏的 偶尔画画 头像来源@呱呱老师', b'1', b'0', '2022-02-22 16:29:38', '2022-02-22 16:29:40');
INSERT INTO `user_following` VALUES (192, 3, '5722423536', '存在感锡箔_', 'https://m.weibo.cn/u/5722423536', '/images/user-profile-photo/006fgHLOly8gky7d15aj4j30ij0ijwi8.jpg', '存在感稀薄', b'1', b'0', '2022-02-22 16:30:00', '2022-02-22 16:30:01');
INSERT INTO `user_following` VALUES (193, 3, '5650189581', '烟熏鱼子', 'https://m.weibo.cn/u/5650189581', '/images/user-profile-photo/006anCq1ly8g3dvqwh6bjj30e80e8t8z.jpg', '这里鱼鱼，之前是个小号关注比较多，养蛛注意⚠️（求求不要块转捏！🥺）', b'1', b'0', '2022-02-22 16:30:18', '2022-02-22 16:30:20');
INSERT INTO `user_following` VALUES (194, 3, '5274032355', '加1很简单', 'https://m.weibo.cn/u/5274032355', '/images/user-profile-photo/005KViJZly8gvl09i9rgmj60sc0sc75o02.jpg', '愿世界温柔以待🤗', b'1', b'0', '2022-02-22 16:45:49', '2022-02-22 16:45:50');
INSERT INTO `user_following` VALUES (195, 3, '7607990513', '吞吞儿_', 'https://m.weibo.cn/u/7607990513', '/images/user-profile-photo/008iSlSply8gy0qlzltw6j30n10n13zn.jpg', '身体是革命的本钱 头像是本人的鼠鼠🐹', b'1', b'0', '2022-02-22 16:46:03', '2022-02-22 16:46:25');
INSERT INTO `user_following` VALUES (196, 3, '7607990513', '吞吞儿_', 'https://m.weibo.cn/u/7607990513', '/images/user-profile-photo/008iSlSply8gy0qlzltw6j30n10n13zn.jpg', '身体是革命的本钱 头像是本人的鼠鼠🐹', b'1', b'0', '2022-02-22 16:46:19', '2022-02-22 16:46:24');
INSERT INTO `user_following` VALUES (197, 3, '5215047714', '立里liliy', 'https://m.weibo.cn/u/5215047714', '/images/user-profile-photo/005GVO8Oly8gpmum1b3fyj30ro0rogn5.jpg', '', b'1', b'0', '2022-02-22 16:46:37', '2022-02-22 16:46:38');
INSERT INTO `user_following` VALUES (198, 3, '7397615491', '犬布屋屋_', 'https://m.weibo.cn/u/7397615491', '/images/user-profile-photo/0084DDITly8gzjc172s23j30sn0snq5n.jpg', '-piu/屋屋/犬布/ 今天黑芝麻糊piu也没在画汪…', b'1', b'0', '2022-02-22 16:46:52', '2022-02-22 16:50:03');
INSERT INTO `user_following` VALUES (200, 3, '7397615491', '犬布屋屋_', 'https://m.weibo.cn/u/7397615491', '/images/user-profile-photo/0084DDITly8gzjc172s23j30sn0snq5n.jpg', '-piu/屋屋/犬布/ 今天黑芝麻糊piu也没在画汪…', b'1', b'0', '2022-02-22 16:47:46', '2022-02-22 16:47:48');
INSERT INTO `user_following` VALUES (201, 3, '2688682712', 'kosyt', 'https://www.weibo.com/u/2688682712', '/images/user-profile-photo/a0420ad8ly8grvwb5vhn3j20u00u0n2e.jpg', '网易GACHA资深画师 憎恶。平庸。废物。', b'1', b'0', '2022-02-22 16:48:37', '2022-02-22 16:48:39');
INSERT INTO `user_following` VALUES (202, 3, '6591725075', '11凉白开', 'https://m.weibo.cn/u/6591725075', '/images/user-profile-photo/007c6cOnly8gwsmzmkcapj30u00u0q61.jpg', '动漫博主 未关注私信已关/评论都会看', b'1', b'0', '2022-02-22 16:48:56', '2022-02-22 16:48:58');
INSERT INTO `user_following` VALUES (203, 3, '6076444300', '响熬', 'https://m.weibo.cn/u/6076444300', '/images/user-profile-photo/006De8K8ly8gt5xkn5gnhj30kg0kgt9t.jpg', '(›´ω`‹ )', b'1', b'0', '2022-02-22 16:49:11', '2022-02-22 16:49:12');
INSERT INTO `user_following` VALUES (204, 3, '5856499332', 'i闪光小虎', 'https://m.weibo.cn/u/5856499332', '/images/user-profile-photo/006olh0Ely8gy6cho05yvj30u00u0q48.jpg', '今天是个好人，明天不一定', b'1', b'0', '2022-02-22 16:49:34', '2022-02-22 16:49:36');
INSERT INTO `user_following` VALUES (205, 3, '3123364095', '_芥末洋葱圈', 'https://m.weibo.cn/u/3123364095', '/images/user-profile-photo/ba2ac0ffly8grfnv011yij20u00u0gn0.jpg', '大量鸡叫发生注意⚠️每天都在挨饿jpg', b'1', b'0', '2022-02-22 16:49:53', '2022-02-22 16:49:54');
INSERT INTO `user_following` VALUES (206, 3, '7434829495', '糊了糊了糊了', 'https://m.weibo.cn/u/7434829495', '/images/user-profile-photo/00879MMLly8gxos6wnlk2j30le0le75g.jpg', '', b'1', b'0', '2022-02-22 16:50:10', '2022-02-22 16:50:11');
INSERT INTO `user_following` VALUES (207, 3, '5998864904', '-望岸-', 'https://m.weibo.cn/u/5998864904', '/images/user-profile-photo/006xYCNyly8gzmcmrgmz5j30u00u0ae3.jpg', '少上微博了，推特@wangan_______', b'1', b'0', '2022-02-22 16:50:26', '2022-02-22 16:50:28');
INSERT INTO `user_following` VALUES (208, 3, '3543968553', '我怎么还没有暴富', 'https://m.weibo.cn/u/3543968553', '/images/user-profile-photo/d33cab29ly8fxiyqn0p09j20e80e8aah.jpg', '垃圾填埋场。', b'1', b'0', '2022-02-22 16:50:44', '2022-02-22 16:50:45');
INSERT INTO `user_following` VALUES (209, 3, '5852469530', '惠惠_Vicky', 'https://m.weibo.cn/u/5852469530', '/images/user-profile-photo/006o4mFQly8gubbvofhwxj60ig0igq3v02.jpg', '未曾谋面的也终将会相遇🐥🦄', b'1', b'0', '2022-02-25 12:07:52', '2022-02-25 12:07:54');
INSERT INTO `user_following` VALUES (210, 3, '5033697878', '妹岛曲奇', 'https://m.weibo.cn/u/5033697878', '/images/user-profile-photo/005uESLsly8gyjfnmfghfj30e80e8wez.jpg', '国家一级未注册建筑师', b'1', b'0', '2022-02-25 12:29:38', '2022-02-25 12:29:39');
INSERT INTO `user_following` VALUES (211, 3, '5409908134', '要吃顿火锅', 'https://m.weibo.cn/u/5409908134', '/images/user-profile-photo/005U7qeOly8g4bl5cbi5yj30ro0rojtj.jpg', '生活总要往前看嘛，冲呀！', b'1', b'0', '2022-02-25 15:05:40', '2022-02-25 15:05:43');
INSERT INTO `user_following` VALUES (212, 3, '5720479956', 'The_Joyee', 'https://m.weibo.cn/u/5720479956', '/images/user-profile-photo/006f8y9Kly8gywqsp58s1j30o30o375w.jpg', '青春不易 请一鼓作气💪', b'1', b'0', '2022-02-25 15:08:34', '2022-02-25 15:08:41');
INSERT INTO `user_following` VALUES (213, 3, '1890300162', 'Kindred灵', 'https://m.weibo.cn/u/1890300162', '/images/user-profile-photo/70abb102jw8fcls3a7rutj20v90v3wgw.jpg', '这个城市没有脆弱的人,人们都强壮得像狮子,孤单得像蚂蚁.', b'1', b'0', '2022-02-26 02:16:35', '2022-02-26 02:16:36');
INSERT INTO `user_following` VALUES (214, 3, '5128894779', 'rurureal', 'https://m.weibo.cn/u/5128894779', '/images/user-profile-photo/005B6jPlly8gzi778pgscj30u00u00uu.jpg', '小茹的成长记录', b'1', b'0', '2022-02-26 02:22:34', '2022-02-26 02:22:35');
INSERT INTO `user_following` VALUES (215, 3, '5297345485', '月亮才不会为你而奔来', 'https://m.weibo.cn/u/5297345485', '/images/user-profile-photo/005Mv7ydly8gm8zomp1igj30e80e8q3j.jpg', '我知道我不够优秀 但我在努力 我会越来越好 越来越好 越来越好', b'1', b'0', '2022-02-26 02:26:35', '2022-02-26 02:26:36');
INSERT INTO `user_following` VALUES (216, 3, '3675941954', '--Jiatong', 'https://m.weibo.cn/u/3675941954', '/images/user-profile-photo/db1a6c42ly8glmo8oq9g6j20u00u0abz.jpg', '', b'1', b'0', '2022-02-26 02:35:52', '2022-02-26 02:35:54');
INSERT INTO `user_following` VALUES (217, 3, '5664503327', '大橘鱼尾中', 'https://m.weibo.cn/u/5664503327', '/images/user-profile-photo/006blG4Tly8ggwsfwl75ij30n00n0757.jpg', '失去梦想变成大鸡腿。', b'1', b'0', '2022-02-26 02:44:03', '2022-02-26 02:44:04');
INSERT INTO `user_following` VALUES (218, 3, '6122801629', 'Christom99', 'https://m.weibo.cn/u/6122801629', '/images/user-profile-photo/006GmEoZly8gnooq8bb6yj30n00n00tz.jpg', '欢迎来到汤汤的微博空间～🐷', b'1', b'0', '2022-02-26 15:45:10', '2022-02-26 15:45:12');
INSERT INTO `user_following` VALUES (219, 3, '6709633383', 'A碎雨飞', 'https://m.weibo.cn/u/6709633383', '/images/user-profile-photo/007k4W9hly8gwf3spwkr8j30ro0roac8.jpg', '撒野', b'1', b'0', '2022-02-27 03:50:27', '2022-02-27 03:50:28');
INSERT INTO `user_following` VALUES (220, 3, '6627202201', 'CongmingSonya', 'https://m.weibo.cn/u/6627202201', '/images/user-profile-photo/007ev425ly8gy91ay5wmyj30u00u0gob.jpg', '你心里藏着什么', b'1', b'0', '2022-02-27 06:31:30', '2022-02-27 06:31:32');
INSERT INTO `user_following` VALUES (221, 3, '5837443217', '-Zzxxw', 'https://m.weibo.cn/u/5837443217', '/images/user-profile-photo/006n3jDXly8gz0a48nxszj30e80e80t9.jpg', '', b'1', b'0', '2022-03-01 11:21:41', '2022-03-01 11:21:42');
INSERT INTO `user_following` VALUES (222, 3, '5562005697', 'forever-linbaby', 'https://m.weibo.cn/u/5562005697', '/images/user-profile-photo/0064pBLjly8gy76lxhk6jj30e80e80th.jpg', '日常|探店|美食|摄影  热爱生活是心中的太阳', b'1', b'0', '2022-03-01 11:24:02', '2022-03-01 11:24:03');
INSERT INTO `user_following` VALUES (223, 3, '1937076040', 'smile小细', 'https://m.weibo.cn/u/1937076040', '/images/user-profile-photo/73756f48ly8gfmmrhuvr4j20ro0rowgo.jpg', '不求多福，只求一生健康！', b'1', b'0', '2022-03-01 11:28:31', '2022-03-01 11:28:33');
INSERT INTO `user_following` VALUES (224, 3, '5130658975', '杨涵Yayayay', 'https://m.weibo.cn/u/5130658975', '/images/user-profile-photo/005BdIM7ly8goh161rdsbj30u00u0dj1.jpg', '一个知道自己想要什么的普通人', b'1', b'0', '2022-03-01 17:31:48', '2022-03-01 17:31:49');
INSERT INTO `user_following` VALUES (225, 3, '5961160216', '笨笨不爱哭鼻子辽', 'https://m.weibo.cn/u/5961160216', '/images/user-profile-photo/006vqq5qly8gya0yeojb9j30it0itaar.jpg', '或许你也喜欢看烟花吗', b'1', b'0', '2022-03-01 17:35:33', '2022-03-01 17:35:35');
INSERT INTO `user_following` VALUES (226, 3, '5443792134', '长河孤岛', 'https://m.weibo.cn/u/5443792134', '/images/user-profile-photo/005WpB0Wly8gwg8te1qavj30ku0ku0ue.jpg', '每一步都算数', b'1', b'0', '2022-03-03 05:47:48', '2022-03-03 05:47:49');
INSERT INTO `user_following` VALUES (227, 3, '5160231614', '拉铃米虫', 'https://m.weibo.cn/u/5160231614', '/images/user-profile-photo/005DdNYaly8h4me0of0zkj30ck0ck0tn.jpg', '最怕你一生碌碌无为，还安慰自己平凡可贵', b'1', b'0', '2022-03-03 12:15:32', '2022-03-03 12:15:34');
INSERT INTO `user_following` VALUES (228, 3, '5276487942', '周慧wow', 'https://m.weibo.cn/u/5276487942', '/images/user-profile-photo/005L5Byejw8ek385k3mudj30f00f074o.jpg', '', b'1', b'0', '2022-03-04 05:29:43', '2022-03-04 05:29:44');
INSERT INTO `user_following` VALUES (229, 3, '3947213150', 'Wasami江江', 'https://m.weibo.cn/u/3947213150', '/images/user-profile-photo/eb45b15ely8glizbpgko8j20u00u0go1.jpg', '努力💪   不约勿扰', b'1', b'0', '2022-03-04 15:04:10', '2022-03-04 15:04:14');
INSERT INTO `user_following` VALUES (230, 3, '5841871454', '沈圆圆儿', 'https://m.weibo.cn/u/5841871454', '/images/user-profile-photo/006nlTD8ly8gumdlu8jfsj60e80e8wfe02.jpg', '永远不长胖的零零后🚙', b'1', b'0', '2022-03-05 15:10:19', '2022-03-05 15:10:25');
INSERT INTO `user_following` VALUES (231, 3, '3977652313', '小小小酒窝呀', 'https://m.weibo.cn/u/3977652313', '/images/user-profile-photo/ed162859ly8gzmincib81j20u00u0n2i.jpg', 'what you believe makes who you are', b'1', b'0', '2022-03-06 15:23:40', '2022-03-06 15:24:05');
INSERT INTO `user_following` VALUES (232, 3, '3977652313', '小小小酒窝呀', 'https://m.weibo.cn/u/3977652313', '/images/user-profile-photo/ed162859ly8gzmincib81j20u00u0n2i.jpg', 'what you believe makes who you are', b'1', b'0', '2022-03-06 15:23:57', '2022-03-06 15:24:02');
INSERT INTO `user_following` VALUES (234, 3, '6122259669', '仪酱o', 'https://m.weibo.cn/u/6122259669', '/images/user-profile-photo/006GknpHly8gypzh4sy59j30u00u0q5y.jpg', '陈玮最想成为的人', b'1', b'0', '2022-03-06 15:29:21', '2022-03-06 15:29:23');
INSERT INTO `user_following` VALUES (235, 3, '1655636350', '纹绣师-阿纯姨', 'https://m.weibo.cn/u/1655636350', '/images/user-profile-photo/62af017ely8gkeccx43cgj20e80e8dgl.jpg', '专业纹眉20年～～', b'1', b'0', '2022-03-07 16:56:17', '2022-03-07 16:56:19');
INSERT INTO `user_following` VALUES (236, 3, '5604284733', '橘子Holland', 'https://m.weibo.cn/u/5604284733', '/images/user-profile-photo/0067h0tfly8grof2ar5vsj30n00n040d.jpg', '温暖磊落，时有女子', b'1', b'0', '2022-03-08 02:27:35', '2022-03-08 02:27:37');
INSERT INTO `user_following` VALUES (237, 3, '1965102275', 'Cheryl_越', 'https://m.weibo.cn/u/1965102275', '/images/user-profile-photo/752114c3ly8gtfxvgja65j20u00u0gnf.jpg', '开心点吧朋友，人间不值得', b'1', b'0', '2022-03-10 04:50:22', '2022-03-10 04:50:24');
INSERT INTO `user_following` VALUES (238, 3, '1811644193', '丫泳柒', 'https://m.weibo.cn/u/1811644193', '/images/user-profile-photo/6bfb7f21ly8gzx7c5jwzmj20u00u0wig.jpg', '✨心之所向必見光芒✨', b'1', b'0', '2022-03-10 04:53:43', '2022-03-10 04:53:44');
INSERT INTO `user_following` VALUES (239, 3, '2868859610', '涫儿音心', 'https://m.weibo.cn/u/2868859610', '/images/user-profile-photo/aaff52daly8gtpy4aujrsj20e80e8js3.jpg', '我升哥的小千金', b'1', b'0', '2022-03-10 04:58:30', '2022-03-10 04:58:32');
INSERT INTO `user_following` VALUES (240, 3, '3268076833', 'yxmmi', 'https://m.weibo.cn/u/3268076833', '/images/user-profile-photo/c2cae521ly8gfl1biq1cmj20e80e8q3p.jpg', '及时行乐课代表', b'1', b'0', '2022-03-10 05:05:18', '2022-03-10 05:05:19');
INSERT INTO `user_following` VALUES (241, 3, '5715306685', '想吃蛋卷儿', 'https://m.weibo.cn/u/5715306685', '/images/user-profile-photo/006eMQlTly8g5egv84bfdj30u00u0wgz.jpg', '我很想你  第一句话是假的  第二句话也是假的', b'1', b'0', '2022-03-10 17:50:08', '2022-03-10 17:50:09');
INSERT INTO `user_following` VALUES (242, 3, '2474891564', 'wanyi_cchen', 'https://m.weibo.cn/u/2474891564', '/images/user-profile-photo/9383d92cly8gmugs4dkjsj20u00u077q.jpg', '最大的满足 是你给的在乎', b'1', b'0', '2022-03-11 02:25:28', '2022-03-11 02:25:29');
INSERT INTO `user_following` VALUES (243, 3, '1810547021', '你用的沐浴露我也买了', 'https://m.weibo.cn/u/1810547021', '/images/user-profile-photo/6beac14dly8gf4ft4os5lj20n10n1dh7.jpg', '恋爱和战斗都要勇往直前', b'1', b'0', '2022-03-11 13:44:27', '2022-03-11 13:44:29');
INSERT INTO `user_following` VALUES (244, 3, '5901322816', '你的小洋妞i', 'https://m.weibo.cn/u/5901322816', '/images/user-profile-photo/006rnlE4ly8fqzhwsc5u5j30e80e8aag.jpg', '👩‍🎓', b'1', b'0', '2022-03-12 03:05:49', '2022-03-12 03:05:50');
INSERT INTO `user_following` VALUES (245, 3, '5808320254', '帆哥养的小猪猪', 'https://weibo.com/u/5808320254', '/images/user-profile-photo/006l57qKly8g82sqzokuej30e80e8jrg.jpg', '我要做个大写的人。', b'1', b'0', '2022-03-14 10:32:51', '2022-03-14 10:32:52');
INSERT INTO `user_following` VALUES (246, 3, '6510783894', '贝贝bei_', 'https://m.weibo.cn/u/6510783894', '/images/user-profile-photo/0076CAjsly8gzk7r45e0vj30u00u00vs.jpg', '与你相遇是最置顶的欢愉', b'1', b'0', '2022-03-17 08:29:28', '2022-03-17 08:29:30');
INSERT INTO `user_following` VALUES (247, 3, '6055539554', 'jiahuiing', 'https://m.weibo.cn/u/6055539554', '/images/user-profile-photo/006BOqsOly8gy1l9e2bpxj30e80e8757.jpg', '', b'1', b'0', '2022-03-18 02:22:34', '2022-03-18 02:22:35');
INSERT INTO `user_following` VALUES (248, 3, '6134632946', '一个快乐的小青', 'https://m.weibo.cn/u/6134632946', '/images/user-profile-photo/006HaigGly8ggn5fhqc0hj30u00u0ae8.jpg', '活的洒脱', b'1', b'0', '2022-03-18 03:12:32', '2022-03-18 03:12:33');
INSERT INTO `user_following` VALUES (249, 3, '1627460110', '雕塑一周', 'https://m.weibo.cn/u/1627460110', '/images/user-profile-photo/6101120ely8fxhatoavqxj20e80e8t9b.jpg', '做雕塑的', b'1', b'0', '2022-03-20 02:30:51', '2022-03-20 02:30:53');
INSERT INTO `user_following` VALUES (250, 3, '5977890671', '浅浅9802', 'https://m.weibo.cn/u/5977890671', '/images/user-profile-photo/006wyCrtly8gxb2ea5d5nj30ro0rogp4.jpg', '钱才是我的安全感', b'1', b'0', '2022-03-23 06:14:06', '2022-03-23 06:14:07');
INSERT INTO `user_following` VALUES (251, 3, '5334984157', '采之君', 'https://m.weibo.cn/u/5334984157', '/images/user-profile-photo/005P335zly8gi8vk5sxwfj30ro0rogqn.jpg', '一只迷路的土拨鼠。', b'1', b'0', '2022-03-24 04:46:40', '2022-03-24 04:46:41');
INSERT INTO `user_following` VALUES (252, 3, '5315540979', 'Y安如夏沫', 'https://m.weibo.cn/u/5315540979', '/images/user-profile-photo/005NJt1Vly8gvyyevjwhwj30e80e8jsa.jpg', '', b'1', b'0', '2022-03-24 04:47:10', '2022-03-24 04:47:11');
INSERT INTO `user_following` VALUES (257, 3, '7301853896', '锅包又_L', 'https://m.weibo.cn/u/7301853896', '/images/user-profile-photo/007Y9PL2ly8gzvkcdhrszj30u00u0acw.jpg', '🎂', b'1', b'0', '2022-03-24 15:11:36', '2022-03-24 15:11:37');
INSERT INTO `user_following` VALUES (258, 3, '5734551204', '小赵很瘦', 'https://m.weibo.cn/u/5734551204', '/images/user-profile-photo/006g5AJmly8g25pj82poyj30ro0rodhk.jpg', '立刻有', b'1', b'0', '2022-05-02 13:49:31', '2022-05-02 13:49:34');
INSERT INTO `user_following` VALUES (259, 3, '5632372827', '不颓废普森小姐', 'https://m.weibo.cn/u/5632372827', '/images/user-profile-photo/0069aRt1ly1ghv16vy3vbj30p00p04f1.jpg', '情感博主 公主号：不颓废青年 商务合作请加v：insbd999', b'1', b'0', '2022-05-29 10:47:41', '2022-05-29 10:47:44');
INSERT INTO `user_following` VALUES (262, 3, '6230375406', '汤圆Sss-', 'https://m.weibo.cn/u/6230375406', '/images/user-profile-photo/006NE1fUly8hkipbmxxbgj30u00u0wl8.jpg', '💐', b'1', b'0', '2022-11-09 14:35:38', '2024-05-10 16:49:50');
INSERT INTO `user_following` VALUES (263, 3, '5554979075', 'kooooo1', 'https://m.weibo.cn/u/5554979075', '/images/user-profile-photo/0063W7OHly8h85k6y7vkhj30u00u0gnq.jpg', '为自己而活～☀️', b'1', b'0', '2022-12-01 06:27:22', '2022-12-01 06:27:24');
INSERT INTO `user_following` VALUES (264, 3, '5975038645', '是糍粑吗', 'https://m.weibo.cn/u/5975038645', '/images/user-profile-photo/006wmEv3ly8hb8qu79c4yj30u00u0dku.jpg', '美食博主 是嘴巴子非常碎，又会跳点舞的糍粑', b'1', b'0', '2022-12-02 05:28:05', '2022-12-02 05:28:07');
INSERT INTO `user_following` VALUES (265, 3, '5957642944', '_yuweitt', 'https://m.weibo.cn/u/5957642944', '/images/user-profile-photo/006vbF5ely8h88ki64qguj30e80e83ys.jpg', '哪朵玫瑰没有荆棘', b'1', b'0', '2022-12-06 06:22:42', '2022-12-06 06:22:45');
INSERT INTO `user_following` VALUES (266, 3, '6343603440', '柿柿如意_ywj', 'https://m.weibo.cn/u/6343603440', '/images/user-profile-photo/006Vj72wly8h3psep7yfkj30u00u0mzx.jpg', '记录与分享每日心情 生活从来都是自己的🌷', b'1', b'0', '2022-12-06 06:23:35', '2022-12-06 06:23:37');
INSERT INTO `user_following` VALUES (267, 3, '2149347785', 'Lulu_的微博', 'https://m.weibo.cn/u/2149347785', '/images/user-profile-photo/801c71c9ly8hhgboj29l1j20u00u0gn3.jpg', '', b'1', b'0', '2024-05-10 15:48:40', '2024-05-10 15:49:36');
INSERT INTO `user_following` VALUES (268, 3, '6536673901', 'xie钞票', 'https://m.weibo.cn/u/6536673901', '/images/user-profile-photo/0078ndudly8hpaix6qw4zj30u00u0gom.jpg', '每个人的花期不同 不必焦虑有人比你提前拥有', b'1', b'0', '2024-05-12 15:53:08', '2024-05-12 15:53:10');
INSERT INTO `user_following` VALUES (269, 3, '3682271460', '-ChanhQ', 'https://m.weibo.cn/u/3682271460', '/images/user-profile-photo/0041cr1aly8gvg78387ucj60e80e80t702.jpg', 'share my life:-)', b'1', b'0', '2024-05-12 16:36:33', '2024-05-12 16:36:33');
INSERT INTO `user_following` VALUES (270, 3, '5647397571', '邂迁', 'https://m.weibo.cn/u/5647397571', '/images/user-profile-photo/006abU5Bly8hjvmuqxpt5j30e80e8mxo.jpg', '关注99     粉丝1988', b'1', b'0', '2024-05-14 15:28:04', '2024-05-14 15:28:05');
INSERT INTO `user_following` VALUES (271, 3, '5988392137', '_LNHeng', 'https://m.weibo.cn/u/5988392137', '/images/user-profile-photo/006xgGlXly8hf56yqsmnoj30e80e8q3j.jpg', '你好 很高兴认识你！', b'1', b'0', '2024-05-15 17:04:31', '2024-05-17 16:55:14');
INSERT INTO `user_following` VALUES (281, 2, '141536573', '栗子怎么肥事', 'https://space.bilibili.com/141536573/video', '/images/user-profile-photo/7dcd172a756d90be297a149073ba069f12c5c553.jpg', '欢迎收看栗子频道 Q?：957199836\n文化水平不怎么高 脑子有点笨 人很呆\n反射弧有点长 主打一个快乐追剧\n可以给我建议 但是别网暴', b'1', b'0', '2024-05-16 02:52:16', '2024-05-21 02:53:55');
INSERT INTO `user_following` VALUES (282, 2, '284915317', '清风逐鹿77', 'https://space.bilibili.com/284915317/video', '/images/user-profile-photo/c46fe783ac7a5291dac4773744b3d35d7cebd77f.jpg', '下架视频资源置顶动态自取～业余up，看点自己想看的，不喜勿扰！', b'1', b'0', '2024-05-16 02:53:58', '2024-05-16 02:53:59');
INSERT INTO `user_following` VALUES (283, 2, '1439261506', '星云叭叭叭', 'https://space.bilibili.com/1439261506/video', '/images/user-profile-photo/af76f643bd87f905093680452f4fbdcc129d0baa.jpg', '下架网盘在置顶动态领取噢，粉丝群：797819611', b'1', b'0', '2024-05-16 02:54:29', '2024-05-16 02:54:29');
INSERT INTO `user_following` VALUES (284, 3, '2352314484', '美人魚瑩瑩是夢女孩Cindy', 'https://m.weibo.cn/u/2352314484', '/images/user-profile-photo/8c357874ly8hpzrq73qv1j20e80e8jrh.jpg', '微博原创视频博主 芳华正好，花田海岸把我四面环绕。', b'1', b'0', '2024-05-16 19:10:26', '2024-05-16 19:11:11');
INSERT INTO `user_following` VALUES (285, 3, '5386155988', '哩哩泡泡_', 'https://m.weibo.cn/u/5386155988', '/images/user-profile-photo/005SvLdGly8hcnim0c10uj30u00u0ad6.jpg', 'sugar on your lips.', b'1', b'0', '2024-05-16 19:14:50', '2024-05-16 19:14:51');
INSERT INTO `user_following` VALUES (286, 3, '5354577882', '榆木老柴rich_', 'https://m.weibo.cn/u/5354577882', '/images/user-profile-photo/005Qngjoly8hoe1d4ty4oj30u00u0aco.jpg', '高上清灵美，悲歌朗太空', b'1', b'0', '2024-05-16 19:20:05', '2024-05-16 19:20:06');
INSERT INTO `user_following` VALUES (287, 3, '6190534089', '今天又要做辣妹哦', 'https://m.weibo.cn/u/6190534089', '/images/user-profile-photo/006KWQI1ly8hf8cz8ukhjj30u00u00v9.jpg', '超级热爱运动、热爱美食🧏🏻‍♀️  财神爷保佑：2024财源滚滚💰', b'1', b'0', '2024-05-16 19:21:38', '2024-05-16 23:45:19');
INSERT INTO `user_following` VALUES (288, 3, '5231761561', '今天不喝奶茶r', 'https://m.weibo.cn/u/5231761561', '/images/user-profile-photo/005I3WaZly8glit9kn3iqj30e80e8wfk.jpg', '介绍不了', b'1', b'0', '2024-05-18 21:27:59', '2024-05-18 21:28:23');
INSERT INTO `user_following` VALUES (289, 3, '5534380369', '赵禾l', 'https://m.weibo.cn/u/5534380369', '/images/user-profile-photo/0062xH9vly8h9u2zwgvfij30u00u0aei.jpg', '我要跑得多快才能追上你', b'1', b'0', '2024-05-19 01:07:02', '2024-05-19 01:07:03');
INSERT INTO `user_following` VALUES (290, 3, '2986560581', 'WANGYHer-', 'https://m.weibo.cn/u/2986560581', '/images/user-profile-photo/b2034c45ly8hphxlgwmklj20iy0iy404.jpg', 'Today', b'1', b'0', '2024-05-19 01:10:45', '2024-05-19 01:10:45');
INSERT INTO `user_following` VALUES (291, 3, '7852261812', 'Puttarn', 'https://m.weibo.cn/u/7852261812', '/images/user-profile-photo/008zpi04ly8hp8apaxl77j30e80e8mxb.jpg', '纯粹纯静纯净至善阳光温柔平凡真实自由突破', b'1', b'0', '2024-05-19 01:15:33', '2024-05-19 01:15:33');
INSERT INTO `user_following` VALUES (292, 3, '7318852456', '我是大美女K', 'https://m.weibo.cn/u/7318852456', '/images/user-profile-photo/007Zj9Rmly8hpwtb5isgwj30e80e8t9k.jpg', '接受的约会方式 老套的喝咖啡看电影吃饭', b'1', b'0', '2024-05-21 19:03:26', '2024-05-21 19:03:27');
INSERT INTO `user_following` VALUES (293, 3, '6642580176', '巧巧_purple', 'https://m.weibo.cn/u/6642580176', '/images/user-profile-photo/007fxAxWly8h84fjcnh0kj30tr0tr76q.jpg', '变优秀 才会有人  为你  着迷✨️', b'1', b'0', '2024-05-21 19:14:57', '2024-05-21 19:14:57');
INSERT INTO `user_following` VALUES (294, 3, '6093530404', '魔仙猪巴啦啦', 'https://m.weibo.cn/u/6093530404', '/images/user-profile-photo/006EnPCsly8hec2534934j30e80e8aab.jpg', '我活着就是来发疯的 这是我的秘密基地', b'1', b'0', '2024-05-21 19:18:09', '2024-05-21 19:18:10');
INSERT INTO `user_following` VALUES (296, 3, '7209571859', '原来是我无人能敌', 'https://m.weibo.cn/u/7209571859', '/images/user-profile-photo/007RUCZ5ly8gzyqs145w4j30e80e874n.jpg', '我的梦 坎坎坷坷 平平淡淡', b'1', b'0', '2024-05-21 19:20:58', '2024-05-21 19:20:59');
INSERT INTO `user_following` VALUES (297, 3, '7341208196', 'Hey胡椒呀', 'https://m.weibo.cn/u/7341208196', '/images/user-profile-photo/0080OXBOly8hqaxan10rej30u00u0wig.jpg', 'From.❤️', b'1', b'0', '2024-05-23 21:42:56', '2024-05-23 21:42:57');
INSERT INTO `user_following` VALUES (298, 3, '2874725630', '永泽和木月', 'https://m.weibo.cn/u/2874725630', '/images/user-profile-photo/ab58d4fely8gwbprwmt58j20n00n0jtm.jpg', '清风徐来 水波不兴', b'1', b'0', '2024-05-23 21:53:29', '2024-05-23 21:53:30');
INSERT INTO `user_following` VALUES (299, 3, '7304266372', 'Nirvanaleon90334', 'https://m.weibo.cn/u/7304266372', '/images/user-profile-photo/007YjXlWly8hk9pl71fvuj30ld0ld3zr.jpg', 'Be a sober man', b'1', b'0', '2024-05-24 14:29:22', '2024-05-24 14:29:23');
INSERT INTO `user_following` VALUES (300, 3, '6479965907', 'hanbooogr', 'https://m.weibo.cn/u/6479965907', '/images/user-profile-photo/0074xh99ly8hp0zgvrzyvj30u00u00ur.jpg', '生活实录罢了', b'1', b'0', '2024-05-25 09:51:23', '2024-05-25 09:51:23');
INSERT INTO `user_following` VALUES (301, 3, '6276119328', '椰子ye加糖', 'https://m.weibo.cn/u/6276119328', '/images/user-profile-photo/006QJXl6ly8hgpuhpix5xj30u00u0gpq.jpg', '做自己呀', b'1', b'0', '2024-05-25 19:45:08', '2024-05-25 19:45:09');
INSERT INTO `user_following` VALUES (302, 3, '5994507312', '嘛哩嘛哩轰轰-', 'https://m.weibo.cn/u/5994507312', '/images/user-profile-photo/006xGlbOly8hp95xmjk3dj30j60j6aan.jpg', '', b'1', b'0', '2024-05-25 19:52:08', '2024-05-25 19:52:09');
INSERT INTO `user_following` VALUES (303, 3, '3272244227', '肉松芋泥麻薯虎皮卷', 'https://m.weibo.cn/u/3272244227', '/images/user-profile-photo/c30a7c03ly8hnivk70p17j20e80e80tc.jpg', '屁话王| 一百万个墙头 镇魂  本人好奇心重，什么都喜欢看看，微博所有访问行为皆为手残', b'1', b'0', '2024-06-02 11:31:17', '2024-06-02 11:31:18');
INSERT INTO `user_following` VALUES (304, 3, '6334606873', 'MAjeuRei', 'https://m.weibo.cn/u/6334606873', '/images/user-profile-photo/006UHmCBly8g0ara3ix2nj30ig0igt9w.jpg', '', b'1', b'0', '2024-06-07 09:58:32', '2024-06-07 09:58:33');
INSERT INTO `user_following` VALUES (305, 3, '3337804812', '惠妈想要去流浪', 'https://m.weibo.cn/u/3337804812', '/images/user-profile-photo/c6f2dc0cly8g472zwkyg5j20u00u00vs.jpg', '✨ 永远善良 纯真 勇敢 洒脱 🎈', b'1', b'0', '2024-06-07 10:03:10', '2024-06-07 10:53:16');
INSERT INTO `user_following` VALUES (306, 3, '2287422701', '薄荷加茉莉', 'https://m.weibo.cn/u/2287422701', '/images/user-profile-photo/88574cedly8hq7uc2co36j20u00u0q5p.jpg', '一只爱运动的科研狗', b'1', b'0', '2024-06-07 10:15:59', '2024-06-07 10:53:30');
INSERT INTO `user_following` VALUES (307, 3, '6207742234', 'Xjusjkd', 'https://m.weibo.cn/u/6207742234', '/images/user-profile-photo/006M73kKly8hixkn42clbj30u00u0jt7.jpg', '', b'1', b'0', '2024-06-07 10:20:06', '2024-06-07 10:20:06');

-- ----------------------------
-- Table structure for user_following_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_following_relation`;
CREATE TABLE `user_following_relation`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id',
  `following_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联关注用户id',
  `platform_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联平台id，冗余字段，用于提升查询效率',
  `type_id` bigint(20) UNSIGNED NOT NULL DEFAULT 1 COMMENT '关联关注类型表id，0-默认分类',
  `sort_no` tinyint(3) UNSIGNED NOT NULL DEFAULT 5 COMMENT '优先级由低到高：1-10，默认5。8-思想、学习；7-美食、营养；6、健身、锻炼；5-兴趣、生活；4~其他',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_where_order`(`user_id`, `platform_id`, `type_id`, `following_id`, `sort_no`) USING BTREE COMMENT '索引用于筛选和排序'
) ENGINE = InnoDB AUTO_INCREMENT = 309 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与关注用户关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_following_relation
-- ----------------------------
INSERT INTO `user_following_relation` VALUES (1, 1, 1, 2, 2, 9, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (2, 1, 2, 2, 2, 8, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (3, 1, 3, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:58:11');
INSERT INTO `user_following_relation` VALUES (4, 1, 4, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:58:15');
INSERT INTO `user_following_relation` VALUES (5, 1, 5, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:58:42');
INSERT INTO `user_following_relation` VALUES (6, 1, 6, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:56:09');
INSERT INTO `user_following_relation` VALUES (7, 1, 7, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:56:13');
INSERT INTO `user_following_relation` VALUES (8, 1, 8, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:56:16');
INSERT INTO `user_following_relation` VALUES (9, 1, 9, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:56:39');
INSERT INTO `user_following_relation` VALUES (10, 1, 10, 2, 2, 6, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (11, 1, 11, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (12, 1, 12, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (13, 1, 13, 2, 2, 6, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (14, 1, 14, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (15, 1, 15, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:56:48');
INSERT INTO `user_following_relation` VALUES (16, 1, 16, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:56:51');
INSERT INTO `user_following_relation` VALUES (17, 1, 17, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (18, 1, 18, 2, 2, 6, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (19, 1, 19, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (20, 1, 20, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 11:00:05');
INSERT INTO `user_following_relation` VALUES (21, 1, 21, 2, 2, 6, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (22, 1, 22, 2, 2, 6, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (23, 1, 23, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (24, 1, 24, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (25, 1, 25, 3, 6, 9, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (26, 1, 26, 3, 6, 8, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (27, 1, 27, 3, 6, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (28, 1, 28, 3, 6, 7, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (29, 1, 29, 3, 6, 7, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (30, 1, 30, 3, 6, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (31, 1, 31, 3, 6, 8, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (32, 1, 32, 3, 6, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (33, 1, 33, 3, 6, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (34, 1, 34, 3, 6, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (35, 1, 35, 3, 6, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (36, 1, 36, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (37, 1, 37, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (38, 1, 38, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (39, 1, 39, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (40, 1, 40, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (41, 1, 41, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (42, 1, 42, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (43, 1, 43, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (44, 1, 44, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (45, 1, 45, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (46, 1, 46, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (47, 1, 47, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (48, 1, 48, 3, 7, 1, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (49, 1, 49, 3, 7, 1, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (50, 1, 50, 3, 9, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 11:40:32');
INSERT INTO `user_following_relation` VALUES (51, 1, 51, 3, 9, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 11:41:04');
INSERT INTO `user_following_relation` VALUES (52, 1, 52, 3, 9, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (53, 1, 53, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:57:54');
INSERT INTO `user_following_relation` VALUES (54, 1, 54, 2, 2, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (55, 1, 55, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:57:05');
INSERT INTO `user_following_relation` VALUES (56, 1, 56, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (57, 1, 57, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (58, 1, 58, 3, 7, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:54:50');
INSERT INTO `user_following_relation` VALUES (59, 1, 59, 2, 2, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:57:41');
INSERT INTO `user_following_relation` VALUES (60, 1, 60, 3, 9, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 11:41:26');
INSERT INTO `user_following_relation` VALUES (61, 1, 61, 3, 9, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 11:41:29');
INSERT INTO `user_following_relation` VALUES (62, 1, 62, 3, 7, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (63, 1, 63, 3, 7, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:55:56');
INSERT INTO `user_following_relation` VALUES (64, 1, 64, 3, 7, 5, b'1', '2021-04-23 06:15:56', '2024-06-07 10:55:59');
INSERT INTO `user_following_relation` VALUES (65, 1, 65, 3, 9, 5, b'0', '2021-04-23 06:15:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (68, 1, 68, 2, 2, 5, b'1', '2021-06-30 13:01:34', '2024-06-07 10:57:38');
INSERT INTO `user_following_relation` VALUES (69, 1, 69, 3, 9, 5, b'0', '2021-07-01 06:19:42', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (70, 1, 70, 3, 9, 5, b'1', '2021-10-07 09:11:44', '2024-06-07 11:42:47');
INSERT INTO `user_following_relation` VALUES (71, 1, 71, 3, 9, 9, b'1', '2021-11-07 03:34:25', '2024-06-07 11:40:08');
INSERT INTO `user_following_relation` VALUES (72, 1, 72, 3, 9, 5, b'1', '2022-01-09 17:39:21', '2024-06-07 11:43:08');
INSERT INTO `user_following_relation` VALUES (73, 1, 73, 3, 9, 5, b'1', '2022-01-12 17:15:05', '2024-06-07 11:43:11');
INSERT INTO `user_following_relation` VALUES (74, 1, 74, 3, 9, 5, b'1', '2022-01-13 03:33:46', '2024-06-07 11:43:14');
INSERT INTO `user_following_relation` VALUES (75, 1, 75, 3, 9, 5, b'1', '2022-01-13 12:43:45', '2024-06-07 11:43:58');
INSERT INTO `user_following_relation` VALUES (76, 1, 76, 3, 9, 5, b'1', '2022-01-13 19:50:43', '2024-06-07 11:44:01');
INSERT INTO `user_following_relation` VALUES (77, 1, 77, 3, 9, 5, b'1', '2022-01-13 19:50:48', '2024-06-07 11:43:38');
INSERT INTO `user_following_relation` VALUES (78, 1, 78, 3, 9, 5, b'1', '2022-01-14 14:24:35', '2024-06-07 11:44:04');
INSERT INTO `user_following_relation` VALUES (79, 1, 79, 3, 9, 5, b'1', '2022-01-15 03:43:05', '2024-06-07 11:45:14');
INSERT INTO `user_following_relation` VALUES (80, 1, 80, 3, 9, 5, b'1', '2022-01-15 03:47:31', '2024-06-07 11:45:17');
INSERT INTO `user_following_relation` VALUES (83, 1, 83, 3, 9, 5, b'1', '2022-01-15 04:14:32', '2024-06-07 11:45:22');
INSERT INTO `user_following_relation` VALUES (84, 1, 84, 3, 9, 5, b'1', '2022-01-15 04:18:56', '2024-06-07 11:45:57');
INSERT INTO `user_following_relation` VALUES (85, 1, 85, 3, 9, 5, b'1', '2022-01-17 17:35:07', '2024-06-07 11:46:01');
INSERT INTO `user_following_relation` VALUES (86, 1, 86, 3, 9, 5, b'1', '2022-01-17 17:44:28', '2024-06-07 11:46:05');
INSERT INTO `user_following_relation` VALUES (87, 1, 87, 3, 9, 5, b'1', '2022-01-18 04:25:32', '2024-06-07 11:46:09');
INSERT INTO `user_following_relation` VALUES (88, 1, 88, 3, 9, 5, b'1', '2022-01-18 15:46:18', '2024-06-07 11:46:22');
INSERT INTO `user_following_relation` VALUES (89, 1, 89, 3, 9, 5, b'1', '2022-01-21 17:02:46', '2024-06-07 11:46:25');
INSERT INTO `user_following_relation` VALUES (90, 1, 90, 3, 9, 5, b'1', '2022-01-21 17:05:47', '2024-06-07 11:46:28');
INSERT INTO `user_following_relation` VALUES (91, 1, 91, 3, 9, 5, b'1', '2022-01-21 17:17:38', '2024-06-07 11:46:30');
INSERT INTO `user_following_relation` VALUES (92, 1, 92, 3, 9, 5, b'1', '2022-01-21 17:21:50', '2024-06-07 11:47:12');
INSERT INTO `user_following_relation` VALUES (93, 1, 93, 3, 9, 5, b'1', '2022-01-22 03:24:48', '2024-06-07 11:47:15');
INSERT INTO `user_following_relation` VALUES (94, 1, 94, 3, 9, 5, b'1', '2022-01-22 07:53:13', '2024-06-07 11:47:18');
INSERT INTO `user_following_relation` VALUES (95, 1, 95, 3, 9, 5, b'1', '2022-01-23 04:16:06', '2024-06-07 11:47:21');
INSERT INTO `user_following_relation` VALUES (96, 1, 96, 3, 9, 5, b'1', '2022-01-24 02:15:13', '2024-06-07 11:47:33');
INSERT INTO `user_following_relation` VALUES (97, 1, 97, 3, 9, 5, b'1', '2022-01-24 02:38:41', '2024-06-07 11:47:57');
INSERT INTO `user_following_relation` VALUES (98, 1, 98, 3, 9, 5, b'1', '2022-01-24 06:06:02', '2024-06-07 11:47:45');
INSERT INTO `user_following_relation` VALUES (99, 1, 99, 3, 9, 5, b'1', '2022-01-24 06:24:37', '2024-06-07 11:47:48');
INSERT INTO `user_following_relation` VALUES (100, 1, 100, 3, 9, 5, b'1', '2022-01-24 06:35:35', '2024-06-07 11:48:15');
INSERT INTO `user_following_relation` VALUES (101, 1, 101, 3, 9, 5, b'1', '2022-01-24 09:15:12', '2024-06-07 11:48:35');
INSERT INTO `user_following_relation` VALUES (102, 1, 102, 3, 9, 5, b'1', '2022-01-24 09:38:44', '2024-06-07 11:48:38');
INSERT INTO `user_following_relation` VALUES (103, 1, 103, 3, 9, 5, b'1', '2022-01-25 18:45:15', '2024-06-07 11:48:41');
INSERT INTO `user_following_relation` VALUES (104, 1, 104, 3, 9, 5, b'1', '2022-01-26 03:03:05', '2024-06-07 11:48:54');
INSERT INTO `user_following_relation` VALUES (105, 1, 105, 3, 9, 5, b'1', '2022-01-26 17:42:53', '2024-06-07 11:48:58');
INSERT INTO `user_following_relation` VALUES (106, 1, 106, 3, 9, 5, b'1', '2022-01-26 18:06:06', '2024-06-07 11:49:00');
INSERT INTO `user_following_relation` VALUES (107, 1, 107, 3, 9, 5, b'1', '2022-01-27 15:20:23', '2024-06-07 11:49:18');
INSERT INTO `user_following_relation` VALUES (108, 1, 108, 3, 9, 5, b'1', '2022-01-27 17:33:41', '2024-06-07 11:49:39');
INSERT INTO `user_following_relation` VALUES (109, 1, 109, 3, 9, 5, b'1', '2022-01-27 17:37:34', '2024-06-07 11:49:42');
INSERT INTO `user_following_relation` VALUES (110, 1, 110, 3, 9, 5, b'1', '2022-01-28 05:38:40', '2024-06-07 11:49:59');
INSERT INTO `user_following_relation` VALUES (111, 1, 111, 3, 9, 5, b'1', '2022-01-28 05:53:56', '2024-06-07 11:50:02');
INSERT INTO `user_following_relation` VALUES (112, 1, 112, 3, 9, 5, b'1', '2022-01-28 15:26:51', '2024-06-07 11:50:46');
INSERT INTO `user_following_relation` VALUES (113, 1, 113, 3, 9, 5, b'1', '2022-01-29 03:29:35', '2024-06-07 11:50:22');
INSERT INTO `user_following_relation` VALUES (114, 1, 114, 3, 9, 5, b'1', '2022-01-30 04:11:19', '2024-06-07 11:50:48');
INSERT INTO `user_following_relation` VALUES (115, 1, 115, 3, 9, 5, b'1', '2022-01-31 03:13:44', '2024-06-07 11:50:51');
INSERT INTO `user_following_relation` VALUES (116, 1, 116, 3, 9, 5, b'1', '2022-01-31 03:19:52', '2024-06-07 11:51:07');
INSERT INTO `user_following_relation` VALUES (117, 1, 117, 3, 9, 5, b'1', '2022-01-31 03:30:59', '2024-06-07 11:51:46');
INSERT INTO `user_following_relation` VALUES (118, 1, 118, 3, 9, 5, b'1', '2022-01-31 03:36:11', '2024-06-07 11:51:49');
INSERT INTO `user_following_relation` VALUES (119, 1, 119, 3, 9, 5, b'1', '2022-02-01 04:03:09', '2024-06-07 11:51:19');
INSERT INTO `user_following_relation` VALUES (120, 1, 120, 3, 9, 5, b'1', '2022-02-01 04:11:23', '2024-06-07 11:51:59');
INSERT INTO `user_following_relation` VALUES (121, 1, 121, 3, 9, 5, b'0', '2022-02-01 04:16:08', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (122, 1, 122, 3, 9, 5, b'1', '2022-02-01 04:31:30', '2024-06-07 11:52:04');
INSERT INTO `user_following_relation` VALUES (123, 1, 123, 3, 9, 5, b'1', '2022-02-01 04:32:22', '2024-06-07 11:52:07');
INSERT INTO `user_following_relation` VALUES (124, 1, 124, 3, 9, 5, b'1', '2022-02-01 18:14:38', '2024-06-07 11:53:13');
INSERT INTO `user_following_relation` VALUES (125, 1, 125, 3, 9, 5, b'1', '2022-02-01 18:41:10', '2024-06-07 11:53:16');
INSERT INTO `user_following_relation` VALUES (126, 1, 126, 3, 9, 5, b'1', '2022-02-02 04:21:49', '2024-06-07 11:53:19');
INSERT INTO `user_following_relation` VALUES (127, 1, 127, 3, 9, 6, b'1', '2022-02-02 19:52:34', '2024-06-07 11:40:15');
INSERT INTO `user_following_relation` VALUES (128, 1, 128, 3, 9, 5, b'1', '2022-02-03 04:04:16', '2024-06-07 11:53:45');
INSERT INTO `user_following_relation` VALUES (129, 1, 129, 3, 9, 5, b'1', '2022-02-04 03:38:46', '2024-06-07 11:53:48');
INSERT INTO `user_following_relation` VALUES (130, 1, 130, 3, 9, 5, b'1', '2022-02-04 03:42:52', '2024-06-07 11:53:55');
INSERT INTO `user_following_relation` VALUES (131, 1, 131, 3, 9, 5, b'1', '2022-02-07 14:55:01', '2024-06-07 11:54:29');
INSERT INTO `user_following_relation` VALUES (132, 1, 132, 3, 9, 5, b'1', '2022-02-07 15:02:17', '2024-06-07 11:54:32');
INSERT INTO `user_following_relation` VALUES (133, 1, 133, 3, 9, 5, b'1', '2022-02-07 15:05:53', '2024-06-07 11:54:35');
INSERT INTO `user_following_relation` VALUES (134, 1, 134, 3, 9, 5, b'1', '2022-02-08 03:26:50', '2024-06-07 11:54:45');
INSERT INTO `user_following_relation` VALUES (135, 1, 135, 3, 9, 5, b'1', '2022-02-08 03:29:38', '2024-06-07 11:54:50');
INSERT INTO `user_following_relation` VALUES (136, 1, 136, 3, 9, 5, b'1', '2022-02-08 03:35:39', '2024-06-07 11:54:53');
INSERT INTO `user_following_relation` VALUES (137, 1, 137, 3, 9, 5, b'1', '2022-02-08 03:45:10', '2024-06-07 11:55:56');
INSERT INTO `user_following_relation` VALUES (138, 1, 138, 3, 9, 5, b'1', '2022-02-08 03:48:25', '2024-06-07 11:55:09');
INSERT INTO `user_following_relation` VALUES (139, 1, 139, 3, 9, 5, b'1', '2022-02-09 15:03:13', '2024-06-07 11:55:12');
INSERT INTO `user_following_relation` VALUES (140, 1, 140, 3, 9, 5, b'1', '2022-02-09 17:43:54', '2024-06-07 11:56:04');
INSERT INTO `user_following_relation` VALUES (141, 1, 141, 3, 9, 5, b'1', '2022-02-10 12:19:25', '2024-06-07 11:56:08');
INSERT INTO `user_following_relation` VALUES (142, 1, 142, 3, 9, 5, b'1', '2022-02-10 15:20:06', '2024-06-07 11:56:21');
INSERT INTO `user_following_relation` VALUES (143, 1, 143, 3, 9, 5, b'1', '2022-02-10 15:25:56', '2024-06-07 11:57:21');
INSERT INTO `user_following_relation` VALUES (144, 1, 144, 3, 9, 5, b'1', '2022-02-10 15:31:28', '2024-06-07 11:57:23');
INSERT INTO `user_following_relation` VALUES (145, 1, 145, 3, 9, 5, b'1', '2022-02-10 15:48:51', '2024-06-07 11:56:37');
INSERT INTO `user_following_relation` VALUES (146, 1, 146, 3, 9, 5, b'1', '2022-02-11 02:33:43', '2024-06-07 11:57:33');
INSERT INTO `user_following_relation` VALUES (147, 1, 147, 3, 9, 5, b'1', '2022-02-11 02:40:26', '2024-06-07 11:57:38');
INSERT INTO `user_following_relation` VALUES (148, 1, 148, 3, 9, 5, b'1', '2022-02-11 02:51:47', '2024-06-07 11:57:41');
INSERT INTO `user_following_relation` VALUES (149, 1, 149, 3, 9, 5, b'1', '2022-02-13 15:09:32', '2024-06-07 11:57:53');
INSERT INTO `user_following_relation` VALUES (150, 1, 150, 3, 9, 5, b'1', '2022-02-17 03:06:02', '2024-06-07 11:57:56');
INSERT INTO `user_following_relation` VALUES (151, 1, 151, 3, 9, 5, b'1', '2022-02-18 03:32:59', '2024-06-07 11:57:59');
INSERT INTO `user_following_relation` VALUES (152, 1, 152, 3, 9, 5, b'1', '2022-02-18 03:39:12', '2024-06-07 11:58:09');
INSERT INTO `user_following_relation` VALUES (153, 1, 153, 3, 9, 5, b'1', '2022-02-18 03:42:21', '2024-06-07 11:58:12');
INSERT INTO `user_following_relation` VALUES (154, 1, 154, 3, 9, 5, b'1', '2022-02-19 13:23:32', '2024-06-07 11:58:23');
INSERT INTO `user_following_relation` VALUES (156, 1, 156, 3, 9, 9, b'0', '2022-02-20 03:13:30', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (157, 1, 157, 3, 9, 5, b'1', '2022-02-20 03:41:04', '2024-06-07 12:11:43');
INSERT INTO `user_following_relation` VALUES (158, 1, 158, 3, 9, 5, b'1', '2022-02-20 03:45:46', '2024-06-07 12:11:46');
INSERT INTO `user_following_relation` VALUES (159, 1, 159, 3, 9, 5, b'0', '2022-02-20 03:48:05', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (160, 1, 160, 3, 9, 5, b'1', '2022-02-20 03:50:04', '2024-06-07 12:09:57');
INSERT INTO `user_following_relation` VALUES (161, 1, 161, 3, 9, 5, b'1', '2022-02-20 03:54:18', '2024-06-07 12:10:01');
INSERT INTO `user_following_relation` VALUES (162, 1, 162, 3, 9, 5, b'1', '2022-02-20 03:56:15', '2024-06-07 12:10:04');
INSERT INTO `user_following_relation` VALUES (163, 1, 163, 3, 9, 5, b'1', '2022-02-21 03:16:25', '2024-06-07 12:10:45');
INSERT INTO `user_following_relation` VALUES (164, 1, 164, 3, 9, 5, b'1', '2022-02-21 15:20:16', '2024-06-07 12:10:49');
INSERT INTO `user_following_relation` VALUES (165, 1, 165, 3, 21, 5, b'0', '2022-02-22 07:28:52', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (166, 1, 166, 3, 21, 5, b'0', '2022-02-22 07:32:53', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (167, 1, 167, 3, 21, 5, b'0', '2022-02-22 07:33:23', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (168, 1, 168, 3, 9, 5, b'0', '2022-02-22 11:55:35', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (169, 1, 169, 3, 21, 5, b'0', '2022-02-22 16:21:45', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (170, 1, 170, 3, 21, 5, b'0', '2022-02-22 16:22:18', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (171, 1, 171, 3, 21, 5, b'0', '2022-02-22 16:22:37', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (172, 1, 172, 3, 21, 5, b'0', '2022-02-22 16:23:13', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (173, 1, 173, 3, 21, 5, b'0', '2022-02-22 16:24:26', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (174, 1, 174, 3, 21, 5, b'0', '2022-02-22 16:24:43', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (175, 1, 175, 3, 21, 5, b'0', '2022-02-22 16:25:04', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (176, 1, 176, 3, 21, 5, b'0', '2022-02-22 16:25:25', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (177, 1, 177, 3, 21, 5, b'0', '2022-02-22 16:25:47', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (178, 1, 178, 3, 21, 5, b'0', '2022-02-22 16:26:03', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (179, 1, 179, 3, 21, 5, b'0', '2022-02-22 16:26:21', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (180, 1, 180, 3, 21, 5, b'0', '2022-02-22 16:26:40', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (181, 1, 181, 3, 21, 5, b'0', '2022-02-22 16:26:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (182, 1, 182, 3, 21, 5, b'0', '2022-02-22 16:27:12', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (183, 1, 183, 3, 21, 5, b'0', '2022-02-22 16:27:31', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (184, 1, 184, 3, 21, 5, b'0', '2022-02-22 16:27:47', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (185, 1, 185, 3, 21, 5, b'0', '2022-02-22 16:28:00', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (186, 1, 186, 3, 21, 5, b'0', '2022-02-22 16:28:13', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (187, 1, 187, 3, 21, 5, b'0', '2022-02-22 16:28:29', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (188, 1, 188, 3, 21, 5, b'0', '2022-02-22 16:28:46', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (190, 1, 190, 3, 21, 5, b'0', '2022-02-22 16:29:08', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (191, 1, 191, 3, 21, 5, b'0', '2022-02-22 16:29:38', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (192, 1, 192, 3, 21, 5, b'0', '2022-02-22 16:30:00', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (193, 1, 193, 3, 21, 5, b'0', '2022-02-22 16:30:18', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (194, 1, 194, 3, 21, 5, b'0', '2022-02-22 16:45:49', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (195, 1, 195, 3, 21, 5, b'0', '2022-02-22 16:46:03', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (196, 1, 196, 3, 21, 5, b'0', '2022-02-22 16:46:19', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (197, 1, 197, 3, 21, 5, b'0', '2022-02-22 16:46:37', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (198, 1, 198, 3, 21, 5, b'0', '2022-02-22 16:46:52', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (200, 1, 200, 3, 21, 5, b'0', '2022-02-22 16:47:46', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (201, 1, 201, 3, 21, 5, b'0', '2022-02-22 16:48:37', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (202, 1, 202, 3, 21, 5, b'0', '2022-02-22 16:48:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (203, 1, 203, 3, 21, 5, b'0', '2022-02-22 16:49:11', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (204, 1, 204, 3, 21, 5, b'0', '2022-02-22 16:49:34', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (205, 1, 205, 3, 21, 5, b'0', '2022-02-22 16:49:53', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (206, 1, 206, 3, 21, 5, b'0', '2022-02-22 16:50:10', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (207, 1, 207, 3, 21, 5, b'0', '2022-02-22 16:50:26', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (208, 1, 208, 3, 21, 5, b'0', '2022-02-22 16:50:44', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (209, 1, 209, 3, 9, 5, b'1', '2022-02-25 12:07:52', '2024-06-07 12:10:52');
INSERT INTO `user_following_relation` VALUES (210, 1, 210, 3, 9, 5, b'1', '2022-02-25 12:29:38', '2024-06-07 12:10:15');
INSERT INTO `user_following_relation` VALUES (211, 1, 211, 3, 9, 5, b'1', '2022-02-25 15:05:40', '2024-06-07 12:10:18');
INSERT INTO `user_following_relation` VALUES (212, 1, 212, 3, 9, 5, b'1', '2022-02-25 15:08:34', '2024-06-07 12:10:24');
INSERT INTO `user_following_relation` VALUES (213, 1, 213, 3, 9, 5, b'1', '2022-02-26 02:16:35', '2024-06-07 12:08:59');
INSERT INTO `user_following_relation` VALUES (214, 1, 214, 3, 9, 5, b'1', '2022-02-26 02:22:34', '2024-06-07 12:09:01');
INSERT INTO `user_following_relation` VALUES (215, 1, 215, 3, 9, 5, b'1', '2022-02-26 02:26:35', '2024-06-07 12:09:04');
INSERT INTO `user_following_relation` VALUES (216, 1, 216, 3, 9, 5, b'1', '2022-02-26 02:35:52', '2024-06-07 12:09:07');
INSERT INTO `user_following_relation` VALUES (217, 1, 217, 3, 9, 5, b'1', '2022-02-26 02:44:03', '2024-06-07 12:09:11');
INSERT INTO `user_following_relation` VALUES (218, 1, 218, 3, 9, 5, b'1', '2022-02-26 15:45:10', '2024-06-07 12:09:15');
INSERT INTO `user_following_relation` VALUES (219, 1, 219, 3, 9, 5, b'1', '2022-02-27 03:50:27', '2024-06-07 12:09:18');
INSERT INTO `user_following_relation` VALUES (220, 1, 220, 3, 9, 5, b'1', '2022-02-27 06:31:30', '2024-06-07 12:09:21');
INSERT INTO `user_following_relation` VALUES (221, 1, 221, 3, 9, 5, b'1', '2022-03-01 11:21:41', '2024-06-07 12:09:24');
INSERT INTO `user_following_relation` VALUES (222, 1, 222, 3, 9, 5, b'1', '2022-03-01 11:24:02', '2024-06-07 12:09:27');
INSERT INTO `user_following_relation` VALUES (223, 1, 223, 3, 9, 5, b'1', '2022-03-01 11:28:31', '2024-06-07 12:07:46');
INSERT INTO `user_following_relation` VALUES (224, 1, 224, 3, 9, 5, b'1', '2022-03-01 17:31:48', '2024-06-07 12:07:50');
INSERT INTO `user_following_relation` VALUES (225, 1, 225, 3, 9, 5, b'1', '2022-03-01 17:35:33', '2024-06-07 12:07:53');
INSERT INTO `user_following_relation` VALUES (226, 1, 226, 3, 9, 5, b'1', '2022-03-03 05:47:48', '2024-06-07 12:07:57');
INSERT INTO `user_following_relation` VALUES (227, 1, 227, 3, 9, 5, b'1', '2022-03-03 12:15:32', '2024-06-07 12:09:49');
INSERT INTO `user_following_relation` VALUES (228, 1, 228, 3, 9, 5, b'1', '2022-03-04 05:29:43', '2024-06-07 12:08:35');
INSERT INTO `user_following_relation` VALUES (229, 1, 229, 3, 9, 5, b'1', '2022-03-04 15:04:10', '2024-06-07 12:08:38');
INSERT INTO `user_following_relation` VALUES (230, 1, 230, 3, 9, 5, b'1', '2022-03-05 15:10:19', '2024-06-07 12:08:42');
INSERT INTO `user_following_relation` VALUES (231, 1, 231, 3, 9, 5, b'1', '2022-03-06 15:23:40', '2024-06-07 12:08:48');
INSERT INTO `user_following_relation` VALUES (232, 1, 232, 3, 9, 5, b'1', '2022-03-06 15:23:57', '2024-06-07 12:08:51');
INSERT INTO `user_following_relation` VALUES (234, 1, 234, 3, 9, 5, b'1', '2022-03-06 15:29:21', '2024-06-07 12:04:31');
INSERT INTO `user_following_relation` VALUES (235, 1, 235, 3, 9, 5, b'1', '2022-03-07 16:56:17', '2024-06-07 12:04:34');
INSERT INTO `user_following_relation` VALUES (236, 1, 236, 3, 9, 5, b'1', '2022-03-08 02:27:35', '2024-06-07 12:04:40');
INSERT INTO `user_following_relation` VALUES (237, 1, 237, 3, 9, 5, b'1', '2022-03-10 04:50:22', '2024-06-07 12:04:44');
INSERT INTO `user_following_relation` VALUES (238, 1, 238, 3, 9, 5, b'1', '2022-03-10 04:53:43', '2024-06-07 12:04:54');
INSERT INTO `user_following_relation` VALUES (239, 1, 239, 3, 9, 5, b'1', '2022-03-10 04:58:30', '2024-06-07 12:05:04');
INSERT INTO `user_following_relation` VALUES (240, 1, 240, 3, 9, 5, b'1', '2022-03-10 05:05:18', '2024-06-07 12:05:21');
INSERT INTO `user_following_relation` VALUES (241, 1, 241, 3, 9, 5, b'0', '2022-03-10 17:50:08', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (242, 1, 242, 3, 9, 5, b'1', '2022-03-11 02:25:28', '2024-06-07 12:05:28');
INSERT INTO `user_following_relation` VALUES (243, 1, 243, 3, 9, 5, b'1', '2022-03-11 13:44:27', '2024-06-07 12:07:12');
INSERT INTO `user_following_relation` VALUES (244, 1, 244, 3, 9, 5, b'1', '2022-03-12 03:05:49', '2024-06-07 12:03:45');
INSERT INTO `user_following_relation` VALUES (245, 1, 245, 3, 9, 5, b'1', '2022-03-14 10:32:51', '2024-06-07 12:03:53');
INSERT INTO `user_following_relation` VALUES (246, 1, 246, 3, 9, 5, b'1', '2022-03-17 08:29:28', '2024-06-07 12:03:57');
INSERT INTO `user_following_relation` VALUES (247, 1, 247, 3, 9, 5, b'1', '2022-03-18 02:22:34', '2024-06-07 12:04:00');
INSERT INTO `user_following_relation` VALUES (248, 1, 248, 3, 9, 5, b'1', '2022-03-18 03:12:32', '2024-06-07 12:04:05');
INSERT INTO `user_following_relation` VALUES (249, 1, 249, 3, 9, 5, b'1', '2022-03-20 02:30:51', '2024-06-07 12:04:08');
INSERT INTO `user_following_relation` VALUES (250, 1, 250, 3, 9, 5, b'1', '2022-03-23 06:14:06', '2024-06-07 12:04:11');
INSERT INTO `user_following_relation` VALUES (251, 1, 251, 3, 9, 5, b'1', '2022-03-24 04:46:40', '2024-06-07 12:04:15');
INSERT INTO `user_following_relation` VALUES (252, 1, 252, 3, 9, 5, b'1', '2022-03-24 04:47:10', '2024-06-07 12:04:19');
INSERT INTO `user_following_relation` VALUES (257, 1, 257, 3, 9, 5, b'1', '2022-03-24 15:11:36', '2024-06-07 12:04:23');
INSERT INTO `user_following_relation` VALUES (258, 1, 258, 3, 9, 5, b'1', '2022-05-02 13:49:31', '2024-06-07 11:59:15');
INSERT INTO `user_following_relation` VALUES (259, 1, 259, 3, 9, 5, b'1', '2022-05-29 10:47:41', '2024-06-07 11:58:54');
INSERT INTO `user_following_relation` VALUES (262, 1, 262, 3, 9, 6, b'0', '2022-11-09 14:35:38', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (263, 1, 263, 3, 9, 5, b'1', '2022-12-01 06:27:22', '2024-06-07 11:58:57');
INSERT INTO `user_following_relation` VALUES (264, 1, 264, 3, 9, 5, b'1', '2022-12-02 05:28:05', '2024-06-07 11:59:29');
INSERT INTO `user_following_relation` VALUES (265, 1, 265, 3, 9, 5, b'1', '2022-12-06 06:22:42', '2024-06-07 11:59:37');
INSERT INTO `user_following_relation` VALUES (266, 1, 266, 3, 9, 5, b'1', '2022-12-06 06:23:35', '2024-06-07 11:59:51');
INSERT INTO `user_following_relation` VALUES (267, 1, 267, 3, 9, 6, b'0', '2024-05-10 15:48:40', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (268, 1, 268, 3, 9, 5, b'1', '2024-05-12 15:53:08', '2024-06-07 12:00:08');
INSERT INTO `user_following_relation` VALUES (269, 1, 269, 3, 9, 5, b'0', '2024-05-12 16:36:33', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (270, 1, 270, 3, 9, 5, b'0', '2024-05-14 15:28:04', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (271, 1, 271, 3, 9, 5, b'0', '2024-05-15 17:04:31', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (281, 1, 18, 2, 22, 5, b'0', '2024-05-16 02:48:13', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (282, 1, 281, 2, 22, 5, b'0', '2024-05-16 02:52:16', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (283, 1, 282, 2, 22, 5, b'0', '2024-05-16 02:53:58', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (284, 1, 283, 2, 22, 5, b'0', '2024-05-16 02:54:29', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (285, 1, 284, 3, 9, 5, b'0', '2024-05-16 19:10:26', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (286, 1, 285, 3, 9, 5, b'1', '2024-05-16 19:14:50', '2024-06-07 12:03:11');
INSERT INTO `user_following_relation` VALUES (287, 1, 286, 3, 9, 5, b'1', '2024-05-16 19:20:05', '2024-06-07 12:03:07');
INSERT INTO `user_following_relation` VALUES (288, 1, 287, 3, 9, 5, b'1', '2024-05-16 19:21:38', '2024-06-07 12:02:41');
INSERT INTO `user_following_relation` VALUES (289, 1, 288, 3, 9, 5, b'0', '2024-05-18 21:27:59', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (290, 1, 289, 3, 9, 5, b'0', '2024-05-19 01:07:02', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (291, 1, 290, 3, 9, 5, b'1', '2024-05-19 01:10:45', '2024-06-07 12:03:24');
INSERT INTO `user_following_relation` VALUES (292, 1, 291, 3, 9, 5, b'1', '2024-05-19 01:15:33', '2024-06-07 12:00:29');
INSERT INTO `user_following_relation` VALUES (293, 1, 292, 3, 9, 5, b'1', '2024-05-21 19:03:26', '2024-06-07 12:00:33');
INSERT INTO `user_following_relation` VALUES (294, 1, 293, 3, 9, 5, b'1', '2024-05-21 19:14:57', '2024-06-07 12:00:55');
INSERT INTO `user_following_relation` VALUES (295, 1, 294, 3, 9, 5, b'1', '2024-05-21 19:18:09', '2024-06-07 12:01:09');
INSERT INTO `user_following_relation` VALUES (297, 1, 296, 3, 9, 5, b'1', '2024-05-21 19:20:58', '2024-06-07 12:01:00');
INSERT INTO `user_following_relation` VALUES (298, 1, 297, 3, 9, 5, b'0', '2024-05-23 21:42:56', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (299, 1, 298, 3, 9, 5, b'1', '2024-05-23 21:53:29', '2024-06-07 12:01:32');
INSERT INTO `user_following_relation` VALUES (300, 1, 299, 3, 9, 5, b'1', '2024-05-24 14:29:22', '2024-06-07 12:01:35');
INSERT INTO `user_following_relation` VALUES (301, 1, 300, 3, 9, 5, b'1', '2024-05-25 09:51:23', '2024-06-07 12:01:39');
INSERT INTO `user_following_relation` VALUES (302, 1, 301, 3, 9, 5, b'0', '2024-05-25 19:45:08', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (303, 1, 302, 3, 9, 5, b'1', '2024-05-25 19:52:08', '2024-06-07 12:02:19');
INSERT INTO `user_following_relation` VALUES (304, 1, 303, 3, 9, 5, b'1', '2024-06-02 11:31:17', '2024-06-07 12:02:23');
INSERT INTO `user_following_relation` VALUES (305, 1, 304, 3, 9, 5, b'0', '2024-06-07 09:58:32', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (306, 1, 305, 3, 9, 5, b'0', '2024-06-07 10:03:10', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (307, 1, 306, 3, 9, 5, b'0', '2024-06-07 10:15:59', '2024-06-07 10:52:20');
INSERT INTO `user_following_relation` VALUES (308, 1, 307, 3, 9, 5, b'0', '2024-06-07 10:20:06', '2024-06-07 10:52:20');

-- ----------------------------
-- Table structure for user_following_remark
-- ----------------------------
DROP TABLE IF EXISTS `user_following_remark`;
CREATE TABLE `user_following_remark`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id',
  `following_id` bigint(20) UNSIGNED NOT NULL COMMENT '注意是用户关系表的id',
  `label_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注/标签',
  `sort_no` tinyint(3) UNSIGNED NOT NULL DEFAULT 5 COMMENT '优先级由低到高：1-10，默认5',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_where_order`(`user_id`, `following_id`, `sort_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 191 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关注备注（标签）表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_following_remark
-- ----------------------------
INSERT INTO `user_following_remark` VALUES (1, 1, 1, '独立思考，拒绝投喂', 1, b'0', '2021-01-02 10:39:41', '2021-07-13 17:17:53');
INSERT INTO `user_following_remark` VALUES (2, 1, 1, '适度安排，避免加压', 2, b'0', '2021-01-02 10:39:41', '2021-07-13 17:17:53');
INSERT INTO `user_following_remark` VALUES (3, 1, 1, '观点信号，自身现实', 3, b'0', '2021-01-02 10:39:41', '2021-07-13 17:17:53');
INSERT INTO `user_following_remark` VALUES (4, 1, 2, '别人的生活始终是别人的', 1, b'0', '2021-01-02 10:42:26', '2021-11-29 16:40:05');
INSERT INTO `user_following_remark` VALUES (5, 1, 2, '认清自身的实际情况，参考学习', 2, b'0', '2021-01-02 10:42:26', '2021-11-29 16:40:05');
INSERT INTO `user_following_remark` VALUES (6, 1, 3, '（格局太高，打工人可能用不到）', 1, b'0', '2021-01-02 10:46:38', '2021-01-02 10:46:38');
INSERT INTO `user_following_remark` VALUES (7, 1, 3, '大环境', 2, b'0', '2021-01-02 10:46:38', '2021-01-02 10:46:38');
INSERT INTO `user_following_remark` VALUES (8, 1, 3, '格局', 3, b'0', '2021-01-02 10:46:38', '2021-01-02 10:46:38');
INSERT INTO `user_following_remark` VALUES (9, 1, 3, '商业', 4, b'0', '2021-01-02 10:46:38', '2021-01-02 10:46:38');
INSERT INTO `user_following_remark` VALUES (10, 1, 3, '科技', 5, b'0', '2021-01-02 10:46:38', '2021-01-02 10:46:38');
INSERT INTO `user_following_remark` VALUES (11, 1, 4, '财经', 5, b'0', '2021-01-02 10:48:08', '2021-01-02 10:48:08');
INSERT INTO `user_following_remark` VALUES (12, 1, 4, '投资', 5, b'0', '2021-01-02 10:48:08', '2021-01-02 10:48:08');
INSERT INTO `user_following_remark` VALUES (13, 1, 4, '格局', 5, b'0', '2021-01-02 10:48:08', '2021-01-02 10:48:08');
INSERT INTO `user_following_remark` VALUES (14, 1, 4, '大环境', 5, b'0', '2021-01-02 10:48:08', '2021-01-02 10:48:08');
INSERT INTO `user_following_remark` VALUES (15, 1, 5, '知识', 5, b'0', '2021-01-02 11:19:57', '2021-01-02 11:19:57');
INSERT INTO `user_following_remark` VALUES (16, 1, 5, '生活', 5, b'0', '2021-01-02 11:19:57', '2021-01-02 11:19:57');
INSERT INTO `user_following_remark` VALUES (17, 1, 6, '心理', 5, b'0', '2021-01-02 11:20:50', '2021-01-02 11:20:50');
INSERT INTO `user_following_remark` VALUES (18, 1, 6, '生活', 5, b'0', '2021-01-02 11:20:50', '2021-01-02 11:20:50');
INSERT INTO `user_following_remark` VALUES (19, 1, 7, '（事实道理都懂，保持独立思考）', 1, b'0', '2021-01-02 11:23:45', '2021-01-02 11:23:45');
INSERT INTO `user_following_remark` VALUES (20, 1, 7, '经验分享', 2, b'0', '2021-01-02 11:23:45', '2021-01-02 11:23:45');
INSERT INTO `user_following_remark` VALUES (21, 1, 7, '思维理论', 3, b'0', '2021-01-02 11:23:45', '2021-01-02 11:23:45');
INSERT INTO `user_following_remark` VALUES (22, 1, 8, '（娱乐就行，思维影响不大）', 1, b'0', '2021-01-02 11:34:24', '2021-01-02 11:34:24');
INSERT INTO `user_following_remark` VALUES (23, 1, 8, '经验分享', 2, b'0', '2021-01-02 11:34:24', '2021-01-02 11:34:24');
INSERT INTO `user_following_remark` VALUES (24, 1, 8, '思维理论', 3, b'0', '2021-01-02 11:34:24', '2021-01-02 11:34:24');
INSERT INTO `user_following_remark` VALUES (25, 1, 9, '社会问题', 5, b'0', '2021-01-02 11:35:25', '2021-01-02 11:35:25');
INSERT INTO `user_following_remark` VALUES (26, 1, 9, '黑客领域', 5, b'0', '2021-01-02 11:35:25', '2021-01-02 11:35:25');
INSERT INTO `user_following_remark` VALUES (27, 1, 10, '饮食', 1, b'0', '2021-01-02 11:36:39', '2022-11-27 14:29:55');
INSERT INTO `user_following_remark` VALUES (28, 1, 10, '营养', 2, b'0', '2021-01-02 11:36:39', '2022-11-27 14:29:55');
INSERT INTO `user_following_remark` VALUES (29, 1, 10, '健康', 3, b'0', '2021-01-02 11:36:39', '2022-11-27 14:29:55');
INSERT INTO `user_following_remark` VALUES (30, 1, 11, '田园生活', 5, b'0', '2021-01-02 11:37:54', '2021-01-02 11:37:54');
INSERT INTO `user_following_remark` VALUES (31, 1, 11, '传统文化', 5, b'0', '2021-01-02 11:37:54', '2021-01-02 11:37:54');
INSERT INTO `user_following_remark` VALUES (32, 1, 12, '美食', 5, b'0', '2021-01-02 11:38:48', '2021-01-02 11:38:48');
INSERT INTO `user_following_remark` VALUES (33, 1, 12, '分享', 5, b'0', '2021-01-02 11:38:48', '2021-01-02 11:38:48');
INSERT INTO `user_following_remark` VALUES (34, 1, 13, '健身', 1, b'0', '2021-01-02 11:40:11', '2022-11-27 14:29:44');
INSERT INTO `user_following_remark` VALUES (35, 1, 13, '生活', 2, b'0', '2021-01-02 11:40:11', '2022-11-27 14:29:44');
INSERT INTO `user_following_remark` VALUES (36, 1, 14, '健身', 5, b'0', '2021-01-02 11:40:43', '2021-01-02 11:40:43');
INSERT INTO `user_following_remark` VALUES (37, 1, 14, '生活', 5, b'0', '2021-01-02 11:40:43', '2021-01-02 11:40:43');
INSERT INTO `user_following_remark` VALUES (38, 1, 15, '（看看就行了）', 1, b'0', '2021-01-02 11:42:26', '2021-01-02 11:42:26');
INSERT INTO `user_following_remark` VALUES (39, 1, 15, '健康', 2, b'0', '2021-01-02 11:42:26', '2021-01-02 11:42:26');
INSERT INTO `user_following_remark` VALUES (40, 1, 15, '护肤', 3, b'0', '2021-01-02 11:42:26', '2021-01-02 11:42:26');
INSERT INTO `user_following_remark` VALUES (41, 1, 16, '（推荐药品依然要谨慎买药用药）', 1, b'0', '2021-01-02 11:43:41', '2021-01-02 11:43:41');
INSERT INTO `user_following_remark` VALUES (42, 1, 16, '皮肤病学', 2, b'0', '2021-01-02 11:43:41', '2021-01-02 11:43:41');
INSERT INTO `user_following_remark` VALUES (43, 1, 17, '疾病', 5, b'0', '2021-01-02 11:45:29', '2021-01-02 11:45:29');
INSERT INTO `user_following_remark` VALUES (44, 1, 17, '健康', 5, b'0', '2021-01-02 11:45:29', '2021-01-02 11:45:29');
INSERT INTO `user_following_remark` VALUES (45, 1, 17, '科普', 5, b'0', '2021-01-02 11:45:29', '2021-01-02 11:45:29');
INSERT INTO `user_following_remark` VALUES (46, 1, 17, '注意不要自行带入', 5, b'0', '2021-01-02 11:45:29', '2021-01-02 11:45:29');
INSERT INTO `user_following_remark` VALUES (47, 1, 18, '绘画', 1, b'0', '2021-01-02 11:47:07', '2024-05-12 06:27:46');
INSERT INTO `user_following_remark` VALUES (48, 1, 18, '知识', 2, b'0', '2021-01-02 11:47:07', '2024-05-12 06:27:46');
INSERT INTO `user_following_remark` VALUES (49, 1, 18, '科普', 3, b'0', '2021-01-02 11:47:07', '2024-05-12 06:27:46');
INSERT INTO `user_following_remark` VALUES (50, 1, 19, '生活', 5, b'0', '2021-01-02 11:47:50', '2021-01-02 11:47:50');
INSERT INTO `user_following_remark` VALUES (51, 1, 19, '分享', 5, b'0', '2021-01-02 11:47:50', '2021-01-02 11:47:50');
INSERT INTO `user_following_remark` VALUES (52, 1, 20, '生活', 5, b'0', '2021-01-02 11:48:28', '2021-01-02 11:48:28');
INSERT INTO `user_following_remark` VALUES (53, 1, 20, '分享', 5, b'0', '2021-01-02 11:48:28', '2021-01-02 11:48:28');
INSERT INTO `user_following_remark` VALUES (54, 1, 21, 'ASMR', 1, b'0', '2021-01-02 11:49:07', '2021-01-02 11:49:07');
INSERT INTO `user_following_remark` VALUES (55, 1, 21, '生活', 2, b'0', '2021-01-02 11:49:07', '2021-01-02 11:49:07');
INSERT INTO `user_following_remark` VALUES (56, 1, 22, '（重质量，更新慢）', 1, b'0', '2021-01-02 11:50:37', '2021-01-02 11:50:37');
INSERT INTO `user_following_remark` VALUES (57, 1, 22, 'AI', 2, b'0', '2021-01-02 11:50:37', '2021-01-02 11:50:37');
INSERT INTO `user_following_remark` VALUES (58, 1, 22, '算法', 3, b'0', '2021-01-02 11:50:37', '2021-01-02 11:50:37');
INSERT INTO `user_following_remark` VALUES (59, 1, 23, '电子技术', 5, b'0', '2021-01-02 11:51:25', '2021-01-02 11:51:25');
INSERT INTO `user_following_remark` VALUES (60, 1, 24, '计算机技术', 5, b'0', '2021-01-02 11:52:02', '2021-01-02 11:52:02');
INSERT INTO `user_following_remark` VALUES (61, 1, 25, '娱乐明星', 2, b'0', '2021-01-02 12:05:12', '2024-05-06 10:11:35');
INSERT INTO `user_following_remark` VALUES (62, 1, 25, '资本斗秀场', 3, b'0', '2021-01-02 12:05:12', '2024-05-06 10:11:35');
INSERT INTO `user_following_remark` VALUES (63, 1, 25, '党政宣传干预', 1, b'0', '2021-01-02 12:05:12', '2021-01-02 12:05:12');
INSERT INTO `user_following_remark` VALUES (64, 1, 26, '党政', 1, b'0', '2021-01-02 12:06:41', '2024-05-10 16:41:49');
INSERT INTO `user_following_remark` VALUES (65, 1, 26, '官方', 2, b'0', '2021-01-02 12:06:41', '2024-05-10 16:41:49');
INSERT INTO `user_following_remark` VALUES (66, 1, 27, '党政', 5, b'0', '2021-01-02 12:07:23', '2021-01-02 12:07:23');
INSERT INTO `user_following_remark` VALUES (67, 1, 27, '官方', 5, b'0', '2021-01-02 12:07:23', '2021-01-02 12:07:23');
INSERT INTO `user_following_remark` VALUES (68, 1, 28, '党政', 1, b'0', '2021-01-02 12:07:58', '2024-05-10 16:42:16');
INSERT INTO `user_following_remark` VALUES (69, 1, 28, '官方', 2, b'0', '2021-01-02 12:07:58', '2024-05-10 16:42:16');
INSERT INTO `user_following_remark` VALUES (70, 1, 29, '党政', 1, b'0', '2021-01-02 12:08:38', '2024-05-10 16:42:26');
INSERT INTO `user_following_remark` VALUES (71, 1, 29, '官方', 2, b'0', '2021-01-02 12:08:38', '2024-05-10 16:42:26');
INSERT INTO `user_following_remark` VALUES (72, 1, 30, '党政', 5, b'0', '2021-01-02 12:09:17', '2021-01-02 12:09:17');
INSERT INTO `user_following_remark` VALUES (73, 1, 30, '官方', 5, b'0', '2021-01-02 12:09:17', '2021-01-02 12:09:17');
INSERT INTO `user_following_remark` VALUES (74, 1, 31, '生命', 1, b'0', '2021-01-02 12:10:01', '2024-05-10 16:42:01');
INSERT INTO `user_following_remark` VALUES (75, 1, 31, '健康', 2, b'0', '2021-01-02 12:10:01', '2024-05-10 16:42:01');
INSERT INTO `user_following_remark` VALUES (76, 1, 32, '时事', 5, b'0', '2021-01-02 12:11:17', '2021-01-02 12:11:17');
INSERT INTO `user_following_remark` VALUES (77, 1, 32, '要闻', 5, b'0', '2021-01-02 12:11:17', '2021-01-02 12:11:17');
INSERT INTO `user_following_remark` VALUES (78, 1, 32, '党政', 5, b'0', '2021-01-02 12:11:17', '2021-01-02 12:11:17');
INSERT INTO `user_following_remark` VALUES (79, 1, 32, '鸡毛', 5, b'0', '2021-01-02 12:11:17', '2021-01-02 12:11:17');
INSERT INTO `user_following_remark` VALUES (80, 1, 33, '时事', 5, b'0', '2021-01-02 12:13:50', '2021-01-02 12:13:50');
INSERT INTO `user_following_remark` VALUES (81, 1, 33, '要闻', 5, b'0', '2021-01-02 12:13:50', '2021-01-02 12:13:50');
INSERT INTO `user_following_remark` VALUES (82, 1, 33, '党政', 5, b'0', '2021-01-02 12:13:50', '2021-01-02 12:13:50');
INSERT INTO `user_following_remark` VALUES (83, 1, 33, '鸡毛', 5, b'0', '2021-01-02 12:13:50', '2021-01-02 12:13:50');
INSERT INTO `user_following_remark` VALUES (84, 1, 34, '宏观经济', 5, b'0', '2021-01-02 12:14:57', '2021-01-02 12:14:57');
INSERT INTO `user_following_remark` VALUES (85, 1, 35, '科技大杂烩', 5, b'0', '2021-01-02 12:15:35', '2021-01-02 12:15:35');
INSERT INTO `user_following_remark` VALUES (86, 1, 36, '田园生活', 5, b'0', '2021-01-03 09:28:55', '2021-01-03 09:28:55');
INSERT INTO `user_following_remark` VALUES (87, 1, 36, '传统文化', 5, b'0', '2021-01-03 09:28:55', '2021-01-03 09:28:55');
INSERT INTO `user_following_remark` VALUES (88, 1, 37, '美食', 5, b'0', '2021-01-03 09:54:37', '2021-01-03 09:54:37');
INSERT INTO `user_following_remark` VALUES (89, 1, 37, '分享', 5, b'0', '2021-01-03 09:54:37', '2021-01-03 09:54:37');
INSERT INTO `user_following_remark` VALUES (90, 1, 38, '美食', 5, b'0', '2021-01-03 09:56:48', '2021-01-03 09:56:48');
INSERT INTO `user_following_remark` VALUES (91, 1, 38, '分享', 5, b'0', '2021-01-03 09:56:48', '2021-01-03 09:56:48');
INSERT INTO `user_following_remark` VALUES (92, 1, 39, '美食', 5, b'0', '2021-01-03 09:57:45', '2021-01-03 09:57:45');
INSERT INTO `user_following_remark` VALUES (93, 1, 39, '分享', 5, b'0', '2021-01-03 09:57:45', '2021-01-03 09:57:45');
INSERT INTO `user_following_remark` VALUES (94, 1, 40, '美食', 5, b'0', '2021-01-03 09:58:30', '2021-01-03 09:58:30');
INSERT INTO `user_following_remark` VALUES (95, 1, 40, '分享', 5, b'0', '2021-01-03 09:58:30', '2021-01-03 09:58:30');
INSERT INTO `user_following_remark` VALUES (96, 1, 41, '饮食', 5, b'0', '2021-01-03 10:00:58', '2021-01-03 10:00:58');
INSERT INTO `user_following_remark` VALUES (97, 1, 41, '营养', 5, b'0', '2021-01-03 10:00:58', '2021-01-03 10:00:58');
INSERT INTO `user_following_remark` VALUES (98, 1, 41, '健康', 5, b'0', '2021-01-03 10:00:58', '2021-01-03 10:00:58');
INSERT INTO `user_following_remark` VALUES (99, 1, 42, '摄影', 5, b'0', '2021-01-03 10:01:22', '2021-01-03 10:01:22');
INSERT INTO `user_following_remark` VALUES (100, 1, 42, '教程', 5, b'0', '2021-01-03 10:01:22', '2021-01-03 10:01:22');
INSERT INTO `user_following_remark` VALUES (101, 1, 43, '资源', 5, b'0', '2021-01-03 10:02:21', '2021-01-03 10:02:21');
INSERT INTO `user_following_remark` VALUES (102, 1, 43, '教程', 5, b'0', '2021-01-03 10:02:21', '2021-01-03 10:02:21');
INSERT INTO `user_following_remark` VALUES (103, 1, 44, '知识', 5, b'0', '2021-01-03 10:03:19', '2021-01-03 10:03:19');
INSERT INTO `user_following_remark` VALUES (104, 1, 44, '科普', 5, b'0', '2021-01-03 10:03:19', '2021-01-03 10:03:19');
INSERT INTO `user_following_remark` VALUES (105, 1, 45, '生活', 5, b'0', '2021-01-03 10:04:16', '2021-01-03 10:04:16');
INSERT INTO `user_following_remark` VALUES (106, 1, 45, '分享', 5, b'0', '2021-01-03 10:04:16', '2021-01-03 10:04:16');
INSERT INTO `user_following_remark` VALUES (107, 1, 46, '理财', 5, b'0', '2021-01-03 10:05:08', '2021-01-03 10:05:08');
INSERT INTO `user_following_remark` VALUES (108, 1, 46, '基金', 5, b'0', '2021-01-03 10:05:08', '2021-01-03 10:05:08');
INSERT INTO `user_following_remark` VALUES (109, 1, 47, '街拍', 5, b'0', '2021-01-03 10:06:04', '2021-01-03 10:06:04');
INSERT INTO `user_following_remark` VALUES (110, 1, 47, '时尚', 5, b'0', '2021-01-03 10:06:04', '2021-01-03 10:06:04');
INSERT INTO `user_following_remark` VALUES (111, 1, 47, '审美', 5, b'0', '2021-01-03 10:06:04', '2021-01-03 10:06:04');
INSERT INTO `user_following_remark` VALUES (112, 1, 47, '欲望', 5, b'0', '2021-01-03 10:06:04', '2021-01-03 10:06:04');
INSERT INTO `user_following_remark` VALUES (113, 1, 48, '生活周边', 2, b'0', '2021-01-03 10:09:19', '2021-01-03 10:09:19');
INSERT INTO `user_following_remark` VALUES (114, 1, 48, '（别人的生活）', 2, b'1', '2021-01-03 10:09:19', '2021-01-03 10:09:19');
INSERT INTO `user_following_remark` VALUES (115, 1, 49, '（别人的生活）', 1, b'0', '2021-01-03 10:12:34', '2021-01-03 10:12:34');
INSERT INTO `user_following_remark` VALUES (116, 1, 49, '生活周边', 2, b'0', '2021-01-03 10:12:34', '2021-01-03 10:12:34');
INSERT INTO `user_following_remark` VALUES (117, 1, 50, '生活', 2, b'0', '2021-01-03 10:15:02', '2022-12-06 06:20:21');
INSERT INTO `user_following_remark` VALUES (118, 1, 51, 'UI', 1, b'0', '2021-01-03 10:17:24', '2021-01-03 10:17:24');
INSERT INTO `user_following_remark` VALUES (119, 1, 52, 'HW\'PD', 5, b'0', '2021-01-03 10:22:35', '2021-01-03 10:22:35');
INSERT INTO `user_following_remark` VALUES (120, 1, 53, '健身', 5, b'0', '2021-04-07 16:54:45', '2021-04-07 16:54:45');
INSERT INTO `user_following_remark` VALUES (121, 1, 53, '生活', 5, b'0', '2021-04-07 16:54:45', '2021-04-07 16:54:45');
INSERT INTO `user_following_remark` VALUES (122, 1, 54, 'IT', 5, b'0', '2021-04-07 16:56:04', '2021-04-07 16:56:04');
INSERT INTO `user_following_remark` VALUES (123, 1, 54, '技术', 5, b'0', '2021-04-07 16:56:04', '2021-04-07 16:56:04');
INSERT INTO `user_following_remark` VALUES (124, 1, 54, '生活', 5, b'0', '2021-04-07 16:56:04', '2021-04-07 16:56:04');
INSERT INTO `user_following_remark` VALUES (125, 1, 55, '（理性借鉴，结合自身条件背景）', 1, b'0', '2021-04-07 16:57:13', '2021-04-07 16:57:13');
INSERT INTO `user_following_remark` VALUES (126, 1, 55, '感悟', 2, b'0', '2021-04-07 16:57:13', '2021-04-07 16:57:13');
INSERT INTO `user_following_remark` VALUES (127, 1, 55, '经验', 3, b'0', '2021-04-07 16:57:13', '2021-04-07 16:57:13');
INSERT INTO `user_following_remark` VALUES (128, 1, 55, '生活', 4, b'0', '2021-04-07 16:57:13', '2021-04-07 16:57:13');
INSERT INTO `user_following_remark` VALUES (129, 1, 56, '时尚', 5, b'0', '2021-04-07 17:00:02', '2021-04-07 17:00:02');
INSERT INTO `user_following_remark` VALUES (130, 1, 56, '穿搭', 5, b'0', '2021-04-07 17:00:02', '2021-04-07 17:00:02');
INSERT INTO `user_following_remark` VALUES (131, 1, 56, '生活', 5, b'0', '2021-04-07 17:00:02', '2021-04-07 17:00:02');
INSERT INTO `user_following_remark` VALUES (132, 1, 57, '时尚', 5, b'0', '2021-04-07 17:00:36', '2021-04-07 17:00:36');
INSERT INTO `user_following_remark` VALUES (133, 1, 57, '穿搭', 5, b'0', '2021-04-07 17:00:36', '2021-04-07 17:00:36');
INSERT INTO `user_following_remark` VALUES (134, 1, 57, '生活', 5, b'0', '2021-04-07 17:00:36', '2021-04-07 17:00:36');
INSERT INTO `user_following_remark` VALUES (135, 1, 58, '时尚', 5, b'0', '2021-04-07 17:06:34', '2021-04-07 17:06:34');
INSERT INTO `user_following_remark` VALUES (136, 1, 58, '穿搭', 5, b'0', '2021-04-07 17:06:34', '2021-04-07 17:06:34');
INSERT INTO `user_following_remark` VALUES (137, 1, 58, '生活', 5, b'0', '2021-04-07 17:06:34', '2021-04-07 17:06:34');
INSERT INTO `user_following_remark` VALUES (138, 1, 59, '时尚', 5, b'0', '2021-04-07 17:08:09', '2021-04-07 17:08:09');
INSERT INTO `user_following_remark` VALUES (139, 1, 59, '穿搭', 5, b'0', '2021-04-07 17:08:09', '2021-04-07 17:08:09');
INSERT INTO `user_following_remark` VALUES (140, 1, 59, '生活', 5, b'0', '2021-04-07 17:08:09', '2021-04-07 17:08:09');
INSERT INTO `user_following_remark` VALUES (141, 1, 60, '青春', 5, b'0', '2021-04-07 17:41:53', '2021-04-07 17:41:53');
INSERT INTO `user_following_remark` VALUES (142, 1, 60, '正能量', 5, b'0', '2021-04-07 17:41:53', '2021-04-07 17:41:53');
INSERT INTO `user_following_remark` VALUES (143, 1, 60, '生活', 5, b'0', '2021-04-07 17:41:53', '2021-04-07 17:41:53');
INSERT INTO `user_following_remark` VALUES (144, 1, 61, '青春', 5, b'0', '2021-04-07 17:42:21', '2021-04-07 17:42:21');
INSERT INTO `user_following_remark` VALUES (145, 1, 61, '正能量', 5, b'0', '2021-04-07 17:42:21', '2021-04-07 17:42:21');
INSERT INTO `user_following_remark` VALUES (146, 1, 61, '生活', 5, b'0', '2021-04-07 17:42:21', '2021-04-07 17:42:21');
INSERT INTO `user_following_remark` VALUES (147, 1, 62, 'ASMR', 1, b'0', '2021-04-07 17:45:30', '2021-04-07 17:45:30');
INSERT INTO `user_following_remark` VALUES (148, 1, 62, '分享', 2, b'0', '2021-04-07 17:45:30', '2021-04-07 17:45:30');
INSERT INTO `user_following_remark` VALUES (149, 1, 62, '生活', 3, b'0', '2021-04-07 17:45:30', '2021-04-07 17:45:30');
INSERT INTO `user_following_remark` VALUES (150, 1, 63, '身材', 1, b'0', '2021-04-07 17:55:32', '2021-04-07 17:55:32');
INSERT INTO `user_following_remark` VALUES (151, 1, 63, '健身', 2, b'0', '2021-04-07 17:55:32', '2021-04-07 17:55:32');
INSERT INTO `user_following_remark` VALUES (152, 1, 63, '时尚', 3, b'0', '2021-04-07 17:55:32', '2021-04-07 17:55:32');
INSERT INTO `user_following_remark` VALUES (153, 1, 63, '生活', 4, b'0', '2021-04-20 17:09:37', '2021-04-20 17:09:37');
INSERT INTO `user_following_remark` VALUES (154, 1, 64, '健身', 5, b'1', '2021-04-20 17:09:40', '2021-04-20 17:09:40');
INSERT INTO `user_following_remark` VALUES (155, 1, 64, '身材', 5, b'1', '2021-04-20 17:13:02', '2021-04-20 17:13:02');
INSERT INTO `user_following_remark` VALUES (156, 1, 64, '时尚', 5, b'1', '2021-04-20 17:14:49', '2021-04-20 17:14:49');
INSERT INTO `user_following_remark` VALUES (157, 1, 64, '生活', 5, b'1', '2021-04-20 17:14:51', '2021-04-20 17:14:51');
INSERT INTO `user_following_remark` VALUES (158, 1, 65, '凌佳慧', 1, b'1', '2021-04-20 17:14:54', '2022-02-22 08:37:40');
INSERT INTO `user_following_remark` VALUES (159, 1, 65, '青春', 1, b'0', '2021-04-20 17:25:45', '2022-02-22 08:37:40');
INSERT INTO `user_following_remark` VALUES (160, 1, 65, '单纯', 5, b'1', '2021-04-20 17:25:46', '2021-10-07 09:11:07');
INSERT INTO `user_following_remark` VALUES (161, 1, 65, '可爱', 5, b'1', '2021-04-20 17:25:49', '2021-10-07 09:11:07');
INSERT INTO `user_following_remark` VALUES (166, 1, 68, '生活', 2, b'0', '2021-06-30 13:01:34', '2021-06-30 13:01:34');
INSERT INTO `user_following_remark` VALUES (167, 1, 68, '科普', 1, b'0', '2021-06-30 13:01:34', '2021-06-30 13:01:34');
INSERT INTO `user_following_remark` VALUES (168, 1, 69, '生活', 2, b'0', '2021-07-01 06:19:42', '2021-07-01 06:19:42');
INSERT INTO `user_following_remark` VALUES (169, 1, 69, '摄影', 1, b'0', '2021-07-01 06:19:42', '2021-10-07 09:10:49');
INSERT INTO `user_following_remark` VALUES (170, 1, 70, '生活', 2, b'0', '2021-10-07 09:11:44', '2021-10-07 09:11:44');
INSERT INTO `user_following_remark` VALUES (171, 1, 70, '记录', 1, b'0', '2021-10-07 09:11:44', '2021-10-07 09:11:44');
INSERT INTO `user_following_remark` VALUES (172, 1, 71, '电竞', 3, b'0', '2021-11-07 03:34:25', '2021-11-07 03:34:25');
INSERT INTO `user_following_remark` VALUES (173, 1, 71, '翻译', 2, b'0', '2021-11-07 03:34:25', '2021-11-07 03:34:25');
INSERT INTO `user_following_remark` VALUES (174, 1, 71, '主持', 1, b'0', '2021-11-07 03:34:25', '2021-11-07 03:34:25');
INSERT INTO `user_following_remark` VALUES (175, 1, 72, '附近', 2, b'0', '2022-01-09 17:39:21', '2022-01-09 17:39:21');
INSERT INTO `user_following_remark` VALUES (176, 1, 72, '沙雕', 1, b'0', '2022-01-09 17:39:21', '2022-01-09 17:39:21');
INSERT INTO `user_following_remark` VALUES (177, 1, 73, '活泼', 2, b'0', '2022-01-12 17:15:05', '2022-01-12 17:15:05');
INSERT INTO `user_following_remark` VALUES (178, 1, 73, '生活', 1, b'0', '2022-01-12 17:15:05', '2022-01-12 17:15:05');
INSERT INTO `user_following_remark` VALUES (179, 1, 74, '生活', 1, b'0', '2022-01-13 03:33:46', '2022-01-13 03:33:46');
INSERT INTO `user_following_remark` VALUES (180, 1, 75, '软件', 1, b'0', '2022-01-13 12:43:45', '2022-01-13 12:43:45');
INSERT INTO `user_following_remark` VALUES (181, 1, 76, '播音', 1, b'0', '2022-01-13 19:50:43', '2022-01-13 19:50:43');
INSERT INTO `user_following_remark` VALUES (182, 1, 77, '播音', 1, b'0', '2022-01-13 19:50:48', '2022-01-13 19:50:48');
INSERT INTO `user_following_remark` VALUES (187, 1, 262, '音乐', 2, b'0', '2022-11-09 14:35:38', '2022-11-09 14:35:38');
INSERT INTO `user_following_remark` VALUES (188, 1, 262, '青春', 1, b'0', '2022-11-09 14:35:38', '2022-11-09 14:35:38');
INSERT INTO `user_following_remark` VALUES (189, 1, 50, '青春', 1, b'1', '2022-12-06 06:20:21', '2022-12-06 06:20:23');
INSERT INTO `user_following_remark` VALUES (190, 1, 50, '青春', 1, b'0', '2022-12-06 06:20:23', '2022-12-06 06:20:23');

-- ----------------------------
-- Table structure for user_following_type
-- ----------------------------
DROP TABLE IF EXISTS `user_following_type`;
CREATE TABLE `user_following_type`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id',
  `platform_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联平台id',
  `type_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关注者所属的类型',
  `sort_no` tinyint(3) UNSIGNED NOT NULL DEFAULT 5 COMMENT '优先级由低到高：1-10，默认5',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_where_order`(`user_id`, `platform_id`, `sort_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关注分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_following_type
-- ----------------------------
INSERT INTO `user_following_type` VALUES (1, 1, 2, '媒体平台', 9, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:08');
INSERT INTO `user_following_type` VALUES (2, 1, 2, '参考资源', 8, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:10');
INSERT INTO `user_following_type` VALUES (3, 1, 2, '生活圈子', 7, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:12');
INSERT INTO `user_following_type` VALUES (4, 1, 2, '陌生圈子', 6, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:14');
INSERT INTO `user_following_type` VALUES (5, 1, 2, '明星名人', 5, b'0', '2021-05-15 08:58:27', '2021-05-15 08:58:27');
INSERT INTO `user_following_type` VALUES (6, 1, 3, '媒体平台', 8, b'0', '2021-05-15 08:58:27', '2022-02-22 07:29:15');
INSERT INTO `user_following_type` VALUES (7, 1, 3, '参考资源', 8, b'0', '2021-05-15 08:58:27', '2021-05-15 14:23:48');
INSERT INTO `user_following_type` VALUES (8, 1, 3, '生活圈子', 7, b'0', '2021-05-15 08:58:27', '2021-05-15 14:23:51');
INSERT INTO `user_following_type` VALUES (9, 1, 3, '陌生圈子', 6, b'0', '2021-05-15 08:58:27', '2021-05-15 14:23:54');
INSERT INTO `user_following_type` VALUES (10, 1, 3, '明星名人', 5, b'0', '2021-05-15 08:58:27', '2021-05-15 08:58:27');
INSERT INTO `user_following_type` VALUES (11, 1, 4, '媒体平台', 9, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:20');
INSERT INTO `user_following_type` VALUES (12, 1, 4, '参考资源', 8, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:22');
INSERT INTO `user_following_type` VALUES (13, 1, 4, '生活圈子', 7, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:23');
INSERT INTO `user_following_type` VALUES (14, 1, 4, '陌生圈子', 6, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:26');
INSERT INTO `user_following_type` VALUES (15, 1, 4, '明星名人', 5, b'0', '2021-05-15 08:58:27', '2021-05-15 08:58:27');
INSERT INTO `user_following_type` VALUES (16, 1, 5, '媒体平台', 9, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:28');
INSERT INTO `user_following_type` VALUES (17, 1, 5, '参考资源', 8, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:30');
INSERT INTO `user_following_type` VALUES (18, 1, 5, '生活圈子', 7, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:33');
INSERT INTO `user_following_type` VALUES (19, 1, 5, '陌生圈子', 6, b'0', '2021-05-15 08:58:27', '2021-05-15 14:24:35');
INSERT INTO `user_following_type` VALUES (20, 1, 5, '明星名人', 5, b'0', '2021-05-15 08:58:27', '2021-05-15 08:58:27');
INSERT INTO `user_following_type` VALUES (21, 1, 3, '速写练习', 8, b'1', '2022-02-22 07:27:32', '2022-11-27 14:40:30');
INSERT INTO `user_following_type` VALUES (22, 1, 2, 'Reaction', 5, b'0', '2024-05-16 00:40:51', '2024-05-16 00:40:51');

-- ----------------------------
-- Table structure for user_opinion
-- ----------------------------
DROP TABLE IF EXISTS `user_opinion`;
CREATE TABLE `user_opinion`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id',
  `platform_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联平台id',
  `opinion_type` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '观点对应类型，注意，这里 0 表示是平台的观点，默认类型不支持观点；其他-某一类型，默认0',
  `opinion_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '观点看法内容',
  `sort_no` tinyint(3) UNSIGNED NOT NULL DEFAULT 5 COMMENT '优先级由低到高：1-10，默认5',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_where_order`(`user_id`, `platform_id`, `opinion_type`, `sort_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户观点看法表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_opinion
-- ----------------------------
INSERT INTO `user_opinion` VALUES (1, 1, 1, 0, '>其中对各个平台的观点看法仅仅是个人观点，可能有些片面、偏激，或是认知错误，不理会便是了，做好自己的事已不易。', 10, b'0', '2021-04-17 04:03:49', '2021-05-15 02:08:36');
INSERT INTO `user_opinion` VALUES (2, 1, 1, 0, '时间和健康是你最宝贵的财富，工作、生活要做的就是将它们变现。', 5, b'0', '2021-04-17 04:03:49', '2021-11-29 16:48:45');
INSERT INTO `user_opinion` VALUES (3, 1, 1, 0, '**你看到的不一定是真的**，有可能是别人刻意想让你看到的；你想看到的大多数在萌芽阶段就被扼杀和谐了，或者被劣质内容冲刷掉了。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (4, 1, 1, 0, '少看一些跟自己不搭界的东西，偌大的世界，互联网拉近了人类的距离，但人的圈子和阶层背景却非常现实和残酷。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (5, 1, 1, 0, '不要废话连篇，言简意赅，长篇大论自己都看不下去，想法总结再多依然可能控制不住自己的行为，不努力拼搏，哪来的经济能力成家立业。', 7, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (6, 1, 2, 0, '众多up主在各自专长的领域发光发热，百家齐放、争奇斗艳。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (7, 1, 2, 0, 'b站目前土壤环境很不错，犹如蓬勃生机的热带雨林。自媒体恰饭前景不错，各行业都有较为优秀的人做up主，优质内容也相对较多，内容丰富反而更要控制逛b站的时间，否则，你的业余时间就被b站收割了，“时间就是金钱”，这句话不是没道理的。', 5, b'0', '2021-04-17 04:03:49', '2021-05-23 18:07:00');
INSERT INTO `user_opinion` VALUES (8, 1, 2, 0, '鱼龙混杂，内容水平参差不齐，好为人师的“野路子”、“半桶水”、“半吊子”很多，当然大神们曾经也是小白，不懂别装懂装逼，虚心学习成长，这样的小up会更有魅力和发展空间。', 5, b'0', '2021-04-17 04:03:49', '2022-03-01 08:36:50');
INSERT INTO `user_opinion` VALUES (9, 1, 2, 0, '优质的一手资料教程基本都收费，你能免费看到的几乎都是一两年甚至更久之前的“老掉牙”的资料了，免费内容要么质量不高，要么加入广告营销，有的甚至**精心设计各种套路**（话术、剧本暗流涌动）让你购买付费内容。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (10, 1, 2, 0, '警惕“广告叫卖式”的up，“林子大了什么鸟都有”，一部分店铺拍一些自己工作场景视频，内容展示“我很厉害，很牛逼，我的产品质量高服务品质有保障，有什么需求来找我就行了！”，说白了就是利用b站平台做生意的，b站之后可能发展商家入驻业务（B2C），还有内容搜索业务，“你感兴趣的都在b站”，会不会再出现一个“百度一下，你就SD”呢？', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (11, 1, 2, 0, '每个人工作生活的时间精力都有限，事分轻重缓急，==即使内容很优质且看着都有用，但并不一定是你当下最迫切需要的==。完成主线目标任务，中间每间隔一两个小时，可以适当休息十分钟左右，放松大脑，调整压抑情绪，但辅线就尽量不要是技术学习内容了，压力过大达不到放松效果，反而还影响主线任务。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (12, 1, 2, 0, '经常看到一些大UP主都说自己某某大学毕业，或者逆袭名校上岸，名校光环有效吗？有！但是否能走得长远，还得是活到老学到老，社会、人生是一辈子的大学。不盲目、不自卑、不好高骛远，结合自身实际条件，踏实走好每一步，高楼大厦固然宏伟壮阔，但空中楼阁容易崩塌！', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (13, 1, 3, 0, '知道为什么现在的年轻人大都喜欢玩游戏吗？看看这几年评分高的电影、电视剧有几部啊？资本娱乐一个圈，矮子里面挑高个，硬凑生给，资本投喂（💩），为了圈快钱，捧造流量，造假热度...完全对不上观众的口味，不看吧，娱乐消遣的内容全都一个样，看吧，真的是==浪费生命==。上头政策管制约束只是一个框架，框架内部同样可以精彩纷呈，波澜壮阔。也不排除有各家对手职业买黑，毕竟市场蛋糕就这么大，竞争十分激烈。但观众用户都不是傻子，我想要的你没有，还一个劲地生给硬塞，投喂垃圾内容，脱离用户需求，还想吸引、留住用户？', 1, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (14, 1, 3, 0, '明星、网红、广告商、企业机构营销宣传的重点场所。2020年06月中旬热搜整顿后，加入了ZF平台干预。以前只有渣浪和资本投喂娱乐八卦内容，现在多了一个党媒官媒投喂政治民生内容。鸡毛蒜皮、屁大点事都能显示“沸”。资本买热搜、撤热搜手段越来越熟练，不想让你看到的，一眨眼的功夫就能把“爆”、“沸”的热度撤得无影无踪。', 7, b'0', '2021-04-17 04:03:49', '2024-05-06 10:41:20');
INSERT INTO `user_opinion` VALUES (15, 1, 3, 0, '“成也明星、败也明星”，明星评论全是控评，发布内容不是广告宣传就是自拍照片，因为有各家粉丝和各种营销号天天盯着，明星言论举止越来越谨慎，明星粉丝的距离拉得越来越远！只要年长一点的粉丝都逐渐清醒过来，脱离虚幻追星娱乐圈，不再愿意被资本当作韭菜割了。最后导致粉丝群体低龄化，“小-中-高-大”学生群体占大多数。步入社会开始工作的年轻人都或多或少的遭受了社会上残忍的“毒打”，沦为“社畜”，自己的生活都过不好，谁还管你什么明星私生活？！', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (16, 1, 3, 0, '关于”娱乐至死“，**至死的不是娱乐，而是人自身**。生活娱乐到处都是，酒吧、KTV、会所、电影院、游乐场、游戏、直播等等，哪个不是娱乐？青少年、年轻人追星荒废学业事业，不能把所有问题都归咎于娱乐明星，玩游戏一样也耽误学业。拿刀杀人的不是刀，而是人。把刀具管制了，就不会出现事故了？问题的重点在于人本身，从关注人自身的问题分析和行动，才能从本质上有效解决问题。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (17, 1, 3, 0, '“娱乐至死”、“追星荒废学业”、“玩游戏耽误学业”，等系列问题，从人身上寻找根本原因，是什么原因导致他们不停地沉迷娱乐、追星、玩游戏？家庭不和睦、家人关爱缺失导致孩子心理和性格上出现缺爱、孤僻的问题；父母亲戚长辈有没有做好榜样，家长自己各种行为不检点，自己做不到的事情，却把所有的期望要求一股脑强压在孩子的身上，不去聆听孩子的心声，缺乏有效沟通，不尊重孩子的想法。我是父母，你是孩子，你就得听话。缺乏关爱、陪伴、聆听、交流、尊重，还强制要求孩子做这做那，结果必然导致矛盾越来越恶化！孩子得不到父母的关爱和陪伴，就想要从其他地方寻找，虚幻的网络世界里，无数缺乏家长指引的孩子沉迷其中，数月数年，难以自拔。直到经历重大沉痛打击，才开始唤醒他们的内心，逐渐走出虚拟世界，追寻现实生活中的美好。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (18, 1, 3, 0, '为什么头部的名人明星经常被黑？==肉被他们吃了，汤也被他们喝了，骨头剩菜被他们宠物吃了，让其他人吃什么啊==？！少占用些资源吧，把`一九/二八法则`降到`三七/四六/五五法则`，你手上的资源已经堆不下了，让几个给后辈年轻人不行吗？非要让绝大部分人眼睁睁地看着你”吃好喝好“、”盆满钵满“？', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (19, 1, 3, 9, '微博、抖音、豆瓣等公众平台发视频、照片、美食、游玩的活跃人群，绝大部分都是还在上学（高中/大学）的年轻人。他们心智、思想还一直停留在学校这个封闭的环境里，还没真正体会到社会上的人情冷暖，校园里的单纯美好，毕业步入社会却成了最致命的弱点，轻易相信他人，容易上当受骗；坚信努力肯定会有回报，却不知道会被公司压榨劳动力；渴望憧憬美好烂漫爱情，却被现实生活一次次打击。', 5, b'0', '2021-04-17 04:03:49', '2024-05-06 10:39:51');
INSERT INTO `user_opinion` VALUES (20, 1, 3, 9, '学业、工作、生活稳定，家人、朋友常聚，一切都按正常人生轨道前行的人，很少会在虚拟的社交平台耗费时间精力，以弥补现实的缺失。**现实中越缺什么，在社交平台就越渴望得到什么**。缺爱、缺朋友，导致内心孤独空虚，现实生活得不到，就会到虚拟的世界寻求可能的机会或心灵的慰藉。', 6, b'0', '2021-04-17 04:03:49', '2024-05-06 10:40:00');
INSERT INTO `user_opinion` VALUES (21, 1, 3, 10, '局宣、广告、自拍。', 5, b'0', '2021-04-17 04:03:49', '2021-05-15 14:35:19');
INSERT INTO `user_opinion` VALUES (22, 1, 3, 10, '更博缓慢，半个月才更新。', 5, b'0', '2021-04-17 04:03:49', '2021-05-15 14:35:21');
INSERT INTO `user_opinion` VALUES (23, 1, 3, 10, '没啥互动，粉丝只是`韭菜`和`工具人`？', 5, b'0', '2021-04-17 04:03:49', '2021-05-15 14:35:23');
INSERT INTO `user_opinion` VALUES (24, 1, 4, 0, '有些兴趣小组还是很不错的，相互交流学习；个别“垃圾场”的污染扩散程度也需要保持清醒头脑和独立思考，避免被“洗脑”带节奏。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (25, 1, 4, 0, '小组内的成员是因为相同的兴趣爱好聚在一起，“物以类聚，人以群分”，跟贴吧一样，用户量不是很大，用户群里也偏年轻。跟某音5+亿用户相比，db的百十万，就像是一包盐里的一小粒。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (26, 1, 4, 0, '兴趣小组、贴吧、论坛，基本上都是相似的一类人，就好比你走进了一个行为习惯“疯癫”的村落，你以为自己是正常的，他们也以为自己是正常的，如果你选择留下，肯定避免不了主动或者被动疯癫化（即使是假装）。要么你就离开，别对着他们指手画脚，在他们眼中你才是疯癫的异类。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (27, 1, 4, 0, '可能出现各种NC、YY、显微镜ju人、营销、引战、带节奏。优质有看点、知识、情感分享内容被大量劣质内容、虚假营销内容淹没。想看的看不到，不想看的到处都是，像暴风式洗脑一般，待久了很大可能就被同化了。一个正常人在一群不正常的人里面，正常人反而是不正常的。', 5, b'0', '2021-04-17 04:03:49', '2021-04-17 04:03:49');
INSERT INTO `user_opinion` VALUES (28, 1, 3, 9, '**以人为镜，可以明得失。** 偶尔看看就好，别一个劲地拿别人生活里的美好跟自己生活里的糟糕作比较，谁没事会跟王思聪比谁有钱，容易自卑、焦虑、抑郁，认清自身条件，适合自己的才是最好的。多关注自身，管好自己就行了。', 8, b'0', '2022-02-22 08:23:41', '2022-02-22 08:32:24');
INSERT INTO `user_opinion` VALUES (29, 1, 3, 9, '没有人不喜欢美好的人和事物，谁不想看美女/帅哥啊？Emmm... 但还是要克制，毕竟看多了确实会提高了**审美阈值**，脱离自身条件，好高骛远，甚至对自己的对象伴侣失去新鲜感，期望与现实的落差越大，最后产生的痛苦也越大。', 7, b'0', '2022-02-22 11:43:48', '2024-05-06 10:42:18');
INSERT INTO `user_opinion` VALUES (30, 1, 2, 22, '电影、电视剧、综艺看多了确实挺耗费时间（没有绝对的好与坏，看个人时间安排）！关键很多影视节目受GD审核限制，每年好看的高分剧集并不多，所以就当作下饭剧随缘看看，缓解工作生活压力。', 5, b'0', '2024-05-16 03:03:51', '2024-05-17 16:48:16');

-- ----------------------------
-- Table structure for user_platform
-- ----------------------------
DROP TABLE IF EXISTS `user_platform`;
CREATE TABLE `user_platform`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台名称',
  `name_en` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台英文名称',
  `main_page` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台主页',
  `platform_logo` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台logo',
  `platform_long_logo` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台长logo',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社交媒体平台表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_platform
-- ----------------------------
INSERT INTO `user_platform` VALUES (1, '我的', 'mypages', '/', '/images/platform-logo/mypages-logo.png', '/images/platform-logo/mypages-long-logo.png', b'0', '2021-01-02 08:49:04', '2021-05-15 02:25:07');
INSERT INTO `user_platform` VALUES (2, 'B站', 'bilibili', 'https://www.bilibili.com', '/images/platform-logo/bilibili-logo.png', '/images/platform-logo/bilibili-long-logo.png', b'0', '2021-01-02 08:49:04', '2021-01-02 08:49:04');
INSERT INTO `user_platform` VALUES (3, '微博', 'weibo', 'https://weibo.com', '/images/platform-logo/weibo-logo.png', '/images/platform-logo/weibo-long-logo.png', b'0', '2021-01-02 08:49:04', '2021-01-02 08:49:04');
INSERT INTO `user_platform` VALUES (4, '豆瓣', 'douban', 'https://www.douban.com', '/images/platform-logo/douban-logo.png', '/images/platform-logo/douban-long-logo-2.png', b'0', '2021-01-02 08:49:04', '2021-01-02 08:49:04');
INSERT INTO `user_platform` VALUES (5, '知乎', 'zhihu', 'https://www.zhihu.com', '/images/platform-logo/zhihu-logo.png', '/images/platform-logo/zhihu-long-logo.png', b'0', '2021-01-02 08:49:04', '2021-01-02 08:49:04');

-- ----------------------------
-- Table structure for user_platform_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_platform_relation`;
CREATE TABLE `user_platform_relation`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联用户id',
  `platform_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联平台id',
  `sort_no` tinyint(3) UNSIGNED NOT NULL DEFAULT 5 COMMENT '优先级由低到高：1-10，默认5',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '本条数据是否已删除，1-是；0-否，默认0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_where_order`(`user_id`, `sort_no`) USING BTREE,
  INDEX `idx_userId_platformId`(`user_id`, `platform_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与平台关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_platform_relation
-- ----------------------------
INSERT INTO `user_platform_relation` VALUES (1, 1, 1, 9, b'0', '2021-04-23 06:17:48', '2021-05-12 05:34:29');
INSERT INTO `user_platform_relation` VALUES (2, 1, 2, 8, b'0', '2021-04-23 06:17:48', '2021-05-12 05:34:32');
INSERT INTO `user_platform_relation` VALUES (3, 1, 3, 7, b'0', '2021-04-23 06:17:48', '2021-05-12 05:34:34');
INSERT INTO `user_platform_relation` VALUES (4, 1, 4, 6, b'0', '2021-04-23 06:17:48', '2021-05-12 05:34:37');
INSERT INTO `user_platform_relation` VALUES (5, 1, 5, 5, b'0', '2021-04-23 06:17:48', '2021-05-04 17:28:54');
INSERT INTO `user_platform_relation` VALUES (6, 2, 1, 7, b'0', '2021-05-12 05:38:52', '2021-05-12 05:38:52');
INSERT INTO `user_platform_relation` VALUES (7, 2, 2, 6, b'0', '2021-05-12 05:39:02', '2021-05-12 05:39:12');
INSERT INTO `user_platform_relation` VALUES (8, 2, 3, 5, b'0', '2021-05-12 05:39:09', '2021-05-12 05:39:09');

SET FOREIGN_KEY_CHECKS = 1;
