--创建数据库
create database seckill
--使用数据库
use seckill;
-- 创建描述库存表
create table seckill(
`seckill_id` bigint not null auto_increment comment '商品库存id',
`name` varchar(120) not null comment '商品名称',
`number` int not null comment '库存数量',
`start_time` timestamp not null comment '开始时间',
`end_time` timestamp not null comment '结束时间',
`create_time` timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
PRIMARY KEY(seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)Engine=InnoDB auto_increment=1000 DEFAULT CHARSET=UTF8 COMMENT='秒杀库存表';

-- 插入初始化数据
insert into 
 seckill(name,number,start_time,end_time)
values
 ("100元秒杀iphone6",100,'2018-9-10 00:00:00','2018-9-11 00:00:00'),
 ("10元秒杀iphone1",200,'2018-9-10 00:00:00','2018-9-11 00:00:00'),
 ("1000元秒杀iphone8",300,'2018-9-10 00:00:00','2018-9-11 00:00:00'),
 ("2000元秒杀小米8",400,'2018-9-10 00:00:00','2018-9-11 00:00:00');
 
 -- 秒杀成功明细表
 create table success_killed(
 `seckill_id` int not null comment '秒杀商品id',
 `user_phone` int not null comment '用户手机号',
 `state` tinyint not null default -1 comment '状态标识 -1：无效 0：成功 1：已付款',
 `create_time` timestamp not null comment '创建时间',
 primary key(seckill_id,user_phone),
 key idx_create_time(create_time)
 )Engine=InnoDB DEFAULT CHARSET=UTF8 COMMENT='秒杀成功记录表';