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
 * 包含存取两个方法，以二进制的形式将对象进行存取
 * @author Administrator
 *
 */
public class RedisCache {
	private JedisPool jedisPool;
	//初始化jedispool对象
	public  RedisCache(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}
	//通过id来获取缓存的seckill对象
	public Seckill getSeckill(Long id) {
		Jedis jedis = jedisPool.getResource();
		
		//给定key来查找value并进行反序列化
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
	
	//通过序列化来将传入的对象保存在redis中
	//使用了ByteArrayOutputStream 和ObjectOutputStream,前者作用是获取对象序列化后的二进制数组
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
