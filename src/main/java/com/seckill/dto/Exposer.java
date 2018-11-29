package com.seckill.dto;
/**
 * dto包存储  表示web和service传递的数据封装
 * Exposer  封装秒杀是否开启和是否开启的相关信息
 * @author Administrator
 *
 */
public class Exposer {
	//是否开启的状态
	private boolean exposed;
	//加密措施
	private String md5;
	
	private long seckillId;
	
	private long start;
	private long end;
	private long now;
	
	/**
	 * 开启
	 * @param exposed
	 * @param md5
	 * @param seckillId
	 */
	public Exposer(boolean exposed, String md5, long seckillId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}
	/**
	 * 没有开启
	 * @param exposed
	 * @param start
	 * @param end
	 * @param now
	 */
	public Exposer(boolean exposed, long seckillId, long start, long end, long now) {
		super();
		this.exposed = exposed;
		this.seckillId =seckillId;
		this.start = start;
		this.end = end;
		this.now = now;
	}
	public boolean isExposed() {
		return exposed;
	}
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public long getNow() {
		return now;
	}
	public void setNow(long now) {
		this.now = now;
	}
	public Exposer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Exposer(boolean exposed, long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}
	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId=" + seckillId + ", start=" + start
				+ ", end=" + end + ", now=" + now + "]";
	}
	
}
