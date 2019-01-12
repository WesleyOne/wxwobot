-- 创建库
CREATE DATABASE IF NOT EXISTS wxwobot DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE wxwobot;

-- Create syntax for TABLE 'wx_rob_config'
CREATE TABLE `wx_rob_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `unique_key` varchar(16) NOT NULL DEFAULT '' COMMENT '机器唯一码',
  `remark` varchar(64) NOT NULL DEFAULT '' COMMENT '备注（微信号）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `to_friend` bit(1) NOT NULL DEFAULT b'0' COMMENT '指定好友回复开关',
  `to_group` bit(1) NOT NULL DEFAULT b'0' COMMENT '指定群回复开关',
  `default_friend` bit(1) NOT NULL DEFAULT b'0' COMMENT '默认好友回复开关',
  `default_group` bit(1) NOT NULL DEFAULT b'0' COMMENT '默认群回复开关',
  `from_out` bit(1) NOT NULL DEFAULT b'0' COMMENT '对外接口开关',
  `enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '机器启用1禁用0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_wx_rob_config_uniquekey` (`unique_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'wx_rob_keyword'
CREATE TABLE `wx_rob_keyword` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `unique_key` varchar(16) NOT NULL DEFAULT '' COMMENT '机器唯一码',
  `key_data` varchar(64) NOT NULL DEFAULT '' COMMENT '关键词',
  `value_data` varchar(1024) NOT NULL DEFAULT '' COMMENT '回复内容',
  `type_data` varchar(64) NOT NULL DEFAULT '' COMMENT '回复类型',
  `nick_name` varchar(64) NOT NULL DEFAULT '' COMMENT '目标昵称',
  `to_group` bit(1) NOT NULL DEFAULT b'1' COMMENT '群1好友0',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用1禁用0',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_wx_rob_keyword_unikey` (`unique_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'wx_rob_relation'
CREATE TABLE `wx_rob_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `out_key` varchar(16) NOT NULL DEFAULT '' COMMENT '外接唯一码',
  `unique_key` varchar(16) NOT NULL DEFAULT '' COMMENT '机器唯一码',
  `nick_name` varchar(64) NOT NULL DEFAULT '' COMMENT '目标昵称',
  `to_group` bit(1) NOT NULL DEFAULT b'1' COMMENT '群1好友0',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用1禁用0',
  `white_list` varchar(255) NOT NULL DEFAULT '' COMMENT 'IP白名单',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_wx_rob_relation_okey` (`out_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;