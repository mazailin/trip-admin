INSERT INTO `sys_user` VALUES ('1', 'admin', 'd886183d225926c15672a0bd308a1ea87d7acfbf89feceedfe1e1625', '系统管理员', '', '', '', '1', '', '2015-11-11 08:00:00', '1', '1', '2015-11-11 08:00:00', '1', '2015-11-11 08:00:00', '最高管理员');

INSERT INTO `code_opt` VALUES ('1', 1, '', '1', 'yyyyMMdd', 4, '','', '1', '2015-11-11 08:00:00', '1', '2015-11-11 08:00:00', '');

INSERT INTO `sys_menu` VALUES ('1', '0', '0,', '功能菜单', '0', '', '', '', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');

INSERT INTO `sys_menu` VALUES ('8', '1', '0,1,', '我的面板', '100', '', '', '', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('81', '8', '0,1,8,', '个人信息', '30', '', '', '', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('811', '81', '0,1,8,81,', '个人信息', '30', '/sys/user/info', '', 'user', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('812', '81', '0,1,8,81,', '修改密码', '60', '/sys/user/modifyPwd', '', 'lock', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');

INSERT INTO `sys_menu` VALUES ('2', '1', '0,1,', '客户管理', '200', '', '', '', '1', '', '1', '2015-10-21 18:48:35', '1', '2015-10-21 18:48:35', '');
INSERT INTO `sys_menu` VALUES ('21', '2', '0,1,2,', '旅行社', '30', '', '', '', '1', '', '1', '2015-10-21 18:50:39', '1', '2015-10-21 18:50:39', '');
INSERT INTO `sys_menu` VALUES ('211', '21', '0,1,2,21,', '旅行社', '30', '/crm/customer', '', 'globe', '1', '', '1', '2015-10-21 18:56:45', '1', '2015-10-22 17:13:37', '');
INSERT INTO `sys_menu` VALUES ('2111', '211', '0,1,2,21,211,', '查看', '30', '', '', '', '0', 'crm:customer:view', '1', '2015-10-22 15:43:44', '1', '2015-10-22 15:43:44', '');
INSERT INTO `sys_menu` VALUES ('2112', '211', '0,1,2,21,211,', '修改', '60', '', '', '', '0', 'crm:customer:edit', '1', '2015-10-22 15:44:34', '1', '2015-10-22 15:44:34', '');
INSERT INTO `sys_menu` VALUES ('22', '2', '0,1,2,', '移动用户', '60', '', '', '', '1', '', '1', '2015-10-21 18:50:53', '1', '2015-10-21 18:59:57', '');
INSERT INTO `sys_menu` VALUES ('221', '22', '0,1,2,22,', '用户资料', '30', '/crm/appuser', '', 'book', '1', 'crm:appuser:view', '1', '2015-10-21 18:58:13', '1', '2015-10-22 14:30:31', '');

INSERT INTO `sys_menu` VALUES ('3', '1', '0,1,', '旅游信息', '300', '', '', '', '1', '', '1', '2015-10-23 15:01:45', '1', '2015-10-23 15:01:45', '');
INSERT INTO `sys_menu` VALUES ('31', '3', '0,1,3,', '地点管理', '30', '', '', '', '1', '', '1', '2015-10-23 15:04:05', '1', '2015-10-23 15:08:52', '');
INSERT INTO `sys_menu` VALUES ('311', '31', '0,1,3,31,', '国家管理', '30', '/tim/country', '', 'star', '1', '', '1', '2015-10-23 15:12:39', '1', '2015-11-11 15:24:44', '');
INSERT INTO `sys_menu` VALUES ('3111', '311', '0,1,3,31,311,', '查看', '30', '', '', '', '0', 'tim:country:view', '1', '2015-10-23 15:40:08', '1', '2015-10-23 15:40:08', '');
INSERT INTO `sys_menu` VALUES ('3112', '311', '0,1,3,31,311,', '修改', '60', '', '', '', '0', 'tim:country:edit', '1', '2015-10-23 15:40:35', '1', '2015-10-23 15:40:35', '');
INSERT INTO `sys_menu` VALUES ('312', '31', '0,1,3,31,', '城市管理', '60', '/tim/city', '', 'road', '1', '', '1', '2015-10-23 15:33:09', '1', '2015-10-23 15:33:09', '');
INSERT INTO `sys_menu` VALUES ('3121', '312', '0,1,3,31,312,', '查看', '30', '', '', '', '0', 'tim:city:view', '1', '2015-10-23 15:40:55', '1', '2015-10-23 15:40:55', '');
INSERT INTO `sys_menu` VALUES ('3122', '312', '0,1,3,31,312,', '修改', '60', '', '', '', '0', 'tim:city:edit', '1', '2015-10-23 15:41:14', '1', '2015-10-23 15:41:14', '');
INSERT INTO `sys_menu` VALUES ('32', '3', '0,1,3,', '内容管理', '60', '', '', '', '1', '', '1', '2015-10-23 15:05:00', '1', '2015-10-23 15:09:11', '');
INSERT INTO `sys_menu` VALUES ('321', '32', '0,1,3,32,', '美食', '30', '/tim/food', '', 'glass', '1', '', '1', '2015-10-23 15:34:31', '1', '2015-10-23 15:34:31', '');
INSERT INTO `sys_menu` VALUES ('3211', '321', '0,1,3,32,321,', '查看', '30', '', '', '', '0', 'tim:food:view', '1', '2015-10-23 15:41:47', '1', '2015-10-23 15:41:47', '');
INSERT INTO `sys_menu` VALUES ('3212', '321', '0,1,3,32,321,', '修改', '60', '', '', '', '0', 'tim:food:edit', '1', '2015-10-23 15:42:09', '1', '2015-10-23 15:42:09', '');
INSERT INTO `sys_menu` VALUES ('322', '32', '0,1,3,32,', '景点', '60', '/tim/scenic', '', 'camera', '1', '', '1', '2015-10-23 15:34:59', '1', '2015-10-23 15:34:59', '');
INSERT INTO `sys_menu` VALUES ('3221', '322', '0,1,3,32,322,', '查看', '30', '', '', '', '0', 'tim:scenic:view', '1', '2015-10-23 15:42:33', '1', '2015-10-23 15:42:33', '');
INSERT INTO `sys_menu` VALUES ('3222', '322', '0,1,3,32,322,', '修改', '60', '', '', '', '0', 'tim:scenic:edit', '1', '2015-10-23 15:42:56', '1', '2015-10-23 15:42:56', '');
INSERT INTO `sys_menu` VALUES ('323', '32', '0,1,3,32,', '购物', '90', '/tim/shop', '', 'shopping-cart', '1', '', '1', '2015-10-23 15:35:24', '1', '2015-10-23 15:35:24', '');
INSERT INTO `sys_menu` VALUES ('3231', '323', '0,1,3,32,323,', '查看', '30', '', '', '', '0', 'tim:shop:view', '1', '2015-10-23 15:43:23', '1', '2015-10-23 15:43:23', '');
INSERT INTO `sys_menu` VALUES ('3232', '323', '0,1,3,32,323,', '修改', '60', '', '', '', '0', 'tim:shop:edit', '1', '2015-10-23 15:43:41', '1', '2015-10-23 15:43:41', '');
INSERT INTO `sys_menu` VALUES ('324', '32', '0,1,3,32,', '导购', '120', '/tim/guide', '', 'flag', '1', '', '1', '2015-10-23 15:36:41', '1', '2015-10-23 15:39:19', '');
INSERT INTO `sys_menu` VALUES ('3241', '324', '0,1,3,32,324,', '查看', '30', '', '', '', '0', 'tim:guide:view', '1', '2015-10-23 15:43:57', '1', '2015-10-23 15:43:57', '');
INSERT INTO `sys_menu` VALUES ('3242', '324', '0,1,3,32,324,', '修改', '60', '', '', '', '0', 'tim:guide:edit', '1', '2015-10-23 15:44:13', '1', '2015-10-23 15:44:13', '');
INSERT INTO `sys_menu` VALUES ('325', '32', '0,1,3,32,', '租车', '150', '/tim/car', '', 'random', '1', '', '1', '2015-10-23 15:38:04', '1', '2015-10-23 15:38:04', '');
INSERT INTO `sys_menu` VALUES ('3251', '325', '0,1,3,32,325,', '查看', '30', '', '', '', '0', 'tim:car:view', '1', '2015-10-23 15:44:31', '1', '2015-10-23 15:44:31', '');
INSERT INTO `sys_menu` VALUES ('3252', '325', '0,1,3,32,325,', '修改', '60', '', '', '', '0', 'tim:car:edit', '1', '2015-10-23 15:44:43', '1', '2015-10-23 15:44:43', '');
INSERT INTO `sys_menu` VALUES ('33', '3', '0,1,3,', '航班管理', '120', '', '', '', '1', '', '1', '2015-11-05 15:20:38', '1', '2015-11-10 18:05:49', '');
INSERT INTO `sys_menu` VALUES ('331', '33', '0,1,3,33,', '航班列表', '30', '/tim/flight/list', '', 'plane', '1', '', '1', '2015-11-06 10:48:14', '1', '2015-11-06 15:07:06', '');
INSERT INTO `sys_menu` VALUES ('3311', '331', '0,1,3,33,331,', '查看', '30', '', '', '', '0', 'tim:flight:view', '1', '2015-11-06 10:51:59', '1', '2015-11-06 10:52:07', '');
INSERT INTO `sys_menu` VALUES ('3312', '331', '0,1,3,33,331,', '编辑', '60', '', '', '', '0', 'tim:flight:edit', '1', '2015-11-06 10:52:20', '1', '2015-11-06 10:52:20', '');
INSERT INTO `sys_menu` VALUES ('34', '3', '0,1,3,', '酒店管理', '90', '', '', '', '1', '', '1', '2015-11-05 15:20:28', '1', '2015-11-10 18:04:55', '');
INSERT INTO `sys_menu` VALUES ('341', '34', '0,1,3,34,', '酒店列表', '30', '/tim/hotel/list', '', 'hotel', '1', '', '1', '2015-11-06 15:10:01', '1', '2015-11-06 15:12:31', '');
INSERT INTO `sys_menu` VALUES ('3411', '341', '0,1,3,34,341,', '查看', '60', '', '', '', '0', 'tim:hotel:view', '1', '2015-11-06 15:03:52', '1', '2015-11-06 15:10:33', '');
INSERT INTO `sys_menu` VALUES ('3412', '341', '0,1,3,34,341,', '编辑', '30', '', '', '', '0', 'tim:hotel:edit', '1', '2015-11-06 15:03:10', '1', '2015-11-06 15:10:22', '');

INSERT INTO `sys_menu` VALUES ('4', '1', '0,1,', '旅游管理', '400', '', '', '', '1', '', '1', '2015-10-27 11:47:35', '1', '2015-10-27 11:47:35', '');
INSERT INTO `sys_menu` VALUES ('41', '4', '0,1,4,', '旅游团管理', '30', '', '', '', '1', '', '1', '2015-10-27 12:28:08', '1', '2015-10-27 12:28:59', '');
INSERT INTO `sys_menu` VALUES ('411', '41', '0,1,4,41,', '旅游团管理', '30', '/tms/group', '', 'barcode', '1', '', '1', '2015-10-27 12:29:50', '1', '2015-10-27 12:30:04', '');
INSERT INTO `sys_menu` VALUES ('4111', '411', '0,1,4,41,411,', '查看', '60', '', '', '', '0', 'tms:group:view', '1', '2015-11-05 14:33:00', '1', '2015-11-05 14:34:25', '');
INSERT INTO `sys_menu` VALUES ('4112', '411', '0,1,4,41,411,', '编辑', '30', '', '', '', '0', 'tms:group:edit', '1', '2015-11-05 14:32:29', '1', '2015-11-05 14:32:29', '');

INSERT INTO `sys_menu` VALUES ('5', '1', '0,1,', '库存管理', '500', '', '', '', '1', '', '1', '2015-10-22 16:53:59', '1', '2015-10-22 16:53:59', '');
INSERT INTO `sys_menu` VALUES ('51', '5', '0,1,5,', '手机信息管理', '30', '', '', '', '1', '', '1', '2015-10-22 16:55:23', '1', '2015-10-22 16:55:23', '');
INSERT INTO `sys_menu` VALUES ('511', '51', '0,1,5,51,', '订单管理', '30', '/ims/phoneOrder', '', 'align-justify', '1', '', '1', '2015-10-22 16:57:52', '1', '2015-10-22 16:58:26', '');
INSERT INTO `sys_menu` VALUES ('512', '51', '0,1,5,51,', '手机管理', '60', '/ims/phone/list', '', 'list', '1', '', '1', '2015-10-23 12:00:47', '1', '2015-11-11 15:29:05', '');
INSERT INTO `sys_menu` VALUES ('513', '51', '0,1,5,51,', '查看', '1050', '', '', '', '0', 'ims:phone:view', '1', '2015-11-05 14:29:50', '1', '2015-11-05 14:33:54', '');
INSERT INTO `sys_menu` VALUES ('514', '51', '0,1,5,51,', '编辑', '1020', '', '', '', '0', 'ims:phone:edit', '1', '2015-11-05 14:29:34', '1', '2015-11-05 14:30:59', '');
INSERT INTO `sys_menu` VALUES ('515', '51', '0,1,5,51,', '退款', '1080', '', '', '', '0', 'ims:phone:refund', '1', '2015-11-05 14:30:45', '1', '2015-11-05 14:33:59', '');

INSERT INTO `sys_menu` VALUES ('9', '1', '0,1,', '系统设置', '9999', '', '', '', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-11-09 16:09:34', '');
INSERT INTO `sys_menu` VALUES ('91', '9', '0,1,9,', '用户角色', '30', '', '', '', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-19 17:42:28', '');
INSERT INTO `sys_menu` VALUES ('911', '91', '0,1,9,91,', '用户管理', '30', '/sys/user', '', 'user', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-28 12:28:48', '');
INSERT INTO `sys_menu` VALUES ('9111', '911', '0,1,9,91,911,', '查看', '30', '', '', '', '0', 'sys:user:view', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9112', '911', '0,1,9,91,911,', '修改', '40', '', '', '', '0', 'sys:user:edit', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('912', '91', '0,1,9,91,', '角色管理', '60', '/sys/role/', '', 'lock', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-19 17:44:44', '');
INSERT INTO `sys_menu` VALUES ('9121', '912', '0,1,9,91,912,', '查看', '30', '', '', '', '0', 'sys:role:view', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9122', '912', '0,1,9,91,912,', '修改', '40', '', '', '', '0', 'sys:role:edit', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('92', '9', '0,1,9,', '系统设置', '60', '', '', '', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('921', '92', '0,1,9,92,', '菜单管理', '30', '/sys/menu/', '', 'list-alt', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9211', '921', '0,1,9,92,921,', '查看', '30', '', '', '', '0', 'sys:menu:view', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9212', '921', '0,1,9,92,921,', '修改', '40', '', '', '', '0', 'sys:menu:edit', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('922', '92', '0,1,9,92,', '字典管理', '60', '/sys/dict/', '', 'th-list', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9221', '922', '0,1,9,92,922,', '查看', '30', '', '', '', '0', 'sys:dict:view', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9222', '922', '0,1,9,92,922,', '修改', '40', '', '', '', '0', 'sys:dict:edit', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('923', '92', '0,1,9,92,', '编码规则', '90', '/sys/code/', '', 'tasks', '1', '', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9231', '923', '0,1,9,92,923,', '查看', '30', '', '', '', '0', 'sys:code:view', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('9232', '923', '0,1,9,92,923,', '修改', '40', '', '', '', '0', 'sys:code:edit', '1', '2015-10-20 08:00:00', '1', '2015-10-20 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('93', '9', '0,1,9,', '日志查询', '90', '', '', '', '1', '', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('931', '93', '0,1,9,93,', '日志查询', '30', '/sys/log', '', 'pencil', '1', 'sys:log:view', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '');
INSERT INTO `sys_menu` VALUES ('932', '93', '0,1,9,93,', '连接池监视', '60', '/../druid', '', 'tags', '1', '', '1', '2013-10-18 08:00:00', '1', '2015-11-11 12:38:42', '');
INSERT INTO `sys_menu` VALUES ('94', '9', '0,1,9,', '手机应用', '120', '', '', '', '1', '', '1', '2015-11-11 15:08:38', '1', '2015-11-11 15:08:38', '');
INSERT INTO `sys_menu` VALUES ('941', '94', '0,1,9,94,', '安装包管理', '30', '/sys/apk', '', 'inbox', '1', '', '1', '2015-11-11 15:09:44', '1', '2015-11-11 15:10:24', '');
INSERT INTO `sys_menu` VALUES ('9411', '941', '0,1,9,94,941,', '上传', '30', '', '', '', '0', 'sys:apk:upload', '1', '2015-11-11 15:10:50', '1', '2015-11-11 15:10:50', '');
INSERT INTO `sys_menu` VALUES ('9412', '941', '0,1,9,94,941,', '查看', '60', '', '', '', '0', 'sys:apk:view', '1', '2015-11-11 15:11:14', '1', '2015-11-11 15:11:14', '');


INSERT INTO `sys_role` VALUES ('1', '系统管理员', '1', '1', '1', '2015-11-11 18:02:37', '1', '2015-11-11 18:02:37', '');

INSERT INTO `sys_user_role` VALUES ('1', '1');

INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '21');
INSERT INTO `sys_role_menu` VALUES ('1', '211');
INSERT INTO `sys_role_menu` VALUES ('1', '2111');
INSERT INTO `sys_role_menu` VALUES ('1', '2112');
INSERT INTO `sys_role_menu` VALUES ('1', '22');
INSERT INTO `sys_role_menu` VALUES ('1', '221');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '31');
INSERT INTO `sys_role_menu` VALUES ('1', '311');
INSERT INTO `sys_role_menu` VALUES ('1', '3111');
INSERT INTO `sys_role_menu` VALUES ('1', '3112');
INSERT INTO `sys_role_menu` VALUES ('1', '312');
INSERT INTO `sys_role_menu` VALUES ('1', '3121');
INSERT INTO `sys_role_menu` VALUES ('1', '3122');
INSERT INTO `sys_role_menu` VALUES ('1', '32');
INSERT INTO `sys_role_menu` VALUES ('1', '321');
INSERT INTO `sys_role_menu` VALUES ('1', '3211');
INSERT INTO `sys_role_menu` VALUES ('1', '3212');
INSERT INTO `sys_role_menu` VALUES ('1', '322');
INSERT INTO `sys_role_menu` VALUES ('1', '3221');
INSERT INTO `sys_role_menu` VALUES ('1', '3222');
INSERT INTO `sys_role_menu` VALUES ('1', '323');
INSERT INTO `sys_role_menu` VALUES ('1', '3231');
INSERT INTO `sys_role_menu` VALUES ('1', '3232');
INSERT INTO `sys_role_menu` VALUES ('1', '324');
INSERT INTO `sys_role_menu` VALUES ('1', '3241');
INSERT INTO `sys_role_menu` VALUES ('1', '3242');
INSERT INTO `sys_role_menu` VALUES ('1', '325');
INSERT INTO `sys_role_menu` VALUES ('1', '3251');
INSERT INTO `sys_role_menu` VALUES ('1', '3252');
INSERT INTO `sys_role_menu` VALUES ('1', '33');
INSERT INTO `sys_role_menu` VALUES ('1', '331');
INSERT INTO `sys_role_menu` VALUES ('1', '3311');
INSERT INTO `sys_role_menu` VALUES ('1', '3312');
INSERT INTO `sys_role_menu` VALUES ('1', '34');
INSERT INTO `sys_role_menu` VALUES ('1', '341');
INSERT INTO `sys_role_menu` VALUES ('1', '3411');
INSERT INTO `sys_role_menu` VALUES ('1', '3412');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '41');
INSERT INTO `sys_role_menu` VALUES ('1', '411');
INSERT INTO `sys_role_menu` VALUES ('1', '4111');
INSERT INTO `sys_role_menu` VALUES ('1', '4112');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '51');
INSERT INTO `sys_role_menu` VALUES ('1', '511');
INSERT INTO `sys_role_menu` VALUES ('1', '512');
INSERT INTO `sys_role_menu` VALUES ('1', '513');
INSERT INTO `sys_role_menu` VALUES ('1', '514');
INSERT INTO `sys_role_menu` VALUES ('1', '515');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '81');
INSERT INTO `sys_role_menu` VALUES ('1', '811');
INSERT INTO `sys_role_menu` VALUES ('1', '812');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', '91');
INSERT INTO `sys_role_menu` VALUES ('1', '911');
INSERT INTO `sys_role_menu` VALUES ('1', '9111');
INSERT INTO `sys_role_menu` VALUES ('1', '9112');
INSERT INTO `sys_role_menu` VALUES ('1', '912');
INSERT INTO `sys_role_menu` VALUES ('1', '9121');
INSERT INTO `sys_role_menu` VALUES ('1', '9122');
INSERT INTO `sys_role_menu` VALUES ('1', '92');
INSERT INTO `sys_role_menu` VALUES ('1', '921');
INSERT INTO `sys_role_menu` VALUES ('1', '9211');
INSERT INTO `sys_role_menu` VALUES ('1', '9212');
INSERT INTO `sys_role_menu` VALUES ('1', '922');
INSERT INTO `sys_role_menu` VALUES ('1', '9221');
INSERT INTO `sys_role_menu` VALUES ('1', '9222');
INSERT INTO `sys_role_menu` VALUES ('1', '923');
INSERT INTO `sys_role_menu` VALUES ('1', '9231');
INSERT INTO `sys_role_menu` VALUES ('1', '9232');
INSERT INTO `sys_role_menu` VALUES ('1', '93');
INSERT INTO `sys_role_menu` VALUES ('1', '931');
INSERT INTO `sys_role_menu` VALUES ('1', '932');
INSERT INTO `sys_role_menu` VALUES ('1', '94');
INSERT INTO `sys_role_menu` VALUES ('1', '941');
INSERT INTO `sys_role_menu` VALUES ('1', '9411');
INSERT INTO `sys_role_menu` VALUES ('1', '9412');


INSERT INTO `sys_dict` VALUES ('1', '1', '行程', 'journey_plan', '行程信息', '10', '0', '1', '2015-10-30 20:17:35', '1', '2015-10-30 20:17:35', '');
INSERT INTO `sys_dict` VALUES ('2', '2', '集合', 'journey_plan', '行程信息', '20', '0', '1', '2015-10-30 20:17:55', '1', '2015-10-30 20:17:55', '');
INSERT INTO `sys_dict` VALUES ('3', '3', '航班', 'journey_plan', '行程信息', '30', '0', '1', '2015-10-30 20:18:04', '1', '2015-10-31 14:51:50', '');
INSERT INTO `sys_dict` VALUES ('4', '4', '交通', 'journey_plan', '行程信息', '40', '0', '1', '2015-10-30 20:18:13', '1', '2015-10-31 14:52:06', '');
INSERT INTO `sys_dict` VALUES ('5', '5', '住宿', 'journey_plan', '行程信息', '50', '0', '1', '2015-10-30 20:18:20', '1', '2015-10-31 14:52:18', '');
INSERT INTO `sys_dict` VALUES ('6', '6', '餐饮', 'journey_plan', '行程信息', '60', '0', '1', '2015-10-30 20:18:30', '1', '2015-10-31 14:52:29', '');
INSERT INTO `sys_dict` VALUES ('7', '7', '景点', 'journey_plan', '行程信息', '70', '0', '1', '2015-10-30 20:18:40', '1', '2015-10-31 14:52:45', '');
INSERT INTO `sys_dict` VALUES ('8', '8', '购物', 'journey_plan', '行程信息', '80', '0', '1', '2015-10-30 20:18:47', '1', '2015-10-31 14:52:54', '');
INSERT INTO `sys_dict` VALUES ('9', '1', '待测试', 'phone_status', '手机状态信息', '10', '0', '1', '2015-11-05 12:10:30', '1', '2015-11-05 12:10:30', '');
INSERT INTO `sys_dict` VALUES ('10', '2', '已测试', 'phone_status', '手机状态信息', '20', '0', '1', '2015-11-05 12:10:40', '1', '2015-11-05 12:10:40', '');
INSERT INTO `sys_dict` VALUES ('11', '3', '待租', 'phone_status', '手机状态信息', '30', '0', '1', '2015-11-05 12:10:49', '1', '2015-11-05 12:10:49', '');
INSERT INTO `sys_dict` VALUES ('12', '4', '已租', 'phone_status', '手机状态信息', '40', '0', '1', '2015-11-05 12:10:57', '1', '2015-11-05 12:10:57', '');
INSERT INTO `sys_dict` VALUES ('13', '5', '维修', 'phone_status', '手机状态信息', '50', '0', '1', '2015-11-05 12:11:05', '1', '2015-11-05 12:11:05', '');
INSERT INTO `sys_dict` VALUES ('14', '9000', '报废', 'phone_status', '手机状态信息', '60', '0', '1', '2015-11-05 12:11:16', '1', '2015-11-05 12:11:16', '');
INSERT INTO `sys_dict` VALUES ('15', '9999', '退款', 'phone_status', '手机状态信息', '70', '0', '1', '2015-11-05 12:11:24', '1', '2015-11-05 12:11:24', '');
INSERT INTO `sys_dict` VALUES ('16', '1', '特色美食', 'food_type', '美食类型', '10', '0', '1', '2015-10-27 15:58:17', '1', '2015-10-27 15:58:17', '');
INSERT INTO `sys_dict` VALUES ('17', '2', '高档餐厅', 'food_type', '美食类型', '20', '0', '1', '2015-10-27 15:58:35', '1', '2015-10-27 15:58:35', '');
INSERT INTO `sys_dict` VALUES ('18', '3', '物美价廉', 'food_type', '美食类型', '30', '0', '1', '2015-10-27 15:58:45', '1', '2015-10-27 15:58:45', '');
INSERT INTO `sys_dict` VALUES ('19', '4', '中国胃', 'food_type', '美食类型', '40', '0', '1', '2015-10-27 15:59:06', '1', '2015-10-27 15:59:06', '');
INSERT INTO `sys_dict` VALUES ('20', 'default', '默认主题', 'theme', '主题方案', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('21', 'cerulean', '天蓝主题', 'theme', '主题方案', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('22', 'readable', '橙色主题', 'theme', '主题方案', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('23', 'united', '红色主题', 'theme', '主题方案', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('24', 'flat', 'Flat主题', 'theme', '主题方案', '50', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('25', '1', '封面', 'shop_file_type', '购物文件类型', '10', '0', '1', '2015-11-08 23:03:09', '1', '2015-11-08 23:03:09', '');
INSERT INTO `sys_dict` VALUES ('26', '2', '轮播', 'shop_file_type', '购物文件类型', '20', '0', '1', '2015-11-08 23:03:23', '1', '2015-11-08 23:03:23', '');
INSERT INTO `sys_dict` VALUES ('27', '4', '推荐购买', 'shop_file_type', '购物文件类型', '40', '0', '1', '2015-11-08 23:03:31', '1', '2015-11-08 23:03:31', '');
INSERT INTO `sys_dict` VALUES ('28', '1', '显示', 'show_hide', '显示/隐藏', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('29', '0', '隐藏', 'show_hide', '显示/隐藏', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('30', '1', '景点推荐', 'scenic_type', '景点类型', '10', '0', '1', '2015-11-08 21:24:43', '1', '2015-11-08 21:24:43', '');
INSERT INTO `sys_dict` VALUES ('31', '2', '自然风光', 'scenic_type', '景点类型', '20', '0', '1', '2015-11-08 21:24:54', '1', '2015-11-08 21:24:54', '');
INSERT INTO `sys_dict` VALUES ('32', '3', '人文景观', 'scenic_type', '景点类型', '30', '0', '1', '2015-11-08 21:25:03', '1', '2015-11-08 21:25:03', '');
INSERT INTO `sys_dict` VALUES ('33', '4', '其他', 'scenic_type', '景点类型', '40', '0', '1', '2015-11-08 21:25:12', '1', '2015-11-08 21:25:12', '');
INSERT INTO `sys_dict` VALUES ('34', '1', '系统管理', 'sys_user_type', '用户类型', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('35', '2', '普通用户', 'sys_user_type', '用户类型', '20', '0', '1', '2013-05-27 08:00:00', '1', '2015-11-11 17:10:30', '');
INSERT INTO `sys_dict` VALUES ('36', '1', '封面', 'guide_file_type', '导购文件类型', '10', '0', '1', '2015-11-08 23:04:10', '1', '2015-11-08 23:04:10', '');
INSERT INTO `sys_dict` VALUES ('37', '2', '轮播', 'guide_file_type', '导购文件类型', '20', '0', '1', '2015-11-08 23:04:17', '1', '2015-11-08 23:04:17', '');
INSERT INTO `sys_dict` VALUES ('38', '1', '封面', 'food_file_type', '美食文件类型', '10', '0', '1', '2015-11-02 16:04:56', '1', '2015-11-02 16:04:56', '');
INSERT INTO `sys_dict` VALUES ('39', '2', '轮播', 'food_file_type', '美食文件类型', '20', '0', '1', '2015-11-02 16:05:23', '1', '2015-11-02 16:05:23', '');
INSERT INTO `sys_dict` VALUES ('40', '1', '封面', 'scenic_file_type', '景点文件类型', '10', '0', '1', '2015-11-08 21:25:47', '1', '2015-11-08 21:25:47', '');
INSERT INTO `sys_dict` VALUES ('41', '2', '轮播', 'scenic_file_type', '景点文件类型', '20', '0', '1', '2015-11-08 21:25:55', '1', '2015-11-08 21:25:55', '');
INSERT INTO `sys_dict` VALUES ('42', '1', '综合', 'shop_type', '购物类型', '10', '0', '1', '2015-11-08 23:02:12', '1', '2015-11-08 23:02:12', '');
INSERT INTO `sys_dict` VALUES ('43', '2', '免税店', 'shop_type', '购物类型', '20', '0', '1', '2015-11-08 23:02:21', '1', '2015-11-08 23:02:21', '');
INSERT INTO `sys_dict` VALUES ('44', '3', '奢侈品', 'shop_type', '购物类型', '30', '0', '1', '2015-11-08 23:02:28', '1', '2015-11-08 23:02:28', '');
INSERT INTO `sys_dict` VALUES ('45', '4', '特产', 'shop_type', '购物类型', '40', '0', '1', '2015-11-08 23:02:37', '1', '2015-11-08 23:02:37', '');
INSERT INTO `sys_dict` VALUES ('46', '5', '零食', 'shop_type', '购物类型', '50', '0', '1', '2015-11-08 23:02:45', '1', '2015-11-08 23:02:45', '');
INSERT INTO `sys_dict` VALUES ('47', '1', '是', 'yes_no', '是/否', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('48', '0', '否', 'yes_no', '是/否', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('49', '1', '接入日志', 'sys_log_type', '日志类型', '10', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('50', '2', '异常日志', 'sys_log_type', '日志类型', '20', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('51', '0', '导游', 'tourist_type', '游客类型', '10', '0', '1', '2015-10-27 16:30:03', '1', '2015-10-27 16:30:03', '');
INSERT INTO `sys_dict` VALUES ('52', '1', '游客', 'tourist_type', '游客类型', '20', '0', '1', '2015-10-27 16:30:22', '1', '2015-10-27 16:30:22', '');
INSERT INTO `sys_dict` VALUES ('53', '1', '出租车预定', 'car_type', '租车类型', '10', '0', '1', '2015-10-25 19:40:37', '1', '2015-10-25 21:05:45', '');
INSERT INTO `sys_dict` VALUES ('54', '2', '预约接送机', 'car_type', '租车类型', '20', '0', '1', '2015-10-25 19:41:35', '1', '2015-10-25 21:05:55', '');
INSERT INTO `sys_dict` VALUES ('55', '3', '旅游专车', 'car_type', '租车类型', '30', '0', '1', '2015-10-25 19:42:02', '1', '2015-10-25 21:06:05', '');
INSERT INTO `sys_dict` VALUES ('56', '0', '未知', 'sex', '性别', '10', '0', '1', '2015-11-20 08:00:00', '1', '2015-11-20 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('57', '1', '男', 'sex', '性别', '20', '0', '1', '2015-11-20 08:00:00', '1', '2015-11-20 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('58', '2', '女', 'sex', '性别', '30', '0', '1', '2015-11-20 08:00:00', '1', '2015-11-20 08:00:00', '');
INSERT INTO `sys_dict` VALUES ('59', '1', '旅行团用户', 'code_rule', '编码规则', '10', '0', '1', '2015-11-20 08:00:00', '1', '2015-11-20 08:00:00', '');
