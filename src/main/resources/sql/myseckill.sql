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

 Date: 09/11/2018 00:12:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL,
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `stock` int(20) DEFAULT NULL COMMENT '商品库存',
  `type` tinyint(10) DEFAULT NULL COMMENT '商品类型',
  `details` varchar(255) DEFAULT NULL COMMENT '商品详情',
  `image` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_active` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for secKill_order
-- ----------------------------
DROP TABLE IF EXISTS `secKill_order`;
CREATE TABLE `secKill_order` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `seckill_id` bigint(20) DEFAULT NULL COMMENT '秒杀详情id',
  `state` smallint(20) DEFAULT NULL COMMENT '订单状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for seckill_detail
-- ----------------------------
DROP TABLE IF EXISTS `seckill_detail`;
CREATE TABLE `seckill_detail` (
  `id` bigint(20) NOT NULL,
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `name` varchar(120) DEFAULT NULL COMMENT '秒杀商品名称',
  `seckill_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀商品价格',
  `stock` int(20) DEFAULT NULL COMMENT '秒杀商品库存',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  `is_active` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `phone_numbedr` varchar(20) DEFAULT NULL COMMENT '用户电话',
  `avater` int(255) DEFAULT NULL COMMENT '头像',
  `md5password` varchar(255) DEFAULT NULL COMMENT 'md5加密密码',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
