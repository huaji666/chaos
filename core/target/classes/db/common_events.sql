/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_java
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : box

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-09-13 17:11:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for common_events
-- ----------------------------
DROP TABLE IF EXISTS `common_events`;
CREATE TABLE `common_events` (
  `event_id` int(4) DEFAULT NULL COMMENT '事件id',
  `create_date` varchar(20) DEFAULT NULL COMMENT '触发时间',
  `event_count` bigint(20) DEFAULT NULL COMMENT '总数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件信息列表';
