/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_java
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : box

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-09-09 10:14:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for common_logs
-- ----------------------------
DROP TABLE IF EXISTS `common_logs`;
CREATE TABLE `common_logs` (
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_id` bigint(20) unsigned DEFAULT '0' COMMENT '用户id',
  `targetTable` varchar(255) DEFAULT NULL COMMENT '目标表',
  `type` int(11) unsigned DEFAULT '0' COMMENT '类型1添加，2删除，3修改',
  `description` varchar(120) DEFAULT NULL COMMENT '日志描述',
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip地址',
  `create_date` varchar(50) DEFAULT NULL COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志记录表';
