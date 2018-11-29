package com.seckill.dao;

import com.seckill.entity.SuccessKilled;

/**
 * 
 * @author Administrator
 *
 */
public interface SuccessKilledDao {
	//插入购买明细，通过联合逐渐过滤重复
	int insertSuccessKilled(long seckillId,long userPhone);
	//根据ID查找SuccessKilled并携带秒杀产品实体
	SuccessKilled queryByIdWithSeckill(long seckillId,long userPhone);
}
