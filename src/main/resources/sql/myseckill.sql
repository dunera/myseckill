/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : localhost:3306
 Source Schema         : myseckill

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 17/11/2018 23:50:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `stock` int(20) DEFAULT NULL COMMENT '商品库存',
  `type` tinyint(10) DEFAULT NULL COMMENT '商品类型',
  `details` varchar(255) DEFAULT NULL COMMENT '商品详情',
  `image` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_active` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES (1, 'ipad', 2000.00, 5000, 1, '苹果小平板', '/img/ipad.png', '2018-11-17 21:25:18', 1);
INSERT INTO `goods` VALUES (2, '苹果', 3000.00, 20, 1, '红富士', '/img/apple.png', '2018-11-17 21:25:12', 1);
INSERT INTO `goods` VALUES (3, '🍎', 5000.00, 10, 1, '大苹果', '/img/apple1.png', '2018-11-17 16:20:50', 1);
COMMIT;

-- ----------------------------
-- Table structure for secKill_order
-- ----------------------------
DROP TABLE IF EXISTS `secKill_order`;
CREATE TABLE `secKill_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `seckill_id` bigint(20) DEFAULT NULL COMMENT '秒杀详情id',
  `state` smallint(20) NOT NULL DEFAULT '0' COMMENT '订单状态 0-初始化 1-下单成功 2-下单失败 3-订单取消 ',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_sek` (`user_id`,`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of secKill_order
-- ----------------------------
BEGIN;
INSERT INTO `secKill_order` VALUES (17, 6, 1, 1, 1, '2018-11-17 08:56:20');
INSERT INTO `secKill_order` VALUES (26, 6, 3, 3, 1, '2018-11-17 09:25:33');
COMMIT;

-- ----------------------------
-- Table structure for seckill_detail
-- ----------------------------
DROP TABLE IF EXISTS `seckill_detail`;
CREATE TABLE `seckill_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `name` varchar(120) DEFAULT NULL COMMENT '秒杀商品名称',
  `seckill_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀商品价格',
  `stock` int(20) DEFAULT NULL COMMENT '秒杀商品库存',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  `is_active` tinyint(1) DEFAULT NULL COMMENT '是否有效 0-无效，1-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of seckill_detail
-- ----------------------------
BEGIN;
INSERT INTO `seckill_detail` VALUES (1, 1, 'ipad', 100.00, 1000, '2018-11-17 15:54:31', '2018-11-17 16:28:51', '2018-11-17 16:28:58', 1);
INSERT INTO `seckill_detail` VALUES (2, 2, '苹果', 200.00, 10, '2018-11-16 16:45:29', '2018-11-16 16:35:25', '2018-11-16 16:35:29', 1);
INSERT INTO `seckill_detail` VALUES (3, 3, '🍎', 300.00, 20, '2018-11-16 16:45:57', '2018-11-17 16:45:47', '2018-11-22 16:45:52', 1);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `phone_number` varchar(20) DEFAULT NULL COMMENT '用户电话',
  `avater` int(255) DEFAULT NULL COMMENT '头像',
  `md5password` varchar(255) DEFAULT NULL COMMENT 'md5加密密码',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (6, '123', NULL, 0, '202cb962ac59075b964b07152d234b70');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
