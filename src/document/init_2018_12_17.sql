/*
Navicat MySQL Data Transfer

Source Server         : w1_arithmetic
Source Server Version : 50635
Source Host           : 172.16.95.128:3306
Source Database       : arithmetic

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-12-17 11:27:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_area
-- ----------------------------
DROP TABLE IF EXISTS `base_area`;
CREATE TABLE `base_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '区域表',
  `name` varchar(60) NOT NULL COMMENT '区域名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父类id',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：未删除  1：已删除',
  `code` char(6) NOT NULL,
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_id` (`id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE,
  KEY `idx_is_del` (`is_del`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2829 DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of base_area
-- ----------------------------
INSERT INTO `base_area` VALUES ('24', '贵州省', '0', '0', '52', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('293', '贵阳市', '24', '0', '520100', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2819', '南明区', '293', '0', '520102', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2820', '云岩区', '293', '0', '520103', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2821', '花溪区', '293', '0', '520111', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2822', '乌当区', '293', '0', '520112', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2823', '白云区', '293', '0', '440111', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2824', '观山湖区', '293', '0', '520115', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2825', '开阳县', '293', '0', '520121', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2826', '息烽县', '293', '0', '520122', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2827', '修文县', '293', '0', '520123', '2017-07-14 09:04:55', '2017-07-14 09:04:55');
INSERT INTO `base_area` VALUES ('2828', '清镇市', '293', '0', '520181', '2017-07-14 09:04:55', '2017-07-14 09:04:55');

-- ----------------------------
-- Table structure for base_community
-- ----------------------------
DROP TABLE IF EXISTS `base_community`;
CREATE TABLE `base_community` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '小区名称',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `county` varchar(255) DEFAULT NULL COMMENT '县',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='小区基础数据表';

-- ----------------------------
-- Records of base_community
-- ----------------------------
INSERT INTO `base_community` VALUES ('1', '纺纱厂', '293', '2821', '0', '2018-12-17 10:32:11');
INSERT INTO `base_community` VALUES ('2', '3535厂小河住宅区', '293', '2821', '0', '2018-12-17 10:32:11');

-- ----------------------------
-- Table structure for base_school
-- ----------------------------
DROP TABLE IF EXISTS `base_school`;
CREATE TABLE `base_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '学校名称',
  `type` tinyint(1) DEFAULT NULL COMMENT '学校类型:1为公立,2为民办',
  `school_type` tinyint(1) DEFAULT NULL COMMENT '学校类型:1为小学,2为初中,3为小学初中一体',
  `city_id` int(11) DEFAULT NULL COMMENT '城市id',
  `county_id` int(11) DEFAULT NULL COMMENT '区县id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='学校基础数据表';

-- ----------------------------
-- Records of base_school
-- ----------------------------
INSERT INTO `base_school` VALUES ('1', '花溪区第一实验学校', '1', '1', '293', '2821', '2018-12-17 10:28:33');

-- ----------------------------
-- Table structure for base_school_community
-- ----------------------------
DROP TABLE IF EXISTS `base_school_community`;
CREATE TABLE `base_school_community` (
  `school_id` int(11) NOT NULL COMMENT '学校id',
  `community_id` int(11) NOT NULL COMMENT '小区id',
  `sort` varchar(255) DEFAULT NULL COMMENT '学校优先小区排名,1为优先度最高',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`school_id`,`community_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校对应小区表';

-- ----------------------------
-- Records of base_school_community
-- ----------------------------
INSERT INTO `base_school_community` VALUES ('1', '1', '1', '2018-12-17 10:33:44', '2018-12-17 10:33:46');
INSERT INTO `base_school_community` VALUES ('1', '2', '1', '2018-12-17 10:33:44', '2018-12-17 10:33:46');

-- ----------------------------
-- Table structure for par_guardian
-- ----------------------------
DROP TABLE IF EXISTS `par_guardian`;
CREATE TABLE `par_guardian` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(11) DEFAULT NULL COMMENT '监护人姓名',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别:0女 1男',
  `hkb_jhr_pic` varchar(255) DEFAULT NULL COMMENT '户口簿监护人页',
  `hkb_pic` varchar(255) DEFAULT NULL COMMENT '户口簿学生页',
  `idcard_pic` varchar(255) DEFAULT NULL COMMENT '监护人身份证照片正面',
  `idcard_pic_back` varchar(255) DEFAULT NULL COMMENT '监护人身份证背面',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生监护人信息表';

-- ----------------------------
-- Records of par_guardian
-- ----------------------------

-- ----------------------------
-- Table structure for par_user
-- ----------------------------
DROP TABLE IF EXISTS `par_user`;
CREATE TABLE `par_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(11) DEFAULT NULL COMMENT '姓名',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `mobile` varchar(11) DEFAULT NULL COMMENT '监护人手机号码',
  `password` varchar(255) DEFAULT NULL,
  `birth` varchar(255) DEFAULT NULL COMMENT '出生日期',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别:0女,1男',
  `grade` varchar(1) DEFAULT NULL COMMENT '年级',
  `guardian_id` int(11) DEFAULT NULL COMMENT '监护人id',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='学生基本信息表';

-- ----------------------------
-- Records of par_user
-- ----------------------------
INSERT INTO `par_user` VALUES ('1', null, '421123198912057273', '18121376785', 'e10adc3949ba59abbe56e057f20f883e', '1989-12-05', '1', '1', null, '2018-12-14 11:35:02');
INSERT INTO `par_user` VALUES ('2', null, '421123198912057273', '18121376785', 'e10adc3949ba59abbe56e057f20f883e', '1989-12-05', '1', '1', null, '2018-12-14 11:48:09');
INSERT INTO `par_user` VALUES ('3', null, '421123198912057273', '18121376785', 'e10adc3949ba59abbe56e057f20f883e', '1989-12-05', '1', '1', null, '2018-12-14 11:49:34');

-- ----------------------------
-- Table structure for par_user_address
-- ----------------------------
DROP TABLE IF EXISTS `par_user_address`;
CREATE TABLE `par_user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `has_house` tinyint(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `house_proof_pic` varchar(255) DEFAULT NULL,
  `live_proof_pic` varchar(255) DEFAULT NULL,
  `work_proof_pic` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生居住信息表';

-- ----------------------------
-- Records of par_user_address
-- ----------------------------

-- ----------------------------
-- Table structure for par_user_type
-- ----------------------------
DROP TABLE IF EXISTS `par_user_type`;
CREATE TABLE `par_user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL COMMENT '学生素质类型',
  `proof_pic` varchar(255) DEFAULT NULL COMMENT '证明材料图片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生素质表(普通生/特长生/残疾生/残障生)';

-- ----------------------------
-- Records of par_user_type
-- ----------------------------

-- ----------------------------
-- Table structure for sch_student_apply
-- ----------------------------
DROP TABLE IF EXISTS `sch_student_apply`;
CREATE TABLE `sch_student_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL COMMENT '学生id',
  `grade` varchar(255) DEFAULT NULL COMMENT '申请年级',
  `school_id` int(11) DEFAULT NULL COMMENT '申请学校id',
  `wish_type` tinyint(1) DEFAULT NULL COMMENT '志愿类型: 0系统分配, 1第一志愿, 2第二志愿, 3第三志愿',
  `check_man` varchar(255) DEFAULT NULL COMMENT '审核人',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '预录取状态: 0未审核,1审核通过, 2审核资料不全补充材料, 3审核存疑,上报教育局, 4审核不通过',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生家长申请表';

-- ----------------------------
-- Records of sch_student_apply
-- ----------------------------

-- ----------------------------
-- Table structure for sys_exception_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_exception_log`;
CREATE TABLE `sys_exception_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exception` longtext NOT NULL COMMENT '异常信息',
  `request_uri` varchar(100) NOT NULL COMMENT '请求uri',
  `source` enum('api','admin') NOT NULL COMMENT '来源（api,admin）',
  `package_name` varchar(60) NOT NULL COMMENT '包名',
  `request_para` varchar(255) DEFAULT NULL COMMENT '异常请求参数',
  `create_time` datetime NOT NULL,
  `resource` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=644 DEFAULT CHARSET=utf8 COMMENT='异常日志表';

-- ----------------------------
-- Records of sys_exception_log
-- ----------------------------
INSERT INTO `sys_exception_log` VALUES ('643', 'java.lang.RuntimeException: to do\r\n	at com.jslx.register.controller.parent.ParentHomeController.login(ParentHomeController.java:26)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:497)\r\n	at com.jfinal.aop.Invocation.invoke(Invocation.java:75)\r\n	at com.jslx.register.interceptor.InterceptorBase.intercept(InterceptorBase.java:98)\r\n	at com.jfinal.aop.Invocation.invoke(Invocation.java:69)\r\n	at com.jfinal.core.ActionHandler.handle(ActionHandler.java:81)\r\n	at com.jfinal.plugin.druid.DruidStatViewHandler.handle(DruidStatViewHandler.java:81)\r\n	at com.jfinal.ext.handler.ContextPathHandler.handle(ContextPathHandler.java:48)\r\n	at com.jfinal.core.JFinalFilter.doFilter(JFinalFilter.java:86)\r\n	at io.undertow.servlet.core.ManagedFilter.doFilter(ManagedFilter.java:61)\r\n	at io.undertow.servlet.handlers.FilterHandler$FilterChainImpl.doFilter(FilterHandler.java:131)\r\n	at io.undertow.servlet.handlers.FilterHandler.handleRequest(FilterHandler.java:84)\r\n	at io.undertow.servlet.handlers.security.ServletSecurityRoleHandler.handleRequest(ServletSecurityRoleHandler.java:62)\r\n	at io.undertow.servlet.handlers.ServletChain$1.handleRequest(ServletChain.java:68)\r\n	at io.undertow.servlet.handlers.ServletDispatchingHandler.handleRequest(ServletDispatchingHandler.java:36)\r\n	at io.undertow.servlet.handlers.security.SSLInformationAssociationHandler.handleRequest(SSLInformationAssociationHandler.java:132)\r\n	at io.undertow.servlet.handlers.security.ServletAuthenticationCallHandler.handleRequest(ServletAuthenticationCallHandler.java:57)\r\n	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)\r\n	at io.undertow.security.handlers.AbstractConfidentialityHandler.handleRequest(AbstractConfidentialityHandler.java:46)\r\n	at io.undertow.servlet.handlers.security.ServletConfidentialityConstraintHandler.handleRequest(ServletConfidentialityConstraintHandler.java:64)\r\n	at io.undertow.security.handlers.AuthenticationMechanismsHandler.handleRequest(AuthenticationMechanismsHandler.java:60)\r\n	at io.undertow.servlet.handlers.security.CachedAuthenticatedSessionHandler.handleRequest(CachedAuthenticatedSessionHandler.java:77)\r\n	at io.undertow.security.handlers.AbstractSecurityContextAssociationHandler.handleRequest(AbstractSecurityContextAssociationHandler.java:43)\r\n	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)\r\n	at io.undertow.server.handlers.PredicateHandler.handleRequest(PredicateHandler.java:43)\r\n	at io.undertow.servlet.handlers.ServletInitialHandler.handleFirstRequest(ServletInitialHandler.java:292)\r\n	at io.undertow.servlet.handlers.ServletInitialHandler.access$100(ServletInitialHandler.java:81)\r\n	at io.undertow.servlet.handlers.ServletInitialHandler$2.call(ServletInitialHandler.java:138)\r\n	at io.undertow.servlet.handlers.ServletInitialHandler$2.call(ServletInitialHandler.java:135)\r\n	at io.undertow.servlet.core.ServletRequestContextThreadSetupAction$1.call(ServletRequestContextThreadSetupAction.java:48)\r\n	at io.undertow.servlet.core.ContextClassLoaderSetupAction$1.call(ContextClassLoaderSetupAction.java:43)\r\n	at io.undertow.servlet.handlers.ServletInitialHandler.dispatchRequest(ServletInitialHandler.java:272)\r\n	at io.undertow.servlet.handlers.ServletInitialHandler.access$000(ServletInitialHandler.java:81)\r\n	at io.undertow.servlet.handlers.ServletInitialHandler$1.handleRequest(ServletInitialHandler.java:104)\r\n	at io.undertow.server.Connectors.executeRootHandler(Connectors.java:360)\r\n	at io.undertow.server.HttpServerExchange$1.run(HttpServerExchange.java:830)\r\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)\r\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)\r\n	at java.lang.Thread.run(Thread.java:745)\r\n\nJFinal action report -------- 2018-12-14 11:43:21 ------------------------------\nParameter   : code=  grade=1  idcard[]={,}  \n--------------------------------------------------------------------------------\n', 'http://localhost/parent/home/regist', 'api', 'parent/', null, '2018-12-14 11:43:22', 'http://localhost:8080/');

-- ----------------------------
-- Table structure for sys_verify_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_verify_code`;
CREATE TABLE `sys_verify_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` char(11) NOT NULL,
  `code` char(6) NOT NULL,
  `is_checked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否验证通过',
  `code_type` tinyint(4) NOT NULL COMMENT '验证码类型（0：注册登录验证码  1：更换手机号验证码/绑定手机号  2：找回密码验证码 ）',
  `source` enum('fd_student','fd_teacher') DEFAULT NULL COMMENT '来源',
  `status` int(11) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信验证表';

-- ----------------------------
-- Records of sys_verify_code
-- ----------------------------
