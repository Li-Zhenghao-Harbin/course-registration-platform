/*
 Navicat Premium Data Transfer

 Source Server         : alpha
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : course_registration

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 18/04/2023 23:34:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_info
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `start_time` datetime NOT NULL,
  `duration` int NOT NULL,
  `price` double(10, 0) NOT NULL,
  `sales` int NOT NULL,
  `tch_id` int NOT NULL,
  `tch_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_info
-- ----------------------------

-- ----------------------------
-- Table structure for course_stock
-- ----------------------------
DROP TABLE IF EXISTS `course_stock`;
CREATE TABLE `course_stock`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_stock
-- ----------------------------

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `stu_id` int NOT NULL,
  `tch_id` int NOT NULL,
  `course_id` int NOT NULL,
  `course_price` double(10, 0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int NOT NULL,
  `step` int NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', 0, 1);

-- ----------------------------
-- Table structure for stu_info
-- ----------------------------
DROP TABLE IF EXISTS `stu_info`;
CREATE TABLE `stu_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` tinyint NOT NULL COMMENT '0-female, 1-male',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_info
-- ----------------------------

-- ----------------------------
-- Table structure for stu_password
-- ----------------------------
DROP TABLE IF EXISTS `stu_password`;
CREATE TABLE `stu_password`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `encrypted_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `stu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_password
-- ----------------------------

-- ----------------------------
-- Table structure for stu_transaction
-- ----------------------------
DROP TABLE IF EXISTS `stu_transaction`;
CREATE TABLE `stu_transaction`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double(255, 0) NOT NULL,
  `stu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_transaction
-- ----------------------------

-- ----------------------------
-- Table structure for stu_wallet
-- ----------------------------
DROP TABLE IF EXISTS `stu_wallet`;
CREATE TABLE `stu_wallet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `balance` double(10, 0) NOT NULL,
  `stu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_wallet
-- ----------------------------

-- ----------------------------
-- Table structure for tch_info
-- ----------------------------
DROP TABLE IF EXISTS `tch_info`;
CREATE TABLE `tch_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` tinyint NOT NULL COMMENT '0-female, 1-male',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tch_info
-- ----------------------------

-- ----------------------------
-- Table structure for tch_password
-- ----------------------------
DROP TABLE IF EXISTS `tch_password`;
CREATE TABLE `tch_password`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `encrypted_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `tch_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tch_password
-- ----------------------------

-- ----------------------------
-- Table structure for tch_transaction
-- ----------------------------
DROP TABLE IF EXISTS `tch_transaction`;
CREATE TABLE `tch_transaction`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double(255, 0) NOT NULL,
  `tch_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tch_transaction
-- ----------------------------

-- ----------------------------
-- Table structure for tch_wallet
-- ----------------------------
DROP TABLE IF EXISTS `tch_wallet`;
CREATE TABLE `tch_wallet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `balance` double(255, 0) NOT NULL,
  `tch_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tch_wallet
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
