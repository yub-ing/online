-- ----------------------------
-- TODO1 创建Mysql数据库和表，只需执行一次
-- ----------------------------
Create Database If Not Exists online_shop Character Set UTF8;
use online_shop;

create table tb_admin (
    id varchar(32) comment '主键',
    username varchar(20) comment '用户名',
    password varchar(20) comment '密码',
    name varchar(12) comment '姓名',
    tele varchar(11) comment '电话',
    PRIMARY KEY (`id`)
)comment '管理员表';
-- 默认插入一条管理员 数据
insert into tb_admin (id,username,password) values('1234567890','1','1');
create table tb_merchant (
    id varchar(32) comment '主键',
    username varchar(20) comment '用户名',
    password varchar(20) comment '密码',
    name varchar(18) comment '商家名',
    avatar varchar(255) comment '头像',
    tele varchar(11) comment '电话',
    xiaodjj varchar(255) comment '小店简介',
    zhuynr varchar(255) comment '主营内容',
    PRIMARY KEY (`id`)
)comment '商家表';
create table tb_user (
    id varchar(32) comment '主键',
    username varchar(20) comment '用户名',
    password varchar(20) comment '密码',
    name varchar(18) comment '姓名',
    avatar varchar(255) comment '头像',
    gender varchar(10) comment '性别',
    age int comment '年龄',
    tele varchar(11) comment '电话',
    youx varchar(255) comment '邮箱',
    place varchar(255) comment '地址',
    PRIMARY KEY (`id`)
)comment '用户表';
create table tb_goods (
    id varchar(32) comment '主键',
    showpic varchar(255) comment '首页图片',
    showtitle varchar(255) comment '商品名称',
    showdesc TEXT comment '简介',
    leix varchar(32) comment '类型',
    mid varchar(32) comment '商家',
    price double comment '价格',
    showdetail TEXT comment '商品内容',
    count int comment '库存数量',
    publishtime datetime comment '发布时间',
    vv varchar(255) comment '展示',
    PRIMARY KEY (`id`)
)comment '商品信息表';
create table tb_stype (
    id varchar(32) comment '主键',
    name varchar(18) comment '类型名',
    describtion varchar(255) comment '说明',
    PRIMARY KEY (`id`)
)comment '商品类型表';





-- TODO3 创建用户并授权
CREATE USER 'online_shop'@'%' IDENTIFIED BY 'online_shop';
GRANT ALL ON online_shop.* TO 'online_shop'@'%';

