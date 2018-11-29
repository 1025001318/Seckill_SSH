package com.seckill.service;

import java.util.List;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

/**
 * 秒杀的服务层接口
 * @author Administrator
 *
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀列表
	 * @return
	 */
	List<Seckill> getSeckillList();
	/**
	 * 通过id来获取秒杀信息
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	/**
	 * 秒杀开启时输出秒杀接口地址
	 * 否则输出系统时间和秒杀时间
	 * 主页面点击秒杀之后进入的页面所呈现的信息
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	SeckillExecution executionSeckill(long seckillId,long userPhone,String md5) 
			throws SeckillException,SeckillCloseException,RepeatKillException;
}
