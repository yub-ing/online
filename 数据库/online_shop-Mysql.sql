-- ----------------------------
-- TODO1 创建Mysql数据库和表，只需执行一次
-- ----------------------------
Create Database If Not Exists online_shop Character Set UTF8;
use online_shop;

create table tb_admin (
    id varchar(32) comment '主键',
    username varchar(20) comment '用户名',
    password varchar(20) comment '密码',
    PRIMARY KEY (`id`)
)comment '管理员表';
-- 默认插入一条管理员 数据
insert into tb_admin (id,username,password) values('1234567890','1','1');





-- TODO3 创建用户并授权
CREATE USER 'online_shop'@'%' IDENTIFIED BY 'online_shop';
GRANT ALL ON online_shop.* TO 'online_shop'@'%';

