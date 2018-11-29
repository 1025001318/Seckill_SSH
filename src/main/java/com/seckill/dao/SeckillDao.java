package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.Seckill;

public interface SeckillDao {
	//减库存
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	//根据id查询秒杀对象
	Seckill queryById(long seckillId);
	//根据偏移量查询秒杀列表
	//java没有保存形参的记录：queryAll(int offset,int limit) -->  query(arg0,arg1)
	//通过ibatis提供的param注解来设置该形参的名字
	List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
