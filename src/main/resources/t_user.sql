# 用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `t_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `t_age` int(10) DEFAULT NULL COMMENT '年龄',
  `t_address` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `t_pwd` varchar(100) DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`t_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT into t_user(t_name,t_age,t_address,t_pwd)
VALUES ('张三','12','张庄','123456'),
('李四','13','李大楼','123456'),
('赵大','14','赵庄','123456');


# 角色表
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `r_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `r_role` varchar(30) DEFAULT NULL COMMENT '标识',
  PRIMARY KEY (`r_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
INSERT into t_role values(1,'超级管理员','admin'),
  (2,'普通用户','user');

# 用户角色关联表
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `ur_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `ur_role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ur_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
INSERT into t_user_role values(1,1,1),
  (2,1,2);


# 日志信息表
DROP TABLE IF EXISTS `t_logger_infos`;
CREATE TABLE `t_logger_infos` (
  `ali_id` int(11) NOT NULL AUTO_INCREMENT,
  `ali_client_ip` varchar(30) DEFAULT NULL COMMENT '客户端请求IP地址',
  `ali_uri` varchar(100) DEFAULT NULL COMMENT '日志请求地址',
  `ali_type` varchar(50) DEFAULT NULL COMMENT '终端请求方式,普通请求,ajax请求',
  `ali_method` varchar(10) DEFAULT NULL COMMENT '请求方式method,post,get等',
  `ali_param_data` longtext COMMENT '请求参数内容,json',
  `ali_session_id` varchar(100) DEFAULT NULL COMMENT '请求接口唯一session标识',
  `ali_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '请求时间',
  `ali_returm_time` varchar(50) DEFAULT NULL COMMENT '接口返回时间',
  `ali_return_data` longtext COMMENT '接口返回数据json',
  `ali_http_status_code` varchar(10) DEFAULT NULL COMMENT '请求时httpStatusCode代码，如：200,400,404等',
  `ali_time_consuming` int(8) DEFAULT '0' COMMENT '请求耗时（毫秒单位）',
  PRIMARY KEY (`ali_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106119 DEFAULT CHARSET=utf8 COMMENT='请求日志信息表';

# 商品信息表
DROP TABLE IF EXISTS `t_good_infos`;
CREATE TABLE `t_good_infos` (
  `tg_id` int(11) NOT NULL AUTO_INCREMENT,
  `tg_title` varchar(50) DEFAULT NULL COMMENT '标题',
  `tg_price` DECIMAL(8,2) DEFAULT NULL COMMENT '价格',
  `tg_unit` VARCHAR(20) DEFAULT NULL COMMENT '单位',
  `tg_order` varchar(255) DEFAULT NULL COMMENT '订单信息',
  `tg_type_id` INT(11)  DEFAULT NULL COMMENT '商品类型ID',
  PRIMARY KEY (`tg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106119 DEFAULT CHARSET=utf8 COMMENT='商品信息表';
INSERT INTO `t_good_infos` VALUES (2,'油菜','12.6', '斤', '2','1');

# 商品分类信息表
DROP TABLE IF EXISTS `t_good_types`;
CREATE TABLE `t_good_types` (
  `tgt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tgt_name` varchar(30) DEFAULT NULL COMMENT '标题',
  `tgt_is_show` CHAR(1) DEFAULT NULL COMMENT '价格',
  `tgt_order` INT(2) DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`tgt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106119 DEFAULT CHARSET=utf8 COMMENT='商品分类信息表';
INSERT INTO `t_good_types` VALUES (1, '绿色蔬菜',1,1);
