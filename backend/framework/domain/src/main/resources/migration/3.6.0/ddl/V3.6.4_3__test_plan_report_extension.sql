CREATE TABLE IF NOT EXISTS `test_plan_report_extension` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `report_id` varchar(50) NOT NULL COMMENT '报告ID',
  `deploy_time` bigint(13) DEFAULT NULL COMMENT '部署时间',
  `deploy_version` varchar(100) DEFAULT NULL COMMENT '部署版本',
  PRIMARY KEY (`id`),
  KEY `idx_report_id` (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试计划报告扩展信息表';
