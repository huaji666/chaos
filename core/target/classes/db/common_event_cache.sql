/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_java
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : myh

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-09-14 19:52:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for common_event_cache
-- ----------------------------
DROP TABLE IF EXISTS `common_event_cache`;
CREATE TABLE `common_event_cache` (
  `event_id` int(11) NOT NULL COMMENT '事件id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `unique_id` bigint(20) DEFAULT NULL COMMENT '其他唯一id',
  `create_date` varchar(20) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
