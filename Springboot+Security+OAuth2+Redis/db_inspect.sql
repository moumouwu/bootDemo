/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : db_inspect

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 28/12/2020 10:19:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `t_oauth_client`;
CREATE TABLE `t_oauth_client`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '必选，唯一客户端标识',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '必选，客户端所能访问的资源ID集合，多个资源时使用逗号分隔',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '必选，指定客户端的访问密钥',
  `user_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '自动生成，提供给客户端的密钥',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'all' COMMENT '必选，指定客户端申请的权限范围',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端支持的grant_type类型，可选值包括\r\nauthorization_code,password,refresh_token,implicit,client_credentials\r\n必选，多个参数使用逗号分隔',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '可选，客户端的重定向URI',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '可选，根据grant_type选择',
  `access_token_validity` bigint(255) DEFAULT NULL COMMENT '可选，设置用户是否自动Approval操作，默认为false，可选值包括true,false,\'read\',\'write\',\r\n该字段只使用于grant_type=‘authorization_code’的情况',
  `refresh_token_validity` bigint(255) DEFAULT NULL COMMENT '可选，客户端的refresh_token有效时间，单位：秒。\r\n不设定值则使用默认时间（60*60*24*30）30天',
  `additional_information` longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '可选，预留字段，在Oauth中没有实际使用，如若设置值，则必须是JSON格式的数据',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '可选，设置用户是否自动Approval操作，默认为false',
  `createTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '默认，创建时间，系统生成',
  `updateTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '默认，更新时间，系统生成',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUE_CLIENT_ID`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_oauth_client
-- ----------------------------
INSERT INTO `t_oauth_client` VALUES (1, 'client1', 'zhty-oauth2-resource', '$2a$10$CZ7RnVs8M6D/l50is7NKMOQtb7bBkMIiTZG077wctSOzkb5DDHS1G', '359603ed267631ce6b565300aceb8bf1', 'all', 'password,refresh_token,client_credentials,sms_code,mobile_password,wechat_miniapp', '', NULL, 1800, 3600, NULL, 'true', '2020-12-08 11:28:57', '2020-12-21 14:48:36');
INSERT INTO `t_oauth_client` VALUES (2, 'client2', 'zhty-oauth2-resource', '$2a$10$73amyK88t3pg3jwWo0UZpOQb/L0fin/2I57b1fMUr5med5PeSVM9i', 'b288d6dc98f0df722dec719c708ff451', 'all', 'password,refresh_token,client_credentials,sms_code,mobile_password,wechat_miniapp', '', NULL, 900, 1800, NULL, '', '2020-12-14 09:32:38', '2020-12-21 14:48:21');

-- ----------------------------
-- Table structure for t_oauth_role
-- ----------------------------
DROP TABLE IF EXISTS `t_oauth_role`;
CREATE TABLE `t_oauth_role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称，仅限于英文+数字+下划线组合，唯一',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `createTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，自动生成',
  `updateTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间。自动生成',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUE_NAME`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_oauth_role
-- ----------------------------
INSERT INTO `t_oauth_role` VALUES (1, 'admin', '管理员', '2020-12-21 15:58:33', '2020-12-21 15:58:33');
INSERT INTO `t_oauth_role` VALUES (2, 'user', '普通用户', '2020-12-21 15:58:53', '2020-12-21 15:58:53');

-- ----------------------------
-- Table structure for t_oauth_user
-- ----------------------------
DROP TABLE IF EXISTS `t_oauth_user`;
CREATE TABLE `t_oauth_user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID,主键',
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `wechatOpenId` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信用户openid',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名，唯一',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码，数据库保存的BCpassword加密后的字符串',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像，默认为空',
  `gender` int(3) DEFAULT 0 COMMENT '性别，0：未知，1：男，2：女',
  `uniqueId` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一编号',
  `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `locked` int(3) NOT NULL DEFAULT 0 COMMENT '账号是否锁定，0：未锁定，1：锁定',
  `expired` int(3) NOT NULL DEFAULT 0 COMMENT '账号是否过期，0：未过期，1：过期',
  `credExpired` int(3) NOT NULL DEFAULT 0 COMMENT '账号密码是否过期，0：未过期，1：过期',
  `enabled` int(3) NOT NULL DEFAULT 0 COMMENT '账号是否被禁用，0：未禁用，1：禁用',
  `createTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，自动生成',
  `updateTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间，自动生成',
  `department` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门',
  `position` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职务',
  `identityNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '家庭地址',
  `personPhoto` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人照片，默认为空，网址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUEID`(`uniqueId`) USING BTREE,
  UNIQUE INDEX `UNIQUE_USERNAME`(`username`) USING BTREE,
  UNIQUE INDEX `UNIQUE_PHONE`(`phone`) USING BTREE,
  UNIQUE INDEX `UNIQUE_WECHAT_OPENID`(`wechatOpenId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_oauth_user
-- ----------------------------
INSERT INTO `t_oauth_user` VALUES (1, '13255986610', 'openId1', 'admin', '$2a$10$T2VBpd9ZNNKJVp0LnjEwbOn.9LVjaUNECBLgt6Nd2eaa4o3naKSAG', NULL, 2, 'UN_01', '管理员', 0, 0, 0, 0, '2020-12-14 14:44:56', '2020-12-25 14:56:14', NULL, NULL, '510623199610165231', '厦门市湖里区兴隆路塘边社253号', NULL);
INSERT INTO `t_oauth_user` VALUES (2, '13708039649', NULL, 'root', '$2a$10$fRC13hdA09LYseTgMoFRQ.Q/s.LrQhsAOdss480voRfQ6bTcZnSDG', NULL, 0, 'UN_02', '橘猫', 0, 0, 0, 0, '2020-12-14 14:46:14', '2020-12-16 10:17:07', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_oauth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_oauth_user_role`;
CREATE TABLE `t_oauth_user_role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `userId` bigint(11) NOT NULL,
  `roleId` bigint(11) NOT NULL,
  `createTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_oauth_user_role
-- ----------------------------
INSERT INTO `t_oauth_user_role` VALUES (1, 1, 1, '2020-12-21 16:01:25', '2020-12-21 16:01:25');
INSERT INTO `t_oauth_user_role` VALUES (6, 2, 2, '2020-12-21 16:05:50', '2020-12-21 16:05:50');

SET FOREIGN_KEY_CHECKS = 1;
