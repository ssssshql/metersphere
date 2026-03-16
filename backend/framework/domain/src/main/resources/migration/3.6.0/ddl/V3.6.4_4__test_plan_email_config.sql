-- set innodb lock wait timeout
SET SESSION innodb_lock_wait_timeout = 7200;

CREATE TABLE IF NOT EXISTS test_plan_email_config(
    `test_plan_id` VARCHAR(50) NOT NULL   COMMENT '测试计划ID' ,
    `email_recipients` VARCHAR(2000)    COMMENT '邮件接收人，多个邮箱以逗号分隔' ,
    `create_time` BIGINT NOT NULL   COMMENT '创建时间' ,
    `update_time` BIGINT NOT NULL   COMMENT '更新时间' ,
    `create_user` VARCHAR(50) NOT NULL   COMMENT '创建人' ,
    `update_user` VARCHAR(50) NOT NULL   COMMENT '更新人' ,
    PRIMARY KEY (test_plan_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '测试计划邮件接收人配置';

-- set innodb lock wait timeout to default
SET SESSION innodb_lock_wait_timeout = DEFAULT;
