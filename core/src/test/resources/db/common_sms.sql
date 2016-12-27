/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_java
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : box

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-09-12 16:35:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for common_sms
-- ----------------------------
DROP TABLE IF EXISTS `common_sms`;
CREATE TABLE `common_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `status` int(4) unsigned DEFAULT '0' COMMENT '0待发送 1 发送成功。',
  `content` varchar(255) DEFAULT NULL COMMENT '内容长度255',
  `type` int(4) unsigned DEFAULT '0' COMMENT '1验证码 2重置码',
  `ip` varchar(20) DEFAULT NULL COMMENT '公网ip地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `create_date` varchar(20) DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='短信记录表';
