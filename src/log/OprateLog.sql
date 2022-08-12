/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : shuangyu

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2022-03-15 17:33:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qr_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log` (
                                  `operate_id` bigint NOT NULL AUTO_INCREMENT,
                                  `menu` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `module` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
                                  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
                                  `operate_time` datetime DEFAULT NULL,
                                  `user_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `client` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
                                  `operate_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `os_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `device_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `net_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                  `status` tinyint(1) DEFAULT NULL,
                                  `error_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
                                  PRIMARY KEY (`operate_id`),
                                  KEY `idx_module` (`module`),
                                  KEY `idx_menu` (`menu`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
