package com.seckill.service;

import java.util.List;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

/**
 * ��ɱ�ķ����ӿ�
 * @author Administrator
 *
 */
public interface SeckillService {
	/**
	 * ��ѯ������ɱ�б�
	 * @return
	 */
	List<Seckill> getSeckillList();
	/**
	 * ͨ��id����ȡ��ɱ��Ϣ
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	/**
	 * ��ɱ����ʱ�����ɱ�ӿڵ�ַ
	 * �������ϵͳʱ�����ɱʱ��
	 * ��ҳ������ɱ֮������ҳ�������ֵ���Ϣ
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	SeckillExecution executionSeckill(long seckillId,long userPhone,String md5) 
			throws SeckillException,SeckillCloseException,RepeatKillException;
}
