/*
 Navicat Premium Data Transfer

 Source Server         : 172.26.92.110
 Source Server Type    : MySQL
 Source Server Version : 50636
 Source Host           : 172.26.92.110:3306
 Source Schema         : order

 Target Server Type    : MySQL
 Target Server Version : 50636
 File Encoding         : 65001

 Date: 14/12/2021 14:58:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_order
-- ----------------------------
DROP TABLE IF EXISTS `s_order`;
CREATE TABLE `s_order`  (
  `ORDER_ID` bigint(19) NOT NULL COMMENT '订单id',
  `USER_ID` bigint(19) DEFAULT NULL COMMENT '用户id',
  `ORDER_AMOUT` double(6, 2) DEFAULT NULL COMMENT '订单金额',
  PRIMARY KEY (`ORDER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for s_points
-- ----------------------------
DROP TABLE IF EXISTS `s_points`;
CREATE TABLE `s_points`  (
  `POINTS_ID` bigint(19) NOT NULL COMMENT '积分id',
  `ORDER_ID` bigint(19) DEFAULT NULL COMMENT '订单id',
  `POINTS` int(11) DEFAULT NULL COMMENT '积分',
  PRIMARY KEY (`POINTS_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
