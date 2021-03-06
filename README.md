[TOC]  

#### 前言
一年一度的双十一马上又要到了，今年没啥要买的东西，但是对电商的大流量并发处理突然很感兴趣，所以趁着双十一的契机，
想着自己实现一个秒杀系统，以期在动手实现的过程中能有所收获。初步想法是基于springboot实现一个单体的简单秒杀应用，
先实现基本的功能，然后通过压测工具测试，记录应用的各项指标数据信息，逐步进行优化。

#### 基本功能
1. 注册登录
2. 商品秒杀页面展示
3. 秒杀倒计时
4. 库存和订单处理
5. 秒杀结果展示

#### 项目搭建与技术选型
version1.0  
java8/springboot/mybatis/redis/mysql/thymeleaf

#### 开发步骤 
* 表结构设计（dao层）

  * 商品表（存储商品基本信息）
  * 秒杀详情表（存储秒杀基本信息）
  * 秒杀订单表（存储秒杀成功订单信息）
  * 用户表（存储用户注册登录相关信息）
  
* 业务架构设计（service）

  * 用户注册与登录服务
  * 登录后展示商品列表服务
  * 商品详情展示服务
  * 秒杀处理服务 成功生成订单，确认支付，扣减库存，失败返回秒杀结束
  
* 技术架构梳理

  * 前端 thymeleaf/springMvc/bootstrap/jQuery/layer.js
  * 前后端分离
  * redis缓存
  * kafka消息队列
  * spring管理bean，事务，整合mybatis
  * mybatis处理dao
  * mysql
  
* 编码 

  * 代码规范
  * 设计模式
  
* 压力测试

  * 工具：jMeter ab

* 项目部署

  * docker
  
* 秒杀架构相关优化点  
  * [优化记录](https://github.com/dunera/myseckill/blob/master/src/main/resources/optimize_note.md)
