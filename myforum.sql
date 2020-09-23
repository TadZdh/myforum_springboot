/*
Navicat MySQL Data Transfer

Source Server         : ppp
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : myforum

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2020-09-23 19:40:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `category_created_date` datetime NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES ('1', 'java', '2020-07-10 17:21:00');
INSERT INTO `t_category` VALUES ('2', 'c++', '2020-07-10 17:52:16');
INSERT INTO `t_category` VALUES ('3', 'python', '2020-07-10 20:43:32');
INSERT INTO `t_category` VALUES ('4', 'php', '2020-07-25 18:34:46');
INSERT INTO `t_category` VALUES ('5', 'c语言', '2020-07-25 18:34:48');
INSERT INTO `t_category` VALUES ('6', 'c#', '2020-07-25 18:34:50');
INSERT INTO `t_category` VALUES ('7', 'javascript', '2020-07-25 18:34:53');
INSERT INTO `t_category` VALUES ('8', '物联网', '2020-07-25 18:34:55');
INSERT INTO `t_category` VALUES ('9', '大数据', '2020-07-25 18:34:57');
INSERT INTO `t_category` VALUES ('10', 'node.js', '2020-07-26 17:59:03');

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_body` varchar(255) NOT NULL,
  `comment_created_date` datetime NOT NULL,
  `comment_user` varchar(255) NOT NULL,
  `comment_post` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('49', '我是二楼', '2020-08-02 17:49:14', 'tom', '32');
INSERT INTO `t_comment` VALUES ('52', '33333', '2020-08-02 17:50:38', 'tom', '32');
INSERT INTO `t_comment` VALUES ('53', '求关注', '2020-08-02 18:55:47', 'jerry', '40');
INSERT INTO `t_comment` VALUES ('54', 'hello', '2020-08-02 18:56:04', 'jerry', '32');
INSERT INTO `t_comment` VALUES ('55', '欢迎新人hhh', '2020-08-02 18:59:33', 'tom', '32');

-- ----------------------------
-- Table structure for `t_follow`
-- ----------------------------
DROP TABLE IF EXISTS `t_follow`;
CREATE TABLE `t_follow` (
  `follow_id` int(11) NOT NULL AUTO_INCREMENT,
  `follow_from` varchar(255) NOT NULL,
  `follow_to` varchar(255) NOT NULL,
  `follow_date` datetime NOT NULL,
  PRIMARY KEY (`follow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_follow
-- ----------------------------
INSERT INTO `t_follow` VALUES ('18', 'jerry', 'tom', '2020-08-02 18:54:16');
INSERT INTO `t_follow` VALUES ('19', 'kang', 'tom', '2020-08-02 18:57:41');
INSERT INTO `t_follow` VALUES ('20', 'kang', 'jerry', '2020-08-02 18:58:31');

-- ----------------------------
-- Table structure for `t_post`
-- ----------------------------
DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_title` varchar(255) NOT NULL,
  `post_body` varchar(255) DEFAULT NULL,
  `post_created_date` datetime NOT NULL,
  `post_hits` bigint(20) NOT NULL DEFAULT '0',
  `post_user` varbinary(255) NOT NULL,
  `post_category` int(11) NOT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_post
-- ----------------------------
INSERT INTO `t_post` VALUES ('32', '欢迎来到论坛', '这个分类下可以谈论关于Java的内容', '2020-08-02 17:42:54', '48', 0x746F6D, '1');
INSERT INTO `t_post` VALUES ('39', 'ccc', 'cccccc', '2020-08-02 18:01:40', '1', 0x746F6D, '2');
INSERT INTO `t_post` VALUES ('40', '我是新人', 'hello', '2020-08-02 18:29:40', '18', 0x6A65727279, '1');
INSERT INTO `t_post` VALUES ('41', '我也来了', 'c++啊', '2020-08-02 18:56:40', '2', 0x6A65727279, '2');
INSERT INTO `t_post` VALUES ('42', '大家好啊', 'hhh', '2020-08-02 18:58:06', '13', 0x6B616E67, '1');
INSERT INTO `t_post` VALUES ('44', '喜欢python的盆友快来呀', '快来呀', '2020-08-03 18:36:34', '0', 0x746F6D, '3');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_portrait` varchar(255) DEFAULT NULL,
  `user_signature` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_created_date` datetime NOT NULL,
  `user_login_date` datetime DEFAULT NULL,
  `user_role` varchar(255) NOT NULL,
  `user_flag` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('jer', '$2a$10$MkfuVrAyToMJfgPLl4YkYuU./y9QTpGRF3fI4DF.Hk6Un.5SRapJi', null, null, '123@qq.com', '2020-09-23 19:24:12', '2020-09-23 19:24:33', 'user', '1');
INSERT INTO `t_user` VALUES ('jerry', '$2a$10$ElQ780MraAOnBZdHoDGBMuIkvHgtherYAfcap/bEhezUiHpkL.HoC', '/upload/157665644b944f71849397c688e8ffbb.jpg', 'hello', '1234@qq.com', '2020-07-10 17:53:55', '2020-09-23 19:29:29', 'user', '1');
INSERT INTO `t_user` VALUES ('kang', '$2a$10$xtvzCybE2IckLakZmIgw5us3OVLuKjSrTWK8t6VYUe9.0ZlLjeEGi', '', 'hello', '1234@qq.com', '2020-07-14 17:53:55', '2020-09-23 19:29:55', 'user', '1');
INSERT INTO `t_user` VALUES ('tom', '$2a$10$ssjBjfqWR6dFNwNV4r2vfOTAA4rLZKpGCCbnI5h.BGEVfhw8baDYS', '/upload/690887b2f70c430492f587409dd4f28e.jpg', 'hello world!', '1234@qq.com', '2020-07-10 17:38:24', '2020-09-23 19:32:11', 'admin', '1');
