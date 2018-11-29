package com.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.entity.SuccessKilled;

//SuccessKilledDao测试类
//在spring环境下运行
@RunWith(SpringJUnit4ClassRunner.class)
//注入spring配置文件
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {
	@Autowired
	private SuccessKilledDao dao;
	
	@Test
	public void insertSuccessKilledTest() {
		long id =1000L;
		long phone = 1573565938L;
		int i = dao.insertSuccessKilled(id, phone);
		System.out.println("insertCount:"+i);
	}
	
	@Test
	public void queryByIdWithSeckill() {
		long id = 1000L;
		long phone = 1573565938L;
		SuccessKilled sk = dao.queryByIdWithSeckill(id,phone);
		System.out.println(sk);
		System.out.println(sk.getSeckill());
		
	}
}
