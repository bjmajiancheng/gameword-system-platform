-- gw表结构
CREATE DATABASE IF NOT EXISTS gameword_platform DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use gameword_platform;

-- 开屏广告管理
DROP TABLE IF EXISTS d_advertisement;
CREATE TABLE d_advertisement(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `platform` int(11) NOT NULL DEFAULT '0' COMMENT '平台（全部、安卓、IOS）',
   `image` varchar(100) NOT NULL DEFAULT '' COMMENT '开屏图片',
   `link` varchar(100) NOT NULL DEFAULT '' COMMENT '链接',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '是否生效',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开屏广告管理';



-- 后台国家管理
DROP TABLE IF EXISTS d_country;
CREATE TABLE d_country(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `country_cn_name` varchar(100) NOT NULL DEFAULT '' COMMENT '国家中文名称',
   `country_en_name` varchar(100) NOT NULL DEFAULT '' COMMENT '国家英文名称',
   `code` varchar(100) NOT NULL DEFAULT '' COMMENT '国家代码值',
   `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
   `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
   `update_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台国家管理';


-- 后台城市管理
DROP TABLE IF EXISTS d_city;
CREATE TABLE d_city(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `country_id` int(11) NOT NULL DEFAULT '0' COMMENT '国家ID',
   `city_cn` varchar(100) NOT NULL DEFAULT '' COMMENT '城市中文名称',
   `city_en` varchar(100) NOT NULL DEFAULT '' COMMENT '城市英文名称',
   `code` varchar(100) NOT NULL DEFAULT '' COMMENT '城市代码值',
   `contact` varchar(100) NOT NULL DEFAULT '' COMMENT '联系人名称',
   `email` varchar(100) NOT NULL DEFAULT '' COMMENT 'email',
   `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
   `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
   `update_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台城市管理';


-- C端用户信息
DROP TABLE IF EXISTS d_user;
CREATE TABLE d_user(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `country_id` int(11) NOT NULL DEFAULT '0' COMMENT '国籍',
   `city_id` int(11) NOT NULL DEFAULT '0' COMMENT '所在城市',
   `last_name` varchar(100) NOT NULL DEFAULT '' COMMENT '姓',
   `first_name` varchar(100) NOT NULL DEFAULT '' COMMENT '名',
   `birthday` varchar(100) NOT NULL DEFAULT '' COMMENT '出生日期',
   `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
   `nickname` varchar(100) NOT NULL DEFAULT '' COMMENT '昵称',
   `agency_name` varchar(100) NOT NULL DEFAULT '' COMMENT '机构/学校名称',
   `desc` varchar(100) NOT NULL DEFAULT '' COMMENT '个人简介（一段文字）',
   `email` varchar(100) NOT NULL DEFAULT '' COMMENT 'email',
   `mobile_phone` varchar(100) NOT NULL DEFAULT '' COMMENT '手机号',
   `head_image` varchar(100) NOT NULL DEFAULT '' COMMENT '头像（照片）',
   `invite_code` varchar(100) NOT NULL DEFAULT '' COMMENT '邀请码',
   `balance` decimal(12, 2) NOT NULL DEFAULT '0.0' COMMENT '余额',
   `register_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT'注册时间',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 0：正常，1：禁用',
   `user_type` int(11) NOT NULL DEFAULT '0' COMMENT '用户类型；0：学生，1：商务会员',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='C端用户信息';


-- 后台用户信息表
DROP TABLE IF EXISTS d_system_user;
CREATE TABLE d_system_user(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
   `display_name` varchar(100) NOT NULL DEFAULT '' COMMENT '昵称',
   `head_image` varchar(100) NOT NULL DEFAULT '' COMMENT '头像图片',
   `contact_phone` varchar(100) NOT NULL DEFAULT '' COMMENT '手机号',
   `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
   `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
   `country_id` int(11) NOT NULL DEFAULT '0' COMMENT '国籍',
   `city_id` int(11) NOT NULL DEFAULT '0' COMMENT '城市',
   `angency_cn_name` varchar(100) NOT NULL DEFAULT '' COMMENT '机构中文名称',
   `angency_en_name` varchar(100) NOT NULL DEFAULT '' COMMENT '机构英文名称',
   `language` int(11) NOT NULL DEFAULT '0' COMMENT '语种',
   `register_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT'注册时间',
   `user_type` int(11) NOT NULL DEFAULT '0' COMMENT '角色（全部/管理员/供应商/教师）',
   `password` varchar(200) NOT NULL DEFAULT '' COMMENT '密码',
   `is_online` int(11) NOT NULL DEFAULT '0' COMMENT '是否在线',
   `enabled` smallint(1) NOT NULL DEFAULT '1' COMMENT '状态，0=冻结，1=正常',
   `last_login_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后登录IP',
   `last_login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后登录时间',
   `account_non_locked` smallint(1) NOT NULL DEFAULT '1' COMMENT '未锁定状态，1=正常，0=锁定',
   `account_non_expired` smallint(1) NOT NULL DEFAULT '1' COMMENT '账号过期状态，1=正常，0=过期',
   `credentials_non_expired` smallint(1) NOT NULL DEFAULT '1' COMMENT '密码失效状态：1：未失效 0：已失效',
   `last_password_reset` timestamp NULL DEFAULT NULL COMMENT '上次密码重置时间',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户信息表';

-- 系统密码映射
DROP TABLE IF EXISTS d_user_pass_mapping;
create table `d_user_pass_mapping` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码明文',
   `password_encode` varchar(512) NOT NULL DEFAULT '' COMMENT '加密密码',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统密码映射表';

-- 系统菜单
DROP TABLE IF EXISTS d_system_function;
create table d_system_function (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
   `function_name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限名称',
   `display` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否显示到菜单栏',
   `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否启用（0：不启用，1：启用）',
   `action` varchar(100) NOT NULL DEFAULT '' COMMENT '请求链接',
   `icon` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单图标',
   `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- 系统角色
DROP TABLE IF EXISTS d_system_role;
create table d_system_role (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
   `status` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否启用（0：不启用，1：启用）',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- 系统角色菜单关联表
DROP TABLE IF EXISTS d_system_role_function;
create table d_system_role_function (
   `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单ID',
   `function_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色菜单关联表';

-- 系统用户角色关联表
DROP TABLE IF EXISTS d_system_user_role;
create table d_system_user_role (
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '系统用户ID',
   `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '系统角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色关联表';

-- 充值信息
DROP TABLE IF EXISTS d_payment;
CREATE TABLE d_payment(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `pay_type` int(11) NOT NULL DEFAULT '0' COMMENT '支付类型 1、微信；2、支付宝；3、PayPal',
   `pay_money` decimal(12, 2) NOT NULL DEFAULT '0.0' COMMENT '充值金额',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '支付用户ID',
   `pay_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT'支付时间',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值信息';

-- 交流联系
DROP TABLE IF EXISTS d_contact;
CREATE TABLE d_contact(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `title` varchar(100) NOT NULL DEFAULT '' COMMENT '标题',
   `content` varchar(100) NOT NULL DEFAULT '' COMMENT '内容',
   `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '本人姓名',
   `mobile_phone` varchar(100) NOT NULL DEFAULT '' COMMENT '电话',
   `email` varchar(100) NOT NULL DEFAULT '' COMMENT 'email',
   `other` varchar(100) NOT NULL DEFAULT '' COMMENT '其他',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '提交人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交流联系';

-- 聊天室

DROP TABLE IF EXISTS d_chat;
CREATE TABLE d_chat(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `type` int(11) NOT NULL DEFAULT '0' COMMENT '聊天室类型 1、公共聊天室；2、商务聊天室',
   `name` varchar(100) NOT NULL DEFAULT '' COMMENT '聊天室名称',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天室';

-- 聊天室成员


DROP TABLE IF EXISTS d_chat_member;
CREATE TABLE d_chat_member(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `chat_id` int(11) NOT NULL DEFAULT '0' COMMENT '聊天室ID',
   `member_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '成员ID',
   `user_role` int(11) NOT NULL DEFAULT '0' COMMENT '成员角色 1：管理员 2：普通成员',
   `is_disable` int(11) NOT NULL DEFAULT '0' COMMENT '是否禁言 0：未禁言，1：已禁言',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天室成员';

-- 聊天室内容

DROP TABLE IF EXISTS d_chat_content;
CREATE TABLE d_chat_content(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `chat_id` int(11) NOT NULL DEFAULT '0' COMMENT '聊天室ID',
   `content` varchar(100) NOT NULL DEFAULT '' COMMENT '发言内容',
   `is_voice` int(11) NOT NULL DEFAULT '0' COMMENT '是否是语音',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '发言人id',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天室内容';


-- 用户聊天记录

DROP TABLE IF EXISTS d_user_chat;
CREATE TABLE d_user_chat(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '消息发送人',
   `friend_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '消息接收人',
   `content` varchar(100) NOT NULL DEFAULT '' COMMENT '消息内容',
   `is_voice` int(11) NOT NULL DEFAULT '0' COMMENT '是否是语音',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户聊天记录';


-- 好友表

DROP TABLE IF EXISTS d_friend;
CREATE TABLE d_friend(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
   `friend_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '好友ID',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友表';


-- 后台提示语管理

DROP TABLE IF EXISTS d_hint;
CREATE TABLE d_hint(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `hint_code` varchar(100) NOT NULL DEFAULT '' COMMENT '提示语编号',
   `hint_cn` varchar(100) NOT NULL DEFAULT '' COMMENT '提示语中文',
   `hint_en` varchar(100) NOT NULL DEFAULT '' COMMENT '提示语英文',
   `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
   `update_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台提示语管理';


-- 菜单链接管理

DROP TABLE IF EXISTS d_menu;
CREATE TABLE d_menu(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `menu_type` int(11) NOT NULL DEFAULT '0' COMMENT '菜单类型 1：秘籍城堡 2：游学研学城',
   `menu_name` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单名称',
   `link` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单链接',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
   `update_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单链接管理';


-- 后台用户通知内容
DROP TABLE IF EXISTS d_user_notice;
CREATE TABLE d_user_notice(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `send_user_id`  int(11) NOT NULL DEFAULT '0' COMMENT '发送人用户ID',
   `user_id`  int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
   `mobile_phone` varchar(100) NOT NULL DEFAULT '' COMMENT '手机号',
   `content` varchar(100) NOT NULL DEFAULT '' COMMENT '内容',
   `type` int(11) NOT NULL DEFAULT '0' COMMENT '短信类型；1：系统短信，2:用户自定义',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态；0：未发送，1：发送成功，2：发送失败',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户通知内容';


-- 前台用户统计表

DROP TABLE IF EXISTS d_user_static;
CREATE TABLE d_user_static(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `head_image` varchar(100) NOT NULL DEFAULT '' COMMENT '头像图标',
   `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '姓名',
   `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
   `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
   `country_id` int(11) NOT NULL DEFAULT '0' COMMENT '国籍',
   `city_id` int(11) NOT NULL DEFAULT '0' COMMENT '城市',
   `agency_name` varchar(100) NOT NULL DEFAULT '' COMMENT '机构/学校名称',
   `register_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT'注册时间',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（正常、禁用）',
   `is_disable` int(11) NOT NULL DEFAULT '0' COMMENT '是否禁言 0：否，1：是',
   `pay_money` decimal(12, 2) NOT NULL DEFAULT '0.0' COMMENT '总支付金额',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='前台用户统计表';


-- 城市驿站管理
DROP TABLE IF EXISTS d_station;
CREATE TABLE d_station(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `country_id` int(11) NOT NULL DEFAULT '0' COMMENT '国家ID',
   `city_id` int(11) NOT NULL DEFAULT '0' COMMENT '城市ID',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `cn_city_info` longtext COMMENT '中文城市信息',
   `en_city_info` longtext COMMENT '英文城市信息',
   `cn_business_cooperation` longtext COMMENT '中文商务合作',
   `en_business_cooperation` longtext COMMENT '英文商务合作',
   `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
   `update_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
   `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市驿站管理';


-- 城市驿站明细信息
DROP TABLE IF EXISTS d_station_detail;
CREATE TABLE d_station_detail(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `station_id` int(11) NOT NULL DEFAULT '0' COMMENT '驿站ID',
   `language` int(11) NOT NULL DEFAULT '0' COMMENT '语种 1：中文 2：英文',
   `city_title` varchar(100) NOT NULL DEFAULT '' COMMENT '城市标题',
   `topic_img` varchar(100) NOT NULL DEFAULT '' COMMENT '主题图',
   `thumb_img` varchar(100) NOT NULL DEFAULT '' COMMENT '缩略图',
   `description` text COMMENT '简介',
   `business_desc` text COMMENT '工商业',
   `travel_desc` text COMMENT '旅游',
   `education_desc` text COMMENT '教育',
   `medical_desc` text COMMENT '医疗',
   `specialty_desc` text COMMENT '特产',
   `holiday_desc` text COMMENT '节庆',
   `culture_desc` text COMMENT '文化',
   `food_desc` text COMMENT '美食',
   `sport_desc` text COMMENT '体育',
   `climate_desc` text COMMENT '气候',
   `celebrity_desc` text COMMENT '名人',
   `tips_desc` text COMMENT '锦囊',
   `city_info` text COMMENT '城市信息',
   `business_cooperation` text COMMENT '商务合作',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市驿站明细信息';


-- 价格信息
DROP TABLE IF EXISTS d_price_conf;
CREATE TABLE d_price_conf(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `price_cn` decimal(12, 2) NOT NULL DEFAULT '0.0' COMMENT '中文学员单价（人民币元/分钟）',
   `price_en` decimal(12, 2) NOT NULL DEFAULT '0.0' COMMENT '英文学员单价（美元/分钟）',
   `update_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='价格信息';


-- 价格维护记录

DROP TABLE IF EXISTS d_price_conf_record;
CREATE TABLE d_price_conf_record(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户',
   `before_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '修改前备注',
   `after_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '修改后备注',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='价格维护记录';


-- 敏感词过滤
DROP TABLE IF EXISTS d_sensitive_word;
CREATE TABLE d_sensitive_word(
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `language` int(11) NOT NULL DEFAULT '0' COMMENT '语种',
   `sensitive_word` varchar(100) NOT NULL DEFAULT '' COMMENT '敏感词',
   `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '维护人',
   `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `u_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='敏感词过滤';

-- 附件表
DROP TABLE IF EXISTS d_attachment;
create TABLE `d_attachment` (
   `attachment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `attachment_name` varchar(128) DEFAULT '' COMMENT '附件名称',
   `attachment_type` tinyint(1) DEFAULT '0' COMMENT '附件类型:0:图片 1:文档 2:其他',
   `attachment_suffix` varchar(32) DEFAULT '' COMMENT '附件后缀',
   `attachment_path` varchar(255) DEFAULT '' COMMENT '附件文件路径',
   `attachment_url` varchar(255) DEFAULT '' COMMENT '附件文件路径',
   `attachment_size` bigint(22) DEFAULT '0' COMMENT '附件文件大小',
   `upload_login_name` varchar(64) DEFAULT '' COMMENT '上传附件用户',
   PRIMARY KEY (`attachment_id`),
   KEY `k_user` (`upload_login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';


insert into d_system_user(id, user_name, password, display_name, contact_phone, user_type, enabled, account_non_locked, account_non_expired, credentials_non_expired) values(1, 'admin', '$2a$10$xEOzVwRIs0UN8/fibgMZ4OwIy90b8S1/iYEppMV7LQJoNCb/Y1xLW', '肖恩', '13717689765', 1, 1, 1, 1, 1);
insert into d_user_pass_mapping(password, password_encode) values('admin', '$2a$10$xEOzVwRIs0UN8/fibgMZ4OwIy90b8S1/iYEppMV7LQJoNCb/Y1xLW');


insert into d_system_function(id, parent_id, function_name, display, status, action, icon, sort) values(1, 0, '系统管理', 1, 1, '#', 'fa-sitemap',1);
insert into d_system_function(id, parent_id, function_name, display, status, action, sort) values(2, 1, '用户管理', 1, 1, 'dvd/system/user.html', 2);
insert into d_system_function(id, parent_id, function_name, display, status, action, sort) values(3, 1, '角色管理', 1, 1, 'dvd/system/role.html', 3);
insert into d_system_function(id, parent_id, function_name, display, status, action, sort) values(4, 1, '菜单管理', 1, 1, 'dvd/system/function.html', 4);

insert into d_system_function(id, parent_id, function_name, display, status, action, icon, sort) values(5, 0, '通用管理', 1, 1, '#', 'fa-sitemap',5);
insert into d_system_function(id, parent_id, function_name, display, status, action, sort) values(6, 5, '国家管理', 1, 1, 'dvd/common/country.html', 6);
insert into d_system_function(id, parent_id, function_name, display, status, action, sort) values(7, 5, '城市管理', 1, 1, 'dvd/common/city.html', 7);
insert into d_system_function(id, parent_id, function_name, display, status, action, sort) values(8, 5, '城市驿站管理', 1, 1, 'dvd/common/station.html', 8);



insert into d_system_role(id, role_name, status) values(1, '系统管理', 1);
insert into d_system_user_role(user_id, role_id) values(1, 1);
insert into d_system_role_function(role_id, function_id) values(1, 1), (1, 2), (1, 3), (1, 4);

insert into d_system_role(id, role_name, status) values(2, '通用管理', 1);
insert into d_system_user_role(user_id, role_id) values(1, 2);
insert into d_system_role_function(role_id, function_id) values(2, 5), (2, 6), (2, 7), (2, 8);

insert into d_country(id, country_cn_name, country_en_name, code, create_user_id, update_user_id) values(1, '中国', 'CHINA', 'CN', 1, 1);
insert into d_city(country_id, city_cn, city_en, code, contact, email, create_user_id, update_user_id) values(1, '北京', 'BEIJING', 'BEIJING', '马建成', 'bjmajiancheng@davdian.com', 1, 1);

insert into d_station (id, country_id, city_id, create_user_id, update_user_id, status) values(1, 1, 1, 1, 1, 1);
insert into d_station_detail(id, station_id, language, city_title, topic_img, thumb_img, description, business_desc, travel_desc, education_desc, medical_desc, specialty_desc, holiday_desc, culture_desc, food_desc, sport_desc, climate_desc, celebrity_desc, tips_desc, city_info, business_cooperation)
values(1, 1, 1, '北京', 'https://9i.dvmama.com/shop_logo/2020/03/07/src__dc1cfa016ecf97d6f1e17acedccb2914.jpg', 'https://9i.dvmama.com/shop_logo/2020/03/07/src__dc1cfa016ecf97d6f1e17acedccb2914.jpg', '简介', '工商业描述', '旅游描述', '教育描述', '医疗描述', '特产描述', '节庆描述', '文化描述', '美食描述', '体育描述', '气候描述', '名人描述', '锦囊描述',
                                                                                                                                                                                                                                             '<p>城市信息</p><table class="table table-bordered"><tbody><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr></tbody></table><p><br></p>',
                                                                                                                                                                                                                                             '<p>商务交流</p><table class="table table-bordered"><tbody><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td><td><br></td><td><br></td></tr></tbody></table><p><br></p>');
