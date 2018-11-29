package com.seckill.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceImpTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService service;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = service.getSeckillList();
		logger.info("list={}", list);
		System.out.println(list);
	}

	@Test
	public void testGetById() {
		long seckillId = 1000L;
		Seckill seckill = service.getById(seckillId);
		logger.info("serckill(1000L)={}"+seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long seckillId = 1000L;
		Exposer exposer = service.exportSeckillUrl(seckillId);
		logger.info("exposer={}"+ exposer);
	}

	@Test
	public void testExecutionSeckill() {
		long seckillId = 1000L;
		long userPhone = 1573565937L;
		String md5 = "6cd54edf97b19799abeced0efd16e512";
		SeckillExecution seckillExcution = service.executionSeckill(seckillId, userPhone, md5);
		logger.info("seckillExcution={}"+seckillExcution);
	}

}
