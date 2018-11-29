package com.seckill.dao.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.seckill.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * ������ȡ�����������Զ����Ƶ���ʽ��������д�ȡ
 * @author Administrator
 *
 */
public class RedisCache {
	private JedisPool jedisPool;
	//��ʼ��jedispool����
	public  RedisCache(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}
	//ͨ��id����ȡ�����seckill����
	public Seckill getSeckill(Long id) {
		Jedis jedis = jedisPool.getResource();
		
		//����key������value�����з����л�
		try {
			String key = "seckill" + id;
			byte[] bytes = jedis.get(key.getBytes());
			if(bytes != null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				ObjectInputStream os = new ObjectInputStream(bis);
				Seckill seckill = (Seckill)os.readObject();
				return seckill;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//ͨ�����л���������Ķ��󱣴���redis��
	//ʹ����ByteArrayOutputStream ��ObjectOutputStream,ǰ�������ǻ�ȡ�������л���Ķ���������
	public String putSeckill(Seckill seckill) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(seckill);
			byte[] bytes = bos.toByteArray();
			if(bytes != null) {
				Jedis jedis = jedisPool.getResource();
				String i = jedis.set(("seckill"+seckill.getSeckillId()).getBytes(), bytes);
				return i;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
