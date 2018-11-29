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

//ͨ��ע��ķ�ʽ������������ע��
@Component
public class SeckillServiceImp implements SeckillService {
	//dao�ӿ���spring-dao.xml�ļ����Ѿ�ʵ������
	@Autowired
	private SeckillDao sDao;
	@Autowired
	private SuccessKilledDao skDao;
	@Autowired
	private RedisCache reidsDao;
	
	private Logger logger = LoggerFactory.getLogger(SeckillServiceImp.class);
	//md5��ֵ�����ڻ���
	private final String slat = "adfa4fsd54f83";
	/**
	 * ��ȡ������ɱ�б�
	 */
	public List<Seckill> getSeckillList() { 
		return sDao.queryAll(0, 100);
	}
	/**
	 * ��ȡ��Ӧ����ɱ��Ϣ
	 */
	public Seckill getById(long seckillId) {
		return sDao.queryById(seckillId);

	}
	/**
	 * ͨ����ɱid��ȡ��ɱ�Ƿ����Ķ�Ӧ��Ϣ
	 */
	public Exposer exportSeckillUrl(long seckillId) {
		//��redis�л�ȡ�ö������û�оʹ����ݿ��л�ȡ���Ҵ���redis
		Seckill seckill = reidsDao.getSeckill(seckillId);
		//���������û�оʹ����ݿ��л�ȡ
		if(seckill == null) {
			seckill = sDao.queryById(seckillId);
		}
		//������ݿ���Ҳû�иö��󣬾�˵�������ڸ���ɱ��Ϣ
		if(seckill == null) {//�ж��Ƿ���ڸ���ɱ��Ϣ
			return new Exposer(false,seckillId);
		}
		reidsDao.putSeckill(seckill);
		Date date = new Date();
		long now = date.getTime();
		//�жϸ���ɱ�Ƿ���
		if(seckill.getStartTime().getTime() > now || seckill.getEndTime().getTime() < now) {
			return new Exposer(false,seckillId,seckill.getStartTime().getTime(),seckill.getEndTime().getTime(),now);
		}
		String md5 = getMd5(seckillId);
		return new Exposer(true,md5, seckillId);
		

	}
	
	/**
	 * ��ȡmd5
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
	 * ��ȡ��ɱ�Ƿ�ɹ�����Ϣ
	 */
	@Transactional
	public SeckillExecution executionSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException {
		//�ж�md5
		if(md5 == null || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("seckill data rewrite.");
		}
		Date now = new Date();
		try {
			//�����
			int reduceCount = sDao.reduceNumber(seckillId, now);
			//�ж��Ƿ���ɱ�ɹ�
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
