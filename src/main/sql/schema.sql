--�������ݿ�
create database seckill
--ʹ�����ݿ�
use seckill;
-- ������������
create table seckill(
`seckill_id` bigint not null auto_increment comment '��Ʒ���id',
`name` varchar(120) not null comment '��Ʒ����',
`number` int not null comment '�������',
`start_time` timestamp not null comment '��ʼʱ��',
`end_time` timestamp not null comment '����ʱ��',
`create_time` timestamp not null default CURRENT_TIMESTAMP comment '����ʱ��',
PRIMARY KEY(seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)Engine=InnoDB auto_increment=1000 DEFAULT CHARSET=UTF8 COMMENT='��ɱ����';

-- �����ʼ������
insert into 
 seckill(name,number,start_time,end_time)
values
 ("100Ԫ��ɱiphone6",100,'2018-9-10 00:00:00','2018-9-11 00:00:00'),
 ("10Ԫ��ɱiphone1",200,'2018-9-10 00:00:00','2018-9-11 00:00:00'),
 ("1000Ԫ��ɱiphone8",300,'2018-9-10 00:00:00','2018-9-11 00:00:00'),
 ("2000Ԫ��ɱС��8",400,'2018-9-10 00:00:00','2018-9-11 00:00:00');
 
 -- ��ɱ�ɹ���ϸ��
 create table success_killed(
 `seckill_id` int not null comment '��ɱ��Ʒid',
 `user_phone` int not null comment '�û��ֻ���',
 `state` tinyint not null default -1 comment '״̬��ʶ -1����Ч 0���ɹ� 1���Ѹ���',
 `create_time` timestamp not null comment '����ʱ��',
 primary key(seckill_id,user_phone),
 key idx_create_time(create_time)
 )Engine=InnoDB DEFAULT CHARSET=UTF8 COMMENT='��ɱ�ɹ���¼��';