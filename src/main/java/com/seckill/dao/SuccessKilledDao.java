package com.seckill.dao;

import com.seckill.entity.SuccessKilled;

/**
 * 
 * @author Administrator
 *
 */
public interface SuccessKilledDao {
	//���빺����ϸ��ͨ�������𽥹����ظ�
	int insertSuccessKilled(long seckillId,long userPhone);
	//����ID����SuccessKilled��Я����ɱ��Ʒʵ��
	SuccessKilled queryByIdWithSeckill(long seckillId,long userPhone);
}
