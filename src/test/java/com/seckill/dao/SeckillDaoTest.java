package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dao.cache.RedisCache;
import com.seckill.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	//对SeckillDao进行注入
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private RedisCache redisDao;
	
	@Test
	public void testGetSeckillByRedis() {
		Long seckillId = 1001L;
		//从redis中获取该对象，如果没有就从数据库中获取并且存入redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		//如果缓存中没有就从数据库中获取
		if(seckill == null) {
			seckill = seckillDao.queryById(seckillId);
		}
		redisDao.putSeckill(seckill);
		Seckill seckill1 = redisDao.getSeckill(seckillId);
		System.out.println(seckill1);
	}
	
	@Test
	public void testReduceNumber() {
		Date date = new Date();
		int count = seckillDao.reduceNumber(1000L, date);
		System.out.println("updateCount"+count);
	}
	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
		
	}
	@Test
	public void testQueryAll() {
		List<Seckill> list = seckillDao.queryAll(0, 100);
		for(Seckill seckill : list) {
			System.out.println(seckill);
		}
	}
}
