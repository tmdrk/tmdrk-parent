CREATE TABLE `ACL_MENUS` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `menu_name` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `menu_desc` varchar(255) DEFAULT NULL COMMENT '菜单描述',
  `ancestry` varchar(50) DEFAULT NULL COMMENT '父关系',
  `ancestry_depth` tinyint(4) DEFAULT NULL COMMENT '深度',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '菜单访问地址',
  `leaf` tinyint(1) DEFAULT NULL COMMENT '是否叶子节点',
  `weight` int(11) DEFAULT NULL COMMENT '权重,正序排',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ACE_USER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `age` tinyint(4) DEFAULT NULL COMMENT '年龄',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号',
  `idcard` varchar(20) NOT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;