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

-- 通话记录
DROP TABLE IF EXISTS `call_records`;
CREATE TABLE `call_records` (
  `id` varchar(36) NOT NULL,
  `code` varchar(36) COMMENT '使用的usercode',
  `tel_function` int(2) COMMENT '通话类型',
  `call_type` int(2) COMMENT '呼叫方式 1.被叫 2.主叫',
  `phone_number` varchar(20) COMMENT '电话号码',
  `answer_status` int(2) COMMENT '应答状态1.挂断 2.响铃 3.应答',
  `start_date` varchar(50) COMMENT '开始时间',
  `end_date` varchar(50) COMMENT '结束时间',
  `answer_date` varchar(50) COMMENT '接听时间',
  `create_user` varchar(36) COMMENT '发送的usercode',
  `create_date` timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) COMMENT='通话记录';

DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate` (
  `user_code` varchar(36) NOT NULL COMMENT '用户编码',
  `score` int(1),
  `plan_id` varchar(36) NOT NULL,
  `info_id` varchar(36) COMMENT '信息表Id',
  `type` int(2) COMMENT '类型 3 航班 5 酒店 6 美食 7 景点 8 购物',
  `name` varchar(255) COMMENT '名称',
  `latitude` decimal(10,6) COMMENT '维度',
  `longitude` decimal(10,6) COMMENT '经度',
  `feedback` varchar(255) COMMENT '反馈',
  `comment` varchar(255) COMMENT '备注',
  `create_date` date COMMENT '创建时间',
  PRIMARY KEY (`user_code`,`plan_id`),
  KEY `user_id` (`user_code`) USING BTREE,
  KEY `info_id` (`user_code`) USING BTREE
) COMMENT='评价';

DROP TABLE IF EXISTS `phone_feedback`;
CREATE TABLE `phone_feedback` (
  `function_id` int(5) NOT NULL COMMENT '功能ID',
  `user_code` varchar(50) NOT NULL COMMENT '用户code',
  `score` int(2) COMMENT '评分1-3 1不喜欢 2一般 3喜欢',
  `feedback` varchar(255) COMMENT '反馈',
  `create_date` date,
  PRIMARY KEY (`function_id`,`user_code`)
) COMMENT='反馈';

DROP TABLE IF EXISTS `phone_function`;
CREATE TABLE `phone_function` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` int(2) COMMENT '类型 1:radio 2:checkbox 3:textarea',
  PRIMARY KEY (`id`)
) COMMENT='反馈类型';

INSERT INTO `phone_function` VALUES ('1', 'WIFI共享', '1');
INSERT INTO `phone_function` VALUES ('2', '电话', '1');
INSERT INTO `phone_function` VALUES ('3', '聊天', '1');
INSERT INTO `phone_function` VALUES ('4', '我的行程', '1');
INSERT INTO `phone_function` VALUES ('5', '地图导航', '1');
INSERT INTO `phone_function` VALUES ('6', '当地玩乐', '1');
INSERT INTO `phone_function` VALUES ('7', '请告诉我们您遇到的问题或想反馈的意见', '3');

ALTER TABLE `journey_plan` ADD COLUMN `feedback_flag`  int(1) DEFAULT 0 COMMENT '是否评价 1评价 0不评价' AFTER `latitude`;

ALTER TABLE `group` ADD COLUMN `chat_id` varchar(36) COMMENT '系统通知聊天编号' AFTER `to_date` ;
ALTER TABLE `group` ADD COLUMN `chat_name` varchar(20) COMMENT '系统通知聊天名称' AFTER `to_date` ;
ALTER TABLE `group` ADD COLUMN `tel_function` varchar(20) COMMENT '通话功能 1原生 2网络 3融云 '  AFTER `to_date` ;
update `group` set chat_id = '',chat_name = '',tel_function = '';

INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) VALUES ('6d2a851946bf4abeb7e0258c52ac58a2', '3', '融云电话', 'tel', '手机通话状态', '30', '0', '1', '2016-01-05 10:04:36', '1', '2016-01-05 10:04:36', '');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) VALUES ('bc1566430cdc40e58cf0f8bd2711d982', '2', '网络电话', 'tel', '手机通话状态', '20', '0', '1', '2016-01-05 10:04:19', '1', '2016-01-05 10:04:19', '');
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) VALUES ('445b0479d6484dc0928a9bafd1535e54', '1', 'sim卡', 'tel', '手机通话状态', '10', '0', '1', '2016-01-05 10:03:57', '1', '2016-01-05 10:03:57', '');

INSERT INTO `sys_menu` (id,parent_id,parent_ids,name,sort,href,target,icon,is_show,permission,create_by,create_date,update_by,update_date,remarks) VALUES ('1518382c210246bfb2b973ce12ef2947', '41', '0,1,4,41,', '系统通知', '60', '/tms/group/notice', '', 'comment', '1', '', '1', '2016-01-07 17:14:33', '1', '2016-01-07 17:14:33', ''), ('5ae660bd2475422dbbbaeff318a301ad', '7e18187519c54f71a79c703d8e7f36a0', '0,1,3,32,7e18187519c54f71a79c703d8e7f36a0,', '修改', '60', '', '', '', '0', 'tim:phone:edit', '1', '2016-01-11 18:42:24', '1', '2016-01-11 18:42:24', ''), ('7a1bf5b0ffb34b6a949260680494fb86', 'c4cb04cdf7d04b15b9def41c620b231d', '0,1,4,c4cb04cdf7d04b15b9def41c620b231d,', '行程评价', '60', '/tms/feedback/journey', '', 'road', '1', '', '1', '2016-01-06 17:16:08', '1', '2016-01-06 17:16:08', ''), ('7e18187519c54f71a79c703d8e7f36a0', '32', '0,1,3,32,', '当地电话', '180', '/tim/phone', '', 'bullhorn', '1', '', '1', '2016-01-11 18:41:24', '1', '2016-01-11 18:41:24', ''), ('908218a36811483a902e6bb916042694', '7e18187519c54f71a79c703d8e7f36a0', '0,1,3,32,7e18187519c54f71a79c703d8e7f36a0,', '查看', '30', '', '', '', '0', 'tim:phone:view', '1', '2016-01-11 18:41:59', '1', '2016-01-11 18:41:59', ''), ('a9d618b74c1a4d2ba95bb1f3de10eec5', 'c4cb04cdf7d04b15b9def41c620b231d', '0,1,4,c4cb04cdf7d04b15b9def41c620b231d,', 'App功能评价', '30', '/tms/feedback/app', '', 'inbox', '1', '', '1', '2016-01-06 17:15:49', '1', '2016-01-06 17:15:49', ''), ('c4cb04cdf7d04b15b9def41c620b231d', '4', '0,1,4,', '评价信息', '60', '', '', '', '1', '', '1', '2016-01-06 17:15:08', '1', '2016-01-06 17:15:08', '');

DROP TABLE IF EXISTS `qingma_client`;
CREATE TABLE `qingma_client` (
  `id` varchar(36) NOT NULL,
  `client_number` varchar(50) COMMENT '轻码云账号',
  `client_pwd` varchar(50) COMMENT '轻码云密码',
  `status` int(1) DEFAULT '1' COMMENT '状态 1.正常 0.关闭',
  `create_time` varchar(50),
  PRIMARY KEY (`id`)
) COMMENT='轻码云';

DROP TABLE IF EXISTS `qingma_record`;
CREATE TABLE `qingma_record` (
  `id` varchar(36) NOT NULL,
  `app_id` varchar(50) COMMENT '应用id',
  `client_number` varchar(50) COMMENT 'Client号码',
  `call_type` int(2) COMMENT '呼叫类型。0：直拨；1：回拨。',
  `caller` varchar(20) COMMENT '主叫电话号码。',
  `called` varchar(20) COMMENT '被叫电话号码。',
  `from_ser_num` varchar(20) COMMENT '主叫侧显示的号码。',
  `to_ser_num` varchar(20) COMMENT '被叫侧显示的号码。',
  `callId` varchar(50) COMMENT '（仅回拨存在）与回拨功能调用返回callid对应，唯一标示一路呼叫。',
  `start_time` datetime COMMENT '通话开始时间。',
  `stop_time` datetime COMMENT '通话结束时间。',
  `called_pick_time` datetime COMMENT '被叫接通时间',
  `bye_type` varchar(10) COMMENT '挂机类型。no_con 主被叫都未接通；a_con：主叫接通；b_con：主被叫都接通。',
  `call_time` int(10) COMMENT '话时长。单位：秒。',
  `reason` varchar(255) COMMENT '挂机原因描述。\r\n正常挂机：\r\n0：正常挂断（通话结束挂断）；\r\n1：余额不足；\r\n2：媒体超时；\r\n通用类型：\r\n10: 通话未建立，主叫挂机。\r\n11: 无人接听；\r\n12: 被叫拒绝接听；\r\n直拨类型：\r\n21: 直拨被叫未振铃，主叫挂断；\r\n22: 直拨被叫振铃了，主叫挂断；\r\n23: 直拨被叫振铃了，被叫挂断；\r\n回拨类型：\r\n31: 回拨主叫振铃了，主叫挂机；\r\n32: 回拨主叫接通了，被叫未振铃，主叫挂机；\r\n33: 回拨主叫接通了，被叫振铃了，主叫挂机；\r\n34: 回拨主叫接通了，被叫振铃了，被叫挂机；\r\n其他原因：',
  `record_url` varchar(255) COMMENT '通话录音完整下载地址',
  PRIMARY KEY (`id`)
) COMMENT='轻码云';



INSERT INTO `sys_menu` VALUES
  ('1e2ed99c085f463e89861dcc2a04080b', 'aa3bc782231d4d5198e77ad6e6f3c7e6', '0,1,5,aa3bc782231d4d5198e77ad6e6f3c7e6,', '库存统计', '30', '/iom/product/store/', '', 'home', '1', 'iom:product:view', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('4ce3f51bac0d4df4a0cfd5eda77666f3', 'aa3bc782231d4d5198e77ad6e6f3c7e6', '0,1,5,aa3bc782231d4d5198e77ad6e6f3c7e6,', '报废明细', '120', '/iom/product/discard/', '', 'trash', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('65905f9fc21049d1b2705e2281a433c4', 'cf5774694db7465aa55382f1ab6c2feb', '0,1,5,aa3bc782231d4d5198e77ad6e6f3c7e6,cf5774694db7465aa55382f1ab6c2feb,', '查看', '30', '', '', '', '0', 'iom:product:in:view', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('94fa717c78294b7183b96ae1df51cf74', 'b5cbf5e9c0354c199c0e25eff743152a', '0,1,5,b5cbf5e9c0354c199c0e25eff743152a,', '产品明细', '90', '/iom/product/detail/', '', 'book', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('9631fbe8b15c451fadfbc386b8eef6a3', 'cf5774694db7465aa55382f1ab6c2feb', '0,1,5,aa3bc782231d4d5198e77ad6e6f3c7e6,cf5774694db7465aa55382f1ab6c2feb,', '修改', '60', '', '', '', '0', 'iom:product:in:edit', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('a414b87df1a44c2698c5c3b09bba9a7e', '94fa717c78294b7183b96ae1df51cf74', '0,1,5,b5cbf5e9c0354c199c0e25eff743152a,94fa717c78294b7183b96ae1df51cf74,', '查看', '30', '', '', '', '0', 'iom:product:detail:view', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('aa3bc782231d4d5198e77ad6e6f3c7e6', '5', '0,1,5,', '库存管理', '120', '', '', '', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('b49273f3c6144000b5d73cd264e569ee', '94fa717c78294b7183b96ae1df51cf74', '0,1,5,b5cbf5e9c0354c199c0e25eff743152a,94fa717c78294b7183b96ae1df51cf74,', '修改', '60', '', '', '', '0', 'iom:product:detail:edit', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('b5a09a1de72a4127b3c6594ceea9158d', 'c398a07782bc4b7d9c1663a83b871ecb', '0,1,5,c398a07782bc4b7d9c1663a83b871ecb,', '出租管理', '30', '/iom/product/rent/', '', 'arrow-right', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('b5cbf5e9c0354c199c0e25eff743152a', '5', '0,1,5,', '产品列表', '90', '', '', '', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('c398a07782bc4b7d9c1663a83b871ecb', '5', '0,1,5,', '租借管理', '150', '', '', '', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('cf5774694db7465aa55382f1ab6c2feb', 'aa3bc782231d4d5198e77ad6e6f3c7e6', '0,1,5,aa3bc782231d4d5198e77ad6e6f3c7e6,', '入库明细', '60', '/iom/product/in/', '', 'plus', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('d8ac7a75d99b454aae9b3814cb983973', 'b5cbf5e9c0354c199c0e25eff743152a', '0,1,5,b5cbf5e9c0354c199c0e25eff743152a,', '产品列表', '60', '/iom/product/', '', 'shopping-cart', '1', '', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('dcaaf949139b45b4bddf5009742f320a', 'd8ac7a75d99b454aae9b3814cb983973', '0,1,5,b5cbf5e9c0354c199c0e25eff743152a,d8ac7a75d99b454aae9b3814cb983973,', '查看', '30', '', '', '', '0', 'iom:product:view', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0'),
  ('ee8f004212df47688e514aa539be3384', 'd8ac7a75d99b454aae9b3814cb983973', '0,1,5,b5cbf5e9c0354c199c0e25eff743152a,d8ac7a75d99b454aae9b3814cb983973,', '修改', '60', '', '', '', '0', 'iom:product:edit', '1', '2015-00-00 00:00:00', '1', '2015-00-00 00:00:00', '', '0');
