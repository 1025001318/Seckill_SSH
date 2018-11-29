package com.seckill.service.Imp;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dao.cache.RedisCache;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;

//通过注解的方式来配置依赖和注入
@Component
public class SeckillServiceImp implements SeckillService {
	//dao接口在spring-dao.xml文件中已经实现配置
	@Autowired
	private SeckillDao sDao;
	@Autowired
	private SuccessKilledDao skDao;
	@Autowired
	private RedisCache reidsDao;
	
	private Logger logger = LoggerFactory.getLogger(SeckillServiceImp.class);
	//md5颜值，用于混淆
	private final String slat = "adfa4fsd54f83";
	/**
	 * 获取所有秒杀列表
	 */
	public List<Seckill> getSeckillList() { 
		return sDao.queryAll(0, 100);
	}
	/**
	 * 获取对应的秒杀信息
	 */
	public Seckill getById(long seckillId) {
		return sDao.queryById(seckillId);

	}
	/**
	 * 通过秒杀id获取秒杀是否开启的对应信息
	 */
	public Exposer exportSeckillUrl(long seckillId) {
		//从redis中获取该对象，如果没有就从数据库中获取并且存入redis
		Seckill seckill = reidsDao.getSeckill(seckillId);
		//如果缓存中没有就从数据库中获取
		if(seckill == null) {
			seckill = sDao.queryById(seckillId);
		}
		//如果数据库中也没有该对象，就说明不存在该秒杀信息
		if(seckill == null) {//判断是否存在该秒杀信息
			return new Exposer(false,seckillId);
		}
		reidsDao.putSeckill(seckill);
		Date date = new Date();
		long now = date.getTime();
		//判断该秒杀是否开启
		if(seckill.getStartTime().getTime() > now || seckill.getEndTime().getTime() < now) {
			return new Exposer(false,seckillId,seckill.getStartTime().getTime(),seckill.getEndTime().getTime(),now);
		}
		String md5 = getMd5(seckillId);
		return new Exposer(true,md5, seckillId);
		

	}
	
	/**
	 * 获取md5
	 * 
	 * @param seckillId
	 * @return
	 */
	private String getMd5(long seckillId) {
		String base = seckillId+"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	/**
	 * 获取秒杀是否成功的信息
	 */
	@Transactional
	public SeckillExecution executionSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException {
		//判断md5
		if(md5 == null || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("seckill data rewrite.");
		}
		Date now = new Date();
		try {
			//减库存
			int reduceCount = sDao.reduceNumber(seckillId, now);
			//判断是否秒杀成功
			if(reduceCount <= 0) {
				throw new SeckillCloseException("seckill is close.");
			}else {
				int insertCount = skDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0) {
					throw new RepeatKillException("seckill repeat.");
				}else {
					SuccessKilled successKilled = skDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
				}
			}
		} catch(SeckillCloseException e1) {
			throw e1;
		} catch(RepeatKillException e2) {
			throw e2;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e 	);
			throw new SeckillException("seckill inner error"+e.getMessage());
		}
	}

}
