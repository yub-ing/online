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
create table tb_cart (
    id varchar(32) comment '主键',
    userid varchar(32) comment '用户',
    goodsid varchar(32) comment '商品',
    tustp varchar(255) comment '商品图片',
    price double comment '价格',
    count int comment '数量',
    zongj double comment '总价',
    createtime datetime comment '加购时间',
    PRIMARY KEY (`id`)
)comment '购物车表';
create table tb_orderdetail (
    id varchar(32) comment '主键',
    userid varchar(32) comment '用户',
    goodsid varchar(32) comment '商品',
    mid varchar(32) comment '商家',
    tustp varchar(255) comment '商品图片',
    price double comment '价格',
    count int comment '数量',
    zongj double comment '总价',
    createtime datetime comment '时间',
    PRIMARY KEY (`id`)
)comment '订单详情表';
create table tb_order (
    id varchar(32) comment '主键',
    userid varchar(32) comment '用户',
    numb varchar(255) comment '订单号',
    sname varchar(20) comment '收货人',
    stele varchar(11) comment '收货人电话',
    saddr varchar(255) comment '收货人地址',
    dingdbz varchar(255) comment '订单备注',
    createtime datetime comment '下单时间',
    mid varchar(32) comment '商家',
    content varchar(255) comment '订单内容',
    details varchar(255) comment '订单详情',
    price double comment '总价格',
    state varchar(255) comment '状态',
    wuliu varchar(255) comment '物流单号',
    wulxx varchar(255) comment '物流信息',
    pingj varchar(255) comment '评价',
    PRIMARY KEY (`id`)
)comment '用户订单表';
create table tb_afterSell (
    id varchar(32) comment '主键',
    userid varchar(32) comment '用户',
    oid varchar(32) comment '订单号',
    typer varchar(255) comment '售后类型',
    content varchar(255) comment '要求售后内容',
    pic varchar(255) comment '产品问题拍照上传',
    createtime datetime comment '售后申请时间',
    state varchar(255) comment '处理状态',
    chuljg varchar(255) comment '处理结果',
    PRIMARY KEY (`id`)
)comment '售后申请表';
create table tb_userComment (
    id varchar(32) comment '主键',
    userid varchar(255) comment '用户编号',
    username varchar(255) comment '用户名',
    rolech varchar(255) comment '用户角色',
    content varchar(255) comment '内容',
    createtime datetime comment '发布时间',
    ctid varchar(255) comment '内容编号',
    type varchar(255) comment '内容类型',
    status varchar(255) comment '状态',
    PRIMARY KEY (`id`)
)comment '评论表';
create table tb_notice (
    id varchar(32) comment '主键',
    title varchar(255) comment '标题',
    content varchar(255) comment '内容',
    createtime datetime comment '发布时间',
    PRIMARY KEY (`id`)
)comment '公告表';



create table tb_star (
    id varchar(255) comment '主键',
    itemid varchar(255) comment '收藏项',
    itemtype varchar(255) comment '收藏类型',
    userid varchar(255) comment '用户',
    userrole varchar(255) comment '角色',
    type varchar(10) comment '点赞或收藏',
    createtime datetime comment '创建时间',
    PRIMARY KEY (`id`)
)comment '收藏表';


-- TODO3 创建用户并授权
CREATE USER 'online_shop'@'%' IDENTIFIED BY 'online_shop';
GRANT ALL ON online_shop.* TO 'online_shop'@'%';

