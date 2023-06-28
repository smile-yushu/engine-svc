CREATE TABLE `communication_protocol_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `log_type` varchar(50) DEFAULT NULL COMMENT '日志类型',
  `log_level` varchar(50) DEFAULT NULL COMMENT '日志级别',
  `host` varchar(50) DEFAULT '' COMMENT '地址',
  `port` int(11) DEFAULT NULL COMMENT '端口',
  `remark` varchar(255) DEFAULT NULL COMMENT '监听通讯记录',
  `opt_code` text COMMENT '操作代码',
  `other` varchar(255) DEFAULT NULL COMMENT '其他异常备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COMMENT='通讯协议日志';