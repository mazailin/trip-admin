/* Drop Tables */

DROP TABLE IF EXISTS `sys_user`;
DROP TABLE IF EXISTS `sys_dict`;
DROP TABLE IF EXISTS `sys_log`;
DROP TABLE IF EXISTS `sys_menu`;
DROP TABLE IF EXISTS `sys_role`;
DROP TABLE IF EXISTS `sys_role_menu`;
DROP TABLE IF EXISTS `sys_user_role`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `country`;
DROP TABLE IF EXISTS `city`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `food`;
DROP TABLE IF EXISTS `food_file`;
DROP TABLE IF EXISTS `scenic`;
DROP TABLE IF EXISTS `scenic_file`;
DROP TABLE IF EXISTS `shop`;
DROP TABLE IF EXISTS `shop_file`;
DROP TABLE IF EXISTS `guide`;
DROP TABLE IF EXISTS `guide_file`;
DROP TABLE IF EXISTS `car_rental`;
DROP TABLE IF EXISTS `group`;
DROP TABLE IF EXISTS `group_user`;
DROP TABLE IF EXISTS `flight`;
DROP TABLE IF EXISTS `hotel`;
DROP TABLE IF EXISTS `group_flight`;
DROP TABLE IF EXISTS `group_hotel`;
DROP TABLE IF EXISTS `journey_day`;
DROP TABLE IF EXISTS `journey_plan`;
DROP TABLE IF EXISTS `phone_info`;
DROP TABLE IF EXISTS `stock_order`;
DROP TABLE IF EXISTS `apk`;
DROP TABLE IF EXISTS `version_tag`;
DROP TABLE IF EXISTS `code_opt`;

/* Create Tables */

CREATE TABLE `sys_user` (
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `login_name` VARCHAR(100) NOT NULL COMMENT '登录名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `name` VARCHAR(100) NOT NULL COMMENT '姓名',
  `email` VARCHAR(200) COMMENT '邮箱',
  `phone` VARCHAR(200) COMMENT '电话',
  `mobile` VARCHAR(200) COMMENT '手机',
  `user_type` CHAR(1) COMMENT '用户类型',
  `login_ip` VARCHAR(100) COMMENT '最后登陆IP',
  `login_date` DATETIME COMMENT '最后登陆时间',
  `login_flag` VARCHAR(64) COMMENT '是否可登录',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` DATETIME NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='用户表';

CREATE TABLE `sys_dict` (
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `value` VARCHAR(100) NOT NULL COMMENT '数据值',
  `label` VARCHAR(100) NOT NULL COMMENT '标签名',
  `type` VARCHAR(100) NOT NULL COMMENT '类型',
  `description` VARCHAR(100) NOT NULL COMMENT '描述',
  `sort` DECIMAL(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` VARCHAR(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` DATETIME NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='字典表';

CREATE TABLE `sys_log` (
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `type` CHAR(1) DEFAULT '1' COMMENT '日志类型',
  `title` VARCHAR(255) DEFAULT '' COMMENT '日志标题',
  `create_by` VARCHAR(64) COMMENT '创建者',
  `create_date` DATETIME COMMENT '创建时间',
  `remote_addr` VARCHAR(255) COMMENT '操作IP地址',
  `user_agent` VARCHAR(255) COMMENT '用户代理',
  `request_uri` VARCHAR(255) COMMENT '请求URI',
  `method` VARCHAR(5) COMMENT '操作方式',
  `params` TEXT COMMENT '操作提交的数据',
  `exception` TEXT COMMENT '异常信息',
  PRIMARY KEY (`id`)
) COMMENT='日志表';

CREATE TABLE `sys_menu` (
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `parent_id` VARCHAR(64) NOT NULL COMMENT '父级编号',
  `parent_ids` VARCHAR(2000) NOT NULL COMMENT '所有父级编号',
  `name` VARCHAR(100) NOT NULL COMMENT '名称',
  `sort` DECIMAL(10,0) NOT NULL COMMENT '排序',
  `href` VARCHAR(2000) COMMENT '链接',
  `target` VARCHAR(20) COMMENT '目标',
  `icon` VARCHAR(100) COMMENT '图标',
  `is_show` CHAR(1) NOT NULL COMMENT '是否在菜单中显示',
  `permission` VARCHAR(200) COMMENT '权限标识',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` DATETIME NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='菜单表';

CREATE TABLE `sys_role` (
  `id` VARCHAR(64) NOT NULL COMMENT '编号',
  `name` VARCHAR(100) NOT NULL COMMENT '角色名称',
  `is_sys` VARCHAR(64) COMMENT '是否系统数据',
  `useable` VARCHAR(64) COMMENT '是否可用',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` DATETIME NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='角色表';

CREATE TABLE `sys_role_menu` (
  `role_id` VARCHAR(64) NOT NULL COMMENT '角色编号',
  `menu_id` VARCHAR(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) COMMENT='角色-菜单';

CREATE TABLE `sys_user_role` (
  `user_id` VARCHAR(64) NOT NULL COMMENT '用户编号',
  `role_id` VARCHAR(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) COMMENT='用户-角色';

CREATE TABLE `customer` (
  `id` VARCHAR(36) NOT NULL COMMENT '标号',
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `description` VARCHAR(255) COMMENT '描述',
  `active` CHAR(1) COMMENT '是否可用\n0：不可用\n1：可用',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT ' 备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
) COMMENT='旅行社';

CREATE TABLE `country` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `description` VARCHAR(255) COMMENT '描述',
  `zcode` VARCHAR(16) COMMENT '当地区号',
  `cnzcode` VARCHAR(16) COMMENT '中国区号',
  `ambulance` VARCHAR(64) COMMENT '急救电话',
  `police` VARCHAR(64) COMMENT '匪警',
  `fire` VARCHAR(64) COMMENT '火警',
  `sea_emerg` VARCHAR(64) COMMENT '海上急救',
  `road_emerg` VARCHAR(64) COMMENT '公路抢险',
  `unionpay_call` VARCHAR(64) COMMENT '银联境外客服电话',
  `embassy_call` VARCHAR(64) COMMENT '大使馆电话',
  `embassy_time` VARCHAR(64) COMMENT '大使馆工作时间',
  `embassy_addr` VARCHAR(64) COMMENT '大使馆地址',
  `embassy_city` VARCHAR(64) COMMENT '大使馆城市',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='国家';

CREATE TABLE `city` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `country` VARCHAR(36) NOT NULL COMMENT '国家',
  `name` VARCHAR(255) NOT NULL COMMENT '城市名称',
  `description` VARCHAR(255) COMMENT '描述',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='城市';

CREATE TABLE `user` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(64) NOT NULL COMMENT '姓名',
  `gender` CHAR(1) DEFAULT '0' COMMENT '性别\n0：未知\n1：男\n2：女',
  `identity_card` VARCHAR(36) COMMENT '身份证',
  `wechat` VARCHAR(50) COMMENT '微信',
  `qq` VARCHAR(20) COMMENT 'QQ',
  `birth` DATE DEFAULT NULL COMMENT '出生日期',
  `birth_place` VARCHAR(255) COMMENT '出生地',
  `issue_place` VARCHAR(255) COMMENT '签发地',
  `issue_date` DATE COMMENT '签发时间',
  `expiry_date` DATE COMMENT '到期时间',
  `photo` VARCHAR(255) COMMENT '头像',
  `passport` VARCHAR(36) COMMENT '护照',
  `passport_photo` VARCHAR(255) COMMENT '护照照片',
  `phone` VARCHAR(36) COMMENT '电话',
  `email` VARCHAR(36) COMMENT '邮件',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `passport_UNIQUE` (`passport` ASC)
) COMMENT='用户';

CREATE TABLE `food` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `city` VARCHAR(36) NOT NULL COMMENT '城市',
  `type` CHAR(1) COMMENT '分类\n1：特色美食\n2：高档餐厅\n3：物美价廉\n4：中国味',
  `description` VARCHAR(2000) COMMENT '介绍',
  `address` VARCHAR(255) COMMENT '地址',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `score` INT DEFAULT 5 COMMENT '评分(0-5)',
  `worktime` VARCHAR(255) COMMENT '营业时间',
  `price` VARCHAR(255) COMMENT '人均价格',
  `phone` VARCHAR(255) COMMENT '预约电话',
  `cooper` CHAR(1) COMMENT '是否推荐\n0：不推荐\n1：推荐',
  `level` INT DEFAULT 0 COMMENT '推荐分级',
  `published` CHAR(1) COMMENT '是否发布\n0：未发布\n1：已发布',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='美食';

CREATE TABLE `food_file` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `food` VARCHAR(36) NOT NULL COMMENT '美食',
  `name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `type` CHAR(1) DEFAULT '2' COMMENT '类型\n1：封面\n2：轮播',
  `description` VARCHAR(2000) COMMENT '描述',
  `path` VARCHAR(64) COMMENT '路径',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='美食文件';

CREATE TABLE `scenic` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `city` VARCHAR(36) NOT NULL COMMENT '城市',
  `type` CHAR(1) COMMENT '分类\n1：景点推荐\n2：乡土风情\n3：民俗民风',
  `description` VARCHAR(2000) COMMENT '介绍',
  `address` VARCHAR(255) COMMENT '地址',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `score` INT DEFAULT 5 COMMENT '评分(0-5)',
  `price` VARCHAR(255) COMMENT '票价信息',
  `hours` VARCHAR(255) COMMENT '建议时间',
  `tips` VARCHAR(2000) COMMENT '实用贴士',
  `cooper` CHAR(1) COMMENT '是否推荐\n0：不推荐\n1：推荐',
  `level` INT DEFAULT 0 COMMENT '推荐分级',
  `published` CHAR(1) COMMENT '是否发布\n0：未发布\n1：已发布',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='景点';

CREATE TABLE `scenic_file` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `scenic` VARCHAR(36) NOT NULL COMMENT '景点',
  `name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `type` CHAR(1) DEFAULT '2' COMMENT '类型\n1：封面\n2：轮播',
  `description` VARCHAR(2000) COMMENT '描述',
  `path` VARCHAR(64) COMMENT '路径',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='景点文件';

CREATE TABLE `shop` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `city` VARCHAR(36) NOT NULL COMMENT '城市',
  `type` CHAR(1) COMMENT '分类\n1：珠宝\n2：纪念品\n3：奢侈品\n4：特产\n5：零食',
  `description` VARCHAR(2000) COMMENT '描述',
  `address` VARCHAR(255) COMMENT '地址',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `score` INT DEFAULT 5 COMMENT '评分(0-5)',
  `worktime` VARCHAR(255) COMMENT '营业时间',
  `cooper` CHAR(1) COMMENT '是否推荐\n0：不推荐\n1：推荐',
  `level` INT DEFAULT 0 COMMENT '推荐分级',
  `published` CHAR(1) COMMENT '是否发布\n0：未发布\n1：已发布',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='商家';

CREATE TABLE `shop_file` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `shop` VARCHAR(36) NOT NULL COMMENT '商家',
  `name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `type` CHAR(1) DEFAULT '2' COMMENT '类型\n1：封面\n2：轮播\n4：推荐购买',
  `description` VARCHAR(2000) NULL COMMENT '描述',
  `path` VARCHAR(64) COMMENT '路径',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='商家文件';

CREATE TABLE `guide` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `city` VARCHAR(36) NOT NULL COMMENT '城市',
  `description` VARCHAR(2000) COMMENT '介绍',
  `published` CHAR(1) COMMENT '是否发布\n0：未发布\n1：已发布',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='导购';

CREATE TABLE `guide_file` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `guide` VARCHAR(36) NOT NULL COMMENT '导购',
  `name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `type` CHAR(1) DEFAULT '2' COMMENT '类型\n1：封面\n2：轮播',
  `description` VARCHAR(2000) COMMENT '描述',
  `path` VARCHAR(64) COMMENT '路径',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='导购文件';

CREATE TABLE `car_rental` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `country` VARCHAR(36) NOT NULL COMMENT '国家',
  `type` CHAR(1) NOT NULL COMMENT '分类\n1：出租车预定\n2：预约接送机\n3：旅游专车',
  `phone` VARCHAR(255) COMMENT '电话',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='租车';

CREATE TABLE `group` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `description` VARCHAR(255) COMMENT '描述',
  `customer` VARCHAR(36) NOT NULL COMMENT '旅行社',
  `from_date` DATE COMMENT '开始日期',
  `to_date` DATE COMMENT '结束日期',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='团队';

CREATE TABLE `group_user` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `group` VARCHAR(36) NOT NULL COMMENT '团队',
  `user` VARCHAR(36) NOT NULL COMMENT '用户',
  `code` VARCHAR(36) NOT NULL COMMENT '登录编码',
  `cphone` VARCHAR(36) COMMENT '本机号码',
  `im_token` VARCHAR(255) COMMENT '用户融云token',
  `type` CHAR(1) DEFAULT '1' COMMENT '类型：\n0：导游\n1：游客',
  `position_flag` INT(1) DEFAULT 0 COMMENT '是否显示位置信息 0不显示 1显示',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC)
) COMMENT='团队成员';

CREATE TABLE `flight` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `flight_no` VARCHAR(255) NOT NULL COMMENT '航班号',
  `company` VARCHAR(255) COMMENT '航班公司',
  `departure_terminal` VARCHAR(255) COMMENT '起飞航站楼',
  `arrival_terminal` VARCHAR(255) COMMENT '到达航站楼',
  `departure_time` TIME COMMENT '起飞时间',
  `arrival_time` TIME COMMENT '到达时间',
  `departure_city` VARCHAR(255) COMMENT '起飞城市',
  `arrival_city` VARCHAR(255) COMMENT '到达城市',
  `common` VARCHAR(255) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT ' 备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='航班';

CREATE TABLE `hotel` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(255) COMMENT '名称',
  `address` VARCHAR(255) COMMENT '地址',
  `city` VARCHAR(36) COMMENT '城市',
  `phone` VARCHAR(255) COMMENT '电话',
  `longitude` DECIMAL(15,7) COMMENT '经度',
  `latitude` DECIMAL(15,7) COMMENT '维度',
  `description` VARCHAR(2000) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT ' 备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='酒店';

CREATE TABLE `group_flight` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `group_id` VARCHAR(36) COMMENT '组ID',
  `flight_id` VARCHAR(36) COMMENT '航班ID',
  `flight_date` DATE COMMENT '航班日期',
  `common` VARCHAR(255) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT ' 备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='团队航班';

CREATE TABLE `group_hotel` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `group_id` VARCHAR(36) COMMENT '组ID',
  `hotel_id` VARCHAR(36) COMMENT '旅馆ID',
  `check_date` DATE COMMENT '入住日期',
  `common` VARCHAR(255) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT ' 备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='团队酒店';

CREATE TABLE `journey_day` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `city_ids` VARCHAR(500) COMMENT '编号',
  `group_id` VARCHAR(36) COMMENT '旅程信息表',
  `day_number` INT(5) COMMENT '天数',
  `title` VARCHAR(100) COMMENT '每天行程标题',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='行程天数表';

CREATE TABLE `journey_plan` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `day_id` VARCHAR(36) NOT NULL COMMENT '行程天数ID',
  `type` INT(2) NOT NULL COMMENT '行程类别  1 行程  2 集合  3 航班 4 交通 5 住宿  6 餐饮 7 景点 8 购物',
  `name` VARCHAR(255) NOT NULL COMMENT '行程名称',
  `time` TIME COMMENT '时间',
  `time_flag` INT(1) COMMENT '是否有时间',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `latitude` DECIMAL(10,6) COMMENT '维度',
  `description` VARCHAR(2000) COMMENT '行程描述',
  `message` VARCHAR(2000) COMMENT '描述',
  `message_flag` INT(1) COMMENT '是否有备注',
  `info_id` VARCHAR(36) COMMENT '对应信息ID',
  `sort` INT(3) COMMENT '排序',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='具体行程表';

CREATE TABLE `phone_info` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `code` VARCHAR(100) COMMENT '编码',
  `status` INT(5) DEFAULT 1 COMMENT '状态 1.待测试 2.已测试 3.待租 4.已租 5.维修 9000.报废 9999.退货',
  `leaseholder` VARCHAR(20) COMMENT '租用人',
  `stock_order_id` VARCHAR(36) COMMENT '采购单ID',
  `comment` VARCHAR(255) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='手机信息';

CREATE TABLE `stock_order` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `order_id` VARCHAR(50) NULL COMMENT '订单号',
  `unit_price` DECIMAL(10,3) NOT NULL COMMENT '单价',
  `quantity` INT(11) COMMENT '数量',
  `total_price` DECIMAL(10,3) COMMENT '总价',
  `insurance` CHAR(1) COMMENT '是否购买保险',
  `model` VARCHAR(50) COMMENT '型号',
  `email` VARCHAR (255) COMMENT '邮件',
  `buyer` VARCHAR(64) COMMENT '采购人',
  `buying_time` DATE COMMENT '购买时间',
  `comment` VARCHAR(255) COMMENT '备注',
  `status`  INT(5) DEFAULT 1 COMMENT '状态0.失效 1.有效 3.完成',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='订单信息';

CREATE TABLE `apk` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(50) COMMENT '包名',
  `version` VARCHAR(20) COMMENT '版本号',
  `package_name` VARCHAR(255) COMMENT '包名',
  `description` VARCHAR(50) COMMENT '描述',
  `md5` VARCHAR(50) COMMENT 'MD5加密',
  `size` VARCHAR(50) COMMENT '安装包大小',
  `url` VARCHAR(100) COMMENT 'url地址',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='安装包更新记录表';

CREATE TABLE `version_tag` (
  `id` VARCHAR(64) NOT NULL COMMENT '版本标识',
  `type` INT(2) NOT NULL COMMENT '版本类型： 1：成员列表，2：行程',
  `tag` VARCHAR(36) NOT NULL COMMENT '版本标签',
  PRIMARY KEY (`id`,`type`)
) COMMENT='数据更新标签';

CREATE TABLE `code_opt` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `mod_type` INT(2) NOT NULL COMMENT '类型',
  `prefix` VARCHAR(36) COMMENT '前缀',
  `use_date` INT(2) COMMENT '是否包括日期',
  `date_fmt` VARCHAR(36) COMMENT '日期格式',
  `num_length` INT(2) COMMENT '长度',
  `next_num` VARCHAR(36) COMMENT '下一个编号',
  `separator` VARCHAR(36) COMMENT '分隔符',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mod_type_UNIQUE` (`mod_type` ASC)
) COMMENT='编码表';


-- 订单与库存管理
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `product_detail`;
DROP TABLE IF EXISTS `product_in`;
DROP TABLE IF EXISTS `in_detail`;
DROP TABLE IF EXISTS `product_discard`;
DROP TABLE IF EXISTS `rent`;
DROP TABLE IF EXISTS `rent_detail`;
CREATE TABLE `product` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `code` VARCHAR(10) NOT NULL COMMENT '编码',
  `unit` VARCHAR(100) COMMENT '计量单位',
  `avg_price` DECIMAL(10,2) COMMENT '平均价格',
  `total_amt` DECIMAL(10,2) COMMENT '总数',
  `rent_amt` DECIMAL(10,2) COMMENT '在租数量',
  `rsv_amt` DECIMAL(10,2) COMMENT '库存数量',
  `avl_amt` DECIMAL(10,2) COMMENT '可用数量',
  `low_limit` DECIMAL(10,2) COMMENT '下限',
  `use_detail` CHAR(1) COMMENT '是否按明细管理',
  `comment` VARCHAR(2000) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='产品列表';

CREATE TABLE `product_detail` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `product_id` VARCHAR(36) NOT NULL COMMENT '产品编号',
  `code` VARCHAR(64) COMMENT '编码',
  `device` VARCHAR(64) COMMENT '设备号',
  `status` VARCHAR(36) COMMENT '状态',
  `comment` VARCHAR(2000) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='产品明细';

CREATE TABLE `product_in` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `code` VARCHAR(36) NOT NULL COMMENT '单号',
  `product_id` VARCHAR(36) NOT NULL COMMENT '产品id',
  `in_date` DATETIME COMMENT '入库日期',
  `in_type` INT(2) COMMENT '入库类型',
  `operator` VARCHAR(64) COMMENT '经办人',
  `bills` VARCHAR(255) COMMENT '采购单据',
  `insure` CHAR(1) COMMENT '是否购买保险',
  `amount` DECIMAL(10,2) COMMENT '数量',
  `buy_amount` DECIMAL(10,2) COMMENT '采购数量',
  `price` DECIMAL(10,2) COMMENT '单价',
  `total_price` DECIMAL(10,2) COMMENT '总价',
  `comment` VARCHAR(2000) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='产品入库';

CREATE TABLE `in_detail` (
  `in_id` VARCHAR(36) NOT NULL COMMENT '产品入库明细编号',
  `pro_detail_id` VARCHAR(36) NOT NULL COMMENT '产品明细id',
  PRIMARY KEY (`in_id`, `pro_detail_id`)
) COMMENT='产品入库明细';

CREATE TABLE `product_discard` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `code` VARCHAR(36) COMMENT '单据编号',
  `product_id` VARCHAR(36) NOT NULL COMMENT '产品编号',
  `pro_detail_id` VARCHAR(36) COMMENT '产品明细编号',
  `amount` DECIMAL(10,2) COMMENT '数量',
  `reasons` VARCHAR(2000) COMMENT '原因',
  `dis_date` DATETIME COMMENT '报废日期',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='产品报废';

CREATE TABLE `rent` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `code` VARCHAR(36) COMMENT '单据编号',
  `renter` VARCHAR(255) COMMENT '租赁人',
  `operator` VARCHAR(255) COMMENT '经办人',
  `deposit` DECIMAL(10,2) COMMENT '押金',
  `get_date` DATETIME COMMENT '取货日期',
  `begin_date` DATETIME COMMENT '开始日期',
  `end_date` DATETIME COMMENT '结束日期',
  `comment` VARCHAR(2000) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='产品租赁';

CREATE TABLE `rent_order` (
  `rent_id` VARCHAR(36) NOT NULL COMMENT '租赁单据编号',
  `product_id` VARCHAR(36) NOT NULL COMMENT '产品编号',
  `amount` DECIMAL(10,2) COMMENT '数量',
  PRIMARY KEY (`rent_id`, `product_id`)
) COMMENT='预订数量';

CREATE TABLE `rent_detail` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `rent_id` VARCHAR(36) NOT NULL COMMENT '租赁单据编号',
  `product_id` VARCHAR(36) NOT NULL COMMENT '产品编号',
  `pro_detail_id` VARCHAR(36) COMMENT '产品明细编号',
  `amount` DECIMAL(10,2) COMMENT '数量',
  `return_amount` DECIMAL(10,2) COMMENT '归还数量',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='租赁明细';

-- 位置轨迹
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `user_id` VARCHAR(36) NOT NULL COMMENT '用户编号',
  `lng` DECIMAL(10,6) NOT NULL COMMENT '经度',
  `lat` DECIMAL(10,6) NOT NULL COMMENT '纬度',
  `timing` TIMESTAMP NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) COMMENT='位置轨迹';

-- 团队分组
DROP TABLE IF EXISTS `team`;
DROP TABLE IF EXISTS `team_user`;
CREATE TABLE `team` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `group_id` VARCHAR(36) NOT NULL COMMENT '旅行团编号',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `comment` VARCHAR(2000) COMMENT '备注',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='团队分组';

CREATE TABLE `team_user` (
  `team_id` VARCHAR(36) NOT NULL COMMENT '小组编号',
  `user_id` VARCHAR(36) NOT NULL COMMENT '名称',
  PRIMARY KEY (`team_id`, `user_id`)
) COMMENT='小组成员';

-- 当地电话
DROP TABLE IF EXISTS `local_phone`;
CREATE TABLE `local_phone` (
  `id` VARCHAR(36) NOT NULL COMMENT '编号',
  `country` VARCHAR(36) NOT NULL COMMENT '国家',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `phone` VARCHAR(255) COMMENT '电话',
  `create_by` VARCHAR(64) NOT NULL COMMENT '创建者',
  `create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) NOT NULL COMMENT '更新者',
  `update_date` TIMESTAMP NOT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255) COMMENT '备注信息',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) COMMENT='当地电话';