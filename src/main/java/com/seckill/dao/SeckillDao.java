package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.Seckill;

public interface SeckillDao {
	//�����
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	//����id��ѯ��ɱ����
	Seckill queryById(long seckillId);
	//����ƫ������ѯ��ɱ�б�
	//javaû�б����βεļ�¼��queryAll(int offset,int limit) -->  query(arg0,arg1)
	//ͨ��ibatis�ṩ��paramע�������ø��βε�����
	List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
