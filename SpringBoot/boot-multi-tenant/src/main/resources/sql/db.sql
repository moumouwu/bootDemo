/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : db3

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 21/02/2022 10:24:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inspect_app_user
-- ----------------------------
DROP TABLE IF EXISTS `inspect_app_user`;
CREATE TABLE `inspect_app_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `app_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `app_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `enabled` int NOT NULL DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
  `mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `app_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `app_nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `app_gender` int NULL DEFAULT 0 COMMENT '性别(男0，女1)',
  `app_user_type` int NOT NULL DEFAULT 0 COMMENT '人员类型(0-巡检人员，1-维修人员)',
  `app_job_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职务',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'app-用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_app_user
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_content_logic
-- ----------------------------
DROP TABLE IF EXISTS `inspect_content_logic`;
CREATE TABLE `inspect_content_logic`  (
  `location_id` bigint NOT NULL COMMENT '点位id',
  `content_id` bigint NOT NULL COMMENT '内容id',
  `logic_id` bigint NOT NULL COMMENT '选项id',
  `default_value` int NULL DEFAULT 0 COMMENT '是否默认值（0-否，1-是）',
  PRIMARY KEY (`location_id`, `content_id`, `logic_id`) USING BTREE,
  INDEX `content_id`(`content_id`) USING BTREE,
  INDEX `logic_id`(`logic_id`) USING BTREE,
  CONSTRAINT `inspect_content_logic_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `inspect_location` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `inspect_content_logic_ibfk_2` FOREIGN KEY (`content_id`) REFERENCES `inspect_res_content` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `inspect_content_logic_ibfk_3` FOREIGN KEY (`logic_id`) REFERENCES `inspect_res_logic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点位-巡检内容-选项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_content_logic
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_day_task
-- ----------------------------
DROP TABLE IF EXISTS `inspect_day_task`;
CREATE TABLE `inspect_day_task`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `line_id` bigint NOT NULL COMMENT '线路id',
  `line_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线路名称',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划名称',
  `period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '巡检时间端',
  `status` int NOT NULL DEFAULT 0 COMMENT '巡检状态（0-未开始，1-巡检中，2-巡检完成，3-未巡检）',
  `inspect_user_id` bigint NULL DEFAULT NULL COMMENT '计划巡检人员id',
  `inspect_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划巡检人员名字',
  `inspect_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '巡检日期',
  `rep_user_id` bigint NULL DEFAULT NULL COMMENT '实际巡检人员id',
  `rep_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际巡检人员姓名',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始巡检时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束巡检时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '每日任务（巡检历史）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_day_task
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_event
-- ----------------------------
DROP TABLE IF EXISTS `inspect_event`;
CREATE TABLE `inspect_event`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态(0-待指派，1-已指派，2-正在维修，3-维修完成)',
  `event_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '事件类别',
  `problem_text` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题描述',
  `problem_img` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问题图片',
  `location_id` bigint NOT NULL COMMENT '点位id',
  `location_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '点位名称',
  `location_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置(经纬度)',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `report_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上报人员',
  `report_user_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上报人员电话',
  `handle_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人账号（管理员）',
  `repair_user_id` bigint NULL DEFAULT NULL COMMENT '维修人id',
  `repair_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维修人姓名',
  `repair_user_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维修人电话',
  `repair_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理结果（文字）',
  `repair_result_img` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理结果（图片）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上报时间',
  `repair_time` datetime NULL DEFAULT NULL COMMENT '维修时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检事件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_event
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_event_process
-- ----------------------------
DROP TABLE IF EXISTS `inspect_event_process`;
CREATE TABLE `inspect_event_process`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `event_id` bigint NOT NULL COMMENT '事件id',
  `desperation` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `handle_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检事件 -- 过程' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_event_process
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_event_type
-- ----------------------------
DROP TABLE IF EXISTS `inspect_event_type`;
CREATE TABLE `inspect_event_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类别名称',
  `desperation` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 是否启用（0-否，1-是）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检事件分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_event_type
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_line
-- ----------------------------
DROP TABLE IF EXISTS `inspect_line`;
CREATE TABLE `inspect_line`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `line_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '线路名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检线路' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_line
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_line_location
-- ----------------------------
DROP TABLE IF EXISTS `inspect_line_location`;
CREATE TABLE `inspect_line_location`  (
  `line_id` bigint NOT NULL COMMENT '路线id',
  `location_id` bigint NOT NULL COMMENT '点位id',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`line_id`, `location_id`) USING BTREE,
  INDEX `location_id`(`location_id`) USING BTREE,
  CONSTRAINT `inspect_line_location_ibfk_1` FOREIGN KEY (`line_id`) REFERENCES `inspect_line` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `inspect_line_location_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `inspect_location` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检线路-点位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_line_location
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_location
-- ----------------------------
DROP TABLE IF EXISTS `inspect_location`;
CREATE TABLE `inspect_location`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `location_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '位置(xxx市xxx区)',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置(经纬度)',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检点位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_location
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_location_content
-- ----------------------------
DROP TABLE IF EXISTS `inspect_location_content`;
CREATE TABLE `inspect_location_content`  (
  `location_id` bigint NOT NULL COMMENT '点位id',
  `content_id` bigint NOT NULL COMMENT '项目id',
  PRIMARY KEY (`location_id`, `content_id`) USING BTREE,
  INDEX `content_id`(`content_id`) USING BTREE,
  CONSTRAINT `inspect_location_content_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `inspect_location` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `inspect_location_content_ibfk_2` FOREIGN KEY (`content_id`) REFERENCES `inspect_res_content` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检点位-巡检内容关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_location_content
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_menu
-- ----------------------------
DROP TABLE IF EXISTS `inspect_menu`;
CREATE TABLE `inspect_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '父类菜单id',
  `menu_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `is_leaf` int NOT NULL DEFAULT 0 COMMENT '是否为菜单(0-否,1-是)',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '前端路由',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由名称',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由component',
  `redirect` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由redirect',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由icon',
  `icon_color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路由icon-颜色',
  `order_num` int NOT NULL DEFAULT 1 COMMENT '排序(默认1)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6205 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单-权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_menu
-- ----------------------------
INSERT INTO `inspect_menu` VALUES (1000, NULL, '系统设置', 1, '/auth', 'AUTH', 'Layout', '/auth/UserList', NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1010, 1000, '账号管理', 1, 'InspectUser', 'InspectUser', '/auth/UserList', NULL, NULL, NULL, 10);
INSERT INTO `inspect_menu` VALUES (1011, 1010, '账号列表', 0, '/inspect/user/list', '', NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1012, 1010, '账号详情', 0, '/inspect/user/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1013, 1010, '添加/修改账号', 0, '/inspect/user', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1015, 1010, '账号删除', 0, '/inspect/user/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1016, 1010, '重置密码', 0, '/inspect/user/resetPwd', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1017, 1010, '账号状态修改', 0, '/inspect/user/changeStatus', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1018, 1010, '查看账号角色', 0, '/inspect/user/hasRoles', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1019, 1010, '账号角色修改', 0, '/inspect/user/updateRoles', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1030, 1000, '角色管理', 1, 'InspectRole', 'InspectRole', '/auth/RoleList', NULL, NULL, NULL, 11);
INSERT INTO `inspect_menu` VALUES (1031, 1030, '角色列表', 0, '/inspect/role/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1032, 1030, '角色详情', 0, '/inspect/role/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1033, 1030, '添加/修改角色', 0, '/inspect/role', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1035, 1030, '角色删除', 0, '/inspect/role/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1036, 1030, '查看角色权限', 0, '/inspect/role/hasPermission', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1037, 1030, '角色权限修改', 0, '/inspect/role/updatePermission', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1040, 1000, '登录日志', 1, 'LoginLog', 'LoginLog', '/auth/LoginLoglist', NULL, NULL, NULL, 12);
INSERT INTO `inspect_menu` VALUES (1041, 1040, '登录日志列表', 0, '/inspect/loginLog/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1042, 1040, '登录日志详情', 0, '/inspect/loginLog/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1043, 1040, '登录日志删除', 0, '/inspect/loginLog/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1044, 1040, '清除登录日志', 0, '/inspect/loginLog/clean', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1045, 1040, '导出登录日志', 0, '/inspect/loginLog/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1050, 1000, '操作日志', 1, 'OperLog', 'OperLog', '/auth/OperLogList', NULL, NULL, NULL, 13);
INSERT INTO `inspect_menu` VALUES (1051, 1050, '操作日志列表', 0, '/inspect/operLog/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1052, 1050, '操作日志详情', 0, '/inspect/operLog/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1053, 1050, '操作日志删除', 0, '/inspect/operLog/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1054, 1050, '清除操作日志', 0, '/inspect/operLog/clean', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (1055, 1050, '导出操作日志', 0, '/inspect/operLog/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2000, NULL, '会员信息', 1, '/tenant', 'TENANT', 'Layout', '/tenant/Info', NULL, NULL, 2);
INSERT INTO `inspect_menu` VALUES (2010, 2000, '基本信息', 1, 'Info', 'Info', '/tenant/Info', NULL, NULL, NULL, 21);
INSERT INTO `inspect_menu` VALUES (2020, 2000, '订单管理', 1, 'OrderList', 'OrderList', '/tenant/OrderList', NULL, NULL, NULL, 22);
INSERT INTO `inspect_menu` VALUES (2021, 2020, '订单列表', 0, '/inspect/order/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2022, 2020, '订单详情', 0, '/inspect/order/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2023, 2020, '创建订单', 0, '/inspect/order', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2024, 2020, '删除订单', 0, '/inspect/order/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2030, 2000, '退款管理', 1, 'RefundList', 'RefundList', '/tenant/RefundList', NULL, NULL, NULL, 23);
INSERT INTO `inspect_menu` VALUES (2031, 2030, '退款列表', 0, '/inspect/refund/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2032, 2030, '退款详情', 0, '/inspect/refund/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2033, 2030, '申请退款', 0, '/inspect/refund', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2034, 2030, '删除退款记录', 0, '/inspect/refund/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2040, 2000, '开票管理', 1, 'InvoiceList', 'InvoiceList', '/tenant/InvoiceList', NULL, NULL, NULL, 24);
INSERT INTO `inspect_menu` VALUES (2041, 2040, '开票列表', 0, '/inspect/invoice/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2042, 2040, '开票详情', 0, '/inspect/invoice/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2043, 2040, '申请开票', 0, '/inspect/invoice', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (2044, 2040, '删除开票记录', 0, '/inspect/invoice/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3000, NULL, '资源管理', 1, '/resource', 'RESOURCE', 'Layout', '/resource/AppUserList', NULL, NULL, 3);
INSERT INTO `inspect_menu` VALUES (3010, 3000, '人员管理', 1, 'AppUserList', 'AppUserList', '/resource/AppUserList', NULL, NULL, NULL, 31);
INSERT INTO `inspect_menu` VALUES (3011, 3010, '人员列表', 0, '/inspect/appUser/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3012, 3010, '人员详情', 0, '/inspect/appUser/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3013, 3010, '添加/修改人员', 0, '/inspect/appUser', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3015, 3010, '删除人员', 0, '/inspect/appUser/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3016, 3010, '导出人员', 0, '/inspect/appUser/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3020, 3000, '巡检内容管理', 1, 'ContentList', 'ContentList', '/resource/ContentList', NULL, NULL, NULL, 32);
INSERT INTO `inspect_menu` VALUES (3021, 3020, '内容列表', 0, '/inspect/content/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3022, 3020, '内容详情', 0, '/inspect/content/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3023, 3020, '添加/修改内容', 0, '/inspect/content', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3025, 3020, '删除内容', 0, '/inspect/content/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3026, 3020, '导出内容', 0, '/inspect/content/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3031, 3020, '逻辑项列表', 0, '/inspect/logic/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3032, 3020, '逻辑项详情', 0, '/inspect/logic/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3033, 3020, '添加/修改逻辑项', 0, '/inspect/logic', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3035, 3020, '删除逻辑项', 0, '/inspect/logic/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3036, 3020, '导出逻辑项', 0, '/inspect/logic/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3041, 3020, '单位列表', 0, '/inspect/unit/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3042, 3020, '单位详情', 0, '/inspect/unit/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3043, 3020, '添加单位', 0, '/inspect/unit/add', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3044, 3020, '修改单位', 0, '/inspect/unit/update', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3045, 3020, '删除单位', 0, '/inspect/unit/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3046, 3020, '导出单位', 0, '/inspect/unit/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3050, 3000, '点位管理', 1, 'LocationList', 'LocationList', '/resource/LocationList', NULL, NULL, NULL, 33);
INSERT INTO `inspect_menu` VALUES (3051, 3050, '点位列表', 0, '/inspect/location/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3052, 3050, '点位详情', 0, '/inspect/location/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3053, 3050, '添加/修改点位', 0, '/inspect/location', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3055, 3050, '删除点位', 0, '/inspect/location/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3056, 3050, '导出点位', 0, '/inspect/location/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3057, 3050, '查询点位中的项目', 0, '/inspect/location/contentList', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3058, 3050, '添加项目到点位中', 0, '/inspect/location/addContent', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3059, 3050, '删除项目中的点位', 0, '/inspect/location/removeContent', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3060, 3050, '查询项目中的逻辑项', 0, '/inspect/location/logicList', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3061, 3050, '添加项目中的逻辑项', 0, '/inspect/location/addLogic', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3062, 3050, '删除项目中的点位', 0, '/inspect/location/removeLogic', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3070, 3000, '线路管理', 1, 'LineList', 'LineList', '/resource/LineList', NULL, NULL, NULL, 34);
INSERT INTO `inspect_menu` VALUES (3071, 3070, '线路列表', 0, '/inspect/line/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3072, 3070, '线路详情', 0, '/inspect/line/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3073, 3070, '添加/修改线路', 0, '/inspect/line', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3075, 3070, '删除线路', 0, '/inspect/line/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3076, 3070, '添加点位到线路中', 0, '/inspect/line/addLocation', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3077, 3070, '删除线路中的点位', 0, '/inspect/line/removeLocation', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3078, 3070, '上移/下移点位', 0, '/inspect/line/move', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3080, 3000, '计划管理', 1, 'PlanList', 'PlanList', '/resource/PlanList', NULL, NULL, NULL, 34);
INSERT INTO `inspect_menu` VALUES (3081, 3080, '计划列表', 0, '/inspect/plan/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3082, 3080, '计划详情', 0, '/inspect/plan/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3083, 3080, '添加/修改计划', 0, '/inspect/plan/add', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3085, 3080, '删除计划', 0, '/inspect/plan/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (3086, 3080, '修改计划状态', 0, '/inspect/plan/changeStatus', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (4000, NULL, '每日任务', 1, 'TaskList', 'TaskList', '/inspect/TaskList', NULL, NULL, NULL, 4);
INSERT INTO `inspect_menu` VALUES (4001, 4000, '查询每日任务', 0, '/inspect/task/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (4002, 4000, '查询详情', 0, '/inspect/task/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (5000, NULL, '报表统计', 1, '/report', 'REPORT', 'Layout', '/report/lineList', NULL, NULL, 5);
INSERT INTO `inspect_menu` VALUES (5100, 5000, '线路统计', 1, 'lineList', 'lineList', '/report/lineList', NULL, NULL, NULL, 51);
INSERT INTO `inspect_menu` VALUES (5101, 5100, '巡检记录', 0, '/inspect/record/listLine', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (5200, 5000, '点位统计', 1, 'listLocation', 'listLocation', '/report/listLocation', NULL, NULL, NULL, 52);
INSERT INTO `inspect_menu` VALUES (5201, 5200, '点位记录', 0, '/inspect/record/listLocation', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6000, NULL, '事件管理', 1, '/event', 'EVENT', 'Layout', '/event/typeList', NULL, NULL, 6);
INSERT INTO `inspect_menu` VALUES (6100, 6000, '事件类别管理', 1, 'TypeList', 'TypeList', '/event/TypeList', NULL, NULL, NULL, 61);
INSERT INTO `inspect_menu` VALUES (6101, 6100, '分类列表', 0, '/inspect/eventType/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6102, 6100, '分类详情', 0, '/inspect/eventType/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6103, 6100, '添加/修改分类', 0, '/inspect/eventType', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6104, 6100, '删除分类', 0, '/inspect/eventType/batchDel', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6105, 6100, '修改分类状态', 0, '/inspect/eventType/changeStatus', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6106, 6100, '导出分类', 0, '/inspect/eventType/export', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6200, 6000, '事件管理', 1, 'EventList', 'EventList', '/event/EventList', NULL, NULL, NULL, 62);
INSERT INTO `inspect_menu` VALUES (6201, 6200, '事件列表', 0, '/inspect/event/list', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6202, 6200, '事件详情', 0, '/inspect/event/detail', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6203, 6200, '添加/修改分类', 0, '/inspect/event/assign', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `inspect_menu` VALUES (6204, 6200, '删除事件', 0, '/inspect/eventType/batchDel', NULL, NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for inspect_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `inspect_operation_log`;
CREATE TABLE `inspect_operation_log`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作模块',
  `business_type` int NOT NULL COMMENT '业务类型（0其它 1新增 2修改 3删除 4授权 5导出 6导入）',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '方法名',
  `request_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `operator_type` int NOT NULL DEFAULT 1 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求地址',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人员',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `oper_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP地址',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回参数',
  `status` int NULL DEFAULT 1 COMMENT '操作状态（1正常 0异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_plan
-- ----------------------------
DROP TABLE IF EXISTS `inspect_plan`;
CREATE TABLE `inspect_plan`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `line_id` bigint NOT NULL COMMENT '线路id',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划名称',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态（0-不启用，1-启用）',
  `start_time` datetime NOT NULL COMMENT '计划开始时间',
  `plan_type` int NULL DEFAULT NULL COMMENT '周期模式（0-每天，1-按周，2-每N天）',
  `week_period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周期模式-每周 （哪几天）',
  `interval_num` int NULL DEFAULT NULL COMMENT '周期模式-每n天',
  `period_start_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '巡检时间开始时间',
  `period_end_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '巡检时间结束时间',
  `inspect_user_id` bigint NOT NULL COMMENT '巡检人员id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检计划' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_plan
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_record
-- ----------------------------
DROP TABLE IF EXISTS `inspect_record`;
CREATE TABLE `inspect_record`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `task_id` bigint NOT NULL COMMENT '任务id',
  `location_id` bigint NOT NULL COMMENT '点位id',
  `location_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '点位名称',
  `real_user_id` bigint NULL DEFAULT NULL COMMENT '实际巡检人id',
  `real_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际巡检人',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '巡检内容',
  `record_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目检测结果',
  `record_imgs` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  `check_time` datetime NULL DEFAULT NULL COMMENT '确认巡检日期',
  `status` int NOT NULL DEFAULT 0 COMMENT '是否完成巡检(0-否，1-是) 默认0',
  `error_status` int NOT NULL DEFAULT 0 COMMENT '是否出现问题(0-否，1-是) 默认0',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_record
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_record_position
-- ----------------------------
DROP TABLE IF EXISTS `inspect_record_position`;
CREATE TABLE `inspect_record_position`  (
  `id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `task_id` bigint NOT NULL COMMENT '任务id',
  `longitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检记录 -- 记录实时位置信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_record_position
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_res_content
-- ----------------------------
DROP TABLE IF EXISTS `inspect_res_content`;
CREATE TABLE `inspect_res_content`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `content_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容名称',
  `content_type` int NOT NULL DEFAULT 0 COMMENT '巡检内容类型（0-逻辑型，1-数字型）',
  `content_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检内容' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_res_content
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_res_logic
-- ----------------------------
DROP TABLE IF EXISTS `inspect_res_logic`;
CREATE TABLE `inspect_res_logic`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `logic_option` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '逻辑选项',
  `logic_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检内容选项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_res_logic
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_res_unit
-- ----------------------------
DROP TABLE IF EXISTS `inspect_res_unit`;
CREATE TABLE `inspect_res_unit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `unit_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位名称',
  `unit_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '巡检内容单位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_res_unit
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_role
-- ----------------------------
DROP TABLE IF EXISTS `inspect_role`;
CREATE TABLE `inspect_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name`) USING BTREE,
  UNIQUE INDEX `role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_role
-- ----------------------------
INSERT INTO `inspect_role` VALUES (1, '超级管理员', 'admin', '所有权限', '2022-01-04 09:59:28', '2022-01-04 10:25:23');

-- ----------------------------
-- Table structure for inspect_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `inspect_role_menu`;
CREATE TABLE `inspect_role_menu`  (
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`menu_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_role_menu
-- ----------------------------
INSERT INTO `inspect_role_menu` VALUES (1000, 1);
INSERT INTO `inspect_role_menu` VALUES (1010, 1);
INSERT INTO `inspect_role_menu` VALUES (1011, 1);
INSERT INTO `inspect_role_menu` VALUES (1012, 1);
INSERT INTO `inspect_role_menu` VALUES (1013, 1);
INSERT INTO `inspect_role_menu` VALUES (1015, 1);
INSERT INTO `inspect_role_menu` VALUES (1016, 1);
INSERT INTO `inspect_role_menu` VALUES (1017, 1);
INSERT INTO `inspect_role_menu` VALUES (1018, 1);
INSERT INTO `inspect_role_menu` VALUES (1019, 1);
INSERT INTO `inspect_role_menu` VALUES (1030, 1);
INSERT INTO `inspect_role_menu` VALUES (1031, 1);
INSERT INTO `inspect_role_menu` VALUES (1032, 1);
INSERT INTO `inspect_role_menu` VALUES (1033, 1);
INSERT INTO `inspect_role_menu` VALUES (1035, 1);
INSERT INTO `inspect_role_menu` VALUES (1036, 1);
INSERT INTO `inspect_role_menu` VALUES (1037, 1);
INSERT INTO `inspect_role_menu` VALUES (1040, 1);
INSERT INTO `inspect_role_menu` VALUES (1041, 1);
INSERT INTO `inspect_role_menu` VALUES (1042, 1);
INSERT INTO `inspect_role_menu` VALUES (1043, 1);
INSERT INTO `inspect_role_menu` VALUES (1044, 1);
INSERT INTO `inspect_role_menu` VALUES (1045, 1);
INSERT INTO `inspect_role_menu` VALUES (1050, 1);
INSERT INTO `inspect_role_menu` VALUES (1051, 1);
INSERT INTO `inspect_role_menu` VALUES (1052, 1);
INSERT INTO `inspect_role_menu` VALUES (1053, 1);
INSERT INTO `inspect_role_menu` VALUES (1054, 1);
INSERT INTO `inspect_role_menu` VALUES (1055, 1);
INSERT INTO `inspect_role_menu` VALUES (2000, 1);
INSERT INTO `inspect_role_menu` VALUES (2010, 1);
INSERT INTO `inspect_role_menu` VALUES (2020, 1);
INSERT INTO `inspect_role_menu` VALUES (2021, 1);
INSERT INTO `inspect_role_menu` VALUES (2022, 1);
INSERT INTO `inspect_role_menu` VALUES (2023, 1);
INSERT INTO `inspect_role_menu` VALUES (2024, 1);
INSERT INTO `inspect_role_menu` VALUES (2030, 1);
INSERT INTO `inspect_role_menu` VALUES (2031, 1);
INSERT INTO `inspect_role_menu` VALUES (2032, 1);
INSERT INTO `inspect_role_menu` VALUES (2033, 1);
INSERT INTO `inspect_role_menu` VALUES (2034, 1);
INSERT INTO `inspect_role_menu` VALUES (2040, 1);
INSERT INTO `inspect_role_menu` VALUES (2041, 1);
INSERT INTO `inspect_role_menu` VALUES (2042, 1);
INSERT INTO `inspect_role_menu` VALUES (2043, 1);
INSERT INTO `inspect_role_menu` VALUES (2044, 1);
INSERT INTO `inspect_role_menu` VALUES (3000, 1);
INSERT INTO `inspect_role_menu` VALUES (3010, 1);
INSERT INTO `inspect_role_menu` VALUES (3011, 1);
INSERT INTO `inspect_role_menu` VALUES (3012, 1);
INSERT INTO `inspect_role_menu` VALUES (3013, 1);
INSERT INTO `inspect_role_menu` VALUES (3015, 1);
INSERT INTO `inspect_role_menu` VALUES (3016, 1);
INSERT INTO `inspect_role_menu` VALUES (3020, 1);
INSERT INTO `inspect_role_menu` VALUES (3021, 1);
INSERT INTO `inspect_role_menu` VALUES (3022, 1);
INSERT INTO `inspect_role_menu` VALUES (3023, 1);
INSERT INTO `inspect_role_menu` VALUES (3025, 1);
INSERT INTO `inspect_role_menu` VALUES (3026, 1);
INSERT INTO `inspect_role_menu` VALUES (3031, 1);
INSERT INTO `inspect_role_menu` VALUES (3032, 1);
INSERT INTO `inspect_role_menu` VALUES (3033, 1);
INSERT INTO `inspect_role_menu` VALUES (3035, 1);
INSERT INTO `inspect_role_menu` VALUES (3036, 1);
INSERT INTO `inspect_role_menu` VALUES (3041, 1);
INSERT INTO `inspect_role_menu` VALUES (3042, 1);
INSERT INTO `inspect_role_menu` VALUES (3043, 1);
INSERT INTO `inspect_role_menu` VALUES (3044, 1);
INSERT INTO `inspect_role_menu` VALUES (3045, 1);
INSERT INTO `inspect_role_menu` VALUES (3046, 1);
INSERT INTO `inspect_role_menu` VALUES (3050, 1);
INSERT INTO `inspect_role_menu` VALUES (3051, 1);
INSERT INTO `inspect_role_menu` VALUES (3052, 1);
INSERT INTO `inspect_role_menu` VALUES (3053, 1);
INSERT INTO `inspect_role_menu` VALUES (3055, 1);
INSERT INTO `inspect_role_menu` VALUES (3056, 1);
INSERT INTO `inspect_role_menu` VALUES (3057, 1);
INSERT INTO `inspect_role_menu` VALUES (3058, 1);
INSERT INTO `inspect_role_menu` VALUES (3059, 1);
INSERT INTO `inspect_role_menu` VALUES (3060, 1);
INSERT INTO `inspect_role_menu` VALUES (3061, 1);
INSERT INTO `inspect_role_menu` VALUES (3062, 1);
INSERT INTO `inspect_role_menu` VALUES (3070, 1);
INSERT INTO `inspect_role_menu` VALUES (3071, 1);
INSERT INTO `inspect_role_menu` VALUES (3072, 1);
INSERT INTO `inspect_role_menu` VALUES (3073, 1);
INSERT INTO `inspect_role_menu` VALUES (3075, 1);
INSERT INTO `inspect_role_menu` VALUES (3076, 1);
INSERT INTO `inspect_role_menu` VALUES (3077, 1);
INSERT INTO `inspect_role_menu` VALUES (3078, 1);
INSERT INTO `inspect_role_menu` VALUES (3080, 1);
INSERT INTO `inspect_role_menu` VALUES (3081, 1);
INSERT INTO `inspect_role_menu` VALUES (3082, 1);
INSERT INTO `inspect_role_menu` VALUES (3083, 1);
INSERT INTO `inspect_role_menu` VALUES (3085, 1);
INSERT INTO `inspect_role_menu` VALUES (3086, 1);
INSERT INTO `inspect_role_menu` VALUES (4000, 1);
INSERT INTO `inspect_role_menu` VALUES (4001, 1);
INSERT INTO `inspect_role_menu` VALUES (4002, 1);
INSERT INTO `inspect_role_menu` VALUES (5000, 1);
INSERT INTO `inspect_role_menu` VALUES (5100, 1);
INSERT INTO `inspect_role_menu` VALUES (5101, 1);
INSERT INTO `inspect_role_menu` VALUES (5200, 1);
INSERT INTO `inspect_role_menu` VALUES (5201, 1);
INSERT INTO `inspect_role_menu` VALUES (6000, 1);
INSERT INTO `inspect_role_menu` VALUES (6100, 1);
INSERT INTO `inspect_role_menu` VALUES (6101, 1);
INSERT INTO `inspect_role_menu` VALUES (6102, 1);
INSERT INTO `inspect_role_menu` VALUES (6103, 1);
INSERT INTO `inspect_role_menu` VALUES (6104, 1);
INSERT INTO `inspect_role_menu` VALUES (6105, 1);
INSERT INTO `inspect_role_menu` VALUES (6106, 1);
INSERT INTO `inspect_role_menu` VALUES (6200, 1);
INSERT INTO `inspect_role_menu` VALUES (6201, 1);
INSERT INTO `inspect_role_menu` VALUES (6202, 1);
INSERT INTO `inspect_role_menu` VALUES (6203, 1);
INSERT INTO `inspect_role_menu` VALUES (6204, 1);

-- ----------------------------
-- Table structure for inspect_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `inspect_sys_log`;
CREATE TABLE `inspect_sys_log`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `log_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录信息',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区',
  `browser_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `status` int NULL DEFAULT NULL COMMENT '操作系统',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录状态（0-失败,1-成功）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for inspect_user
-- ----------------------------
DROP TABLE IF EXISTS `inspect_user`;
CREATE TABLE `inspect_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `gender` int NOT NULL DEFAULT 0 COMMENT '性别(男0，女1)',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门',
  `job_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职务',
  `enabled` int NOT NULL DEFAULT 1 COMMENT '状态（0-禁用，1-正常）',
  `account_non_expired` int NOT NULL DEFAULT 1 COMMENT '账号是否过期(0-过期，1-正常)',
  `account_non_locked` int NOT NULL DEFAULT 1 COMMENT '账号被锁定(0-锁定，1-正常)',
  `credentials_non_expired` int NOT NULL DEFAULT 1 COMMENT '账号的凭证(密码)过期(0-过期，1-正常)',
  `last_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_user
-- ----------------------------
INSERT INTO `inspect_user` VALUES (1, 'admin', '$2a$10$e6myw87Wy28d4LHo4TuPlO5xwJZ.rV17R/lktYLglOKMAfkJVuBlG', ' ', NULL, 0, NULL, NULL, NULL, NULL, 1, 1, 1, 1, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ----------------------------
-- Table structure for inspect_user_role
-- ----------------------------
DROP TABLE IF EXISTS `inspect_user_role`;
CREATE TABLE `inspect_user_role`  (
  `user_id` bigint NOT NULL COMMENT '账号id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inspect_user_role
-- ----------------------------
INSERT INTO `inspect_user_role` VALUES (1, 1);

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端密匙',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端申请的权限范围',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端支持的grant_type',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重定向URI',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  `access_token_validity` int NULL DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
  `additional_information` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户是否自动Approval操作'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
