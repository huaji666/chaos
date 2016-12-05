/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_java
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-10-12 18:50:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for common_up_file
-- ----------------------------
DROP TABLE IF EXISTS `common_up_file`;
CREATE TABLE `common_up_file` (
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '唯一标示，每个文件唯一',
  `name` varchar(255) DEFAULT '' COMMENT '文件名称',
  `path` varchar(255) DEFAULT NULL COMMENT '文件分类',
  `status` smallint(4) unsigned DEFAULT '0' COMMENT '文件上传状态0~100，100为上传完成。',
  `file_type` varchar(100) DEFAULT '0' COMMENT '文件类型，0图片，1文档',
  `create_date` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`),
  UNIQUE KEY `up_file_code_uindex` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传表';
