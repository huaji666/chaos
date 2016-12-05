/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_java
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : box

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-09-13 17:11:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for common_event_info
-- ----------------------------
DROP TABLE IF EXISTS `common_event_info`;
CREATE TABLE `common_event_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '事件标题',
  `type` int(4) unsigned DEFAULT '0' COMMENT '类型1用户',
  `description` varchar(50) DEFAULT NULL COMMENT '事件描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='事件信息表';
