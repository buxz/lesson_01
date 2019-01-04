-- 安全角色信息表
DROP TABLE IF EXISTS `oauth_authority`;
CREATE TABLE `oauth_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO `oauth_authority` VALUES
  ('ROLE_ADMIN'),
  ('ROLE_USER');

-- AccessToken信息表
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- RefreshToken信息表
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 安全用户信息表
DROP TABLE IF EXISTS `oauth_user`;
CREATE TABLE `oauth_user` (
  `username` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `activated` tinyint(1) DEFAULT '0',
  `activationkey` varchar(50) DEFAULT NULL,
  `resetpasswordkey` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO `oauth_user` VALUES
('admin', 'admin@mail.me', 'b8f57d6d6ec0a60dfe2e20182d4615b12e321cad9e2979e0b9f81e0d6eda78ad9b6dcfe53e4e22d1', '1', null, null),
('user', 'user@mail.me', 'd6dfa9ff45e03b161e7f680f35d90d5ef51d243c2a8285aa7e11247bc2c92acde0c2bb626b1fac74', '1', null, null),
('rajith', 'rajith@abc.com', 'd6dfa9ff45e03b161e7f680f35d90d5ef51d243c2a8285aa7e11247bc2c92acde0c2bb626b1fac74', '1', null, null);

-- 用户角色关联表
DROP TABLE IF EXISTS `oauth_user_authority`;
CREATE TABLE `oauth_user_authority` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `user_authority_idx_1` (`username`,`authority`),
  KEY `authority` (`authority`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO `oauth_user_authority` VALUES
  ('admin', 'ROLE_ADMIN'),
  ('admin', 'ROLE_USER'),
  ('rajith', 'ROLE_USER'),
  ('user', 'ROLE_USER');
