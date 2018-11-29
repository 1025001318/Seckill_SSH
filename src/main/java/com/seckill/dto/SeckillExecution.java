package com.seckill.dto;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnum;

/**
 * ��װ��ɱִ�к�Ľ����Ϣ
 * @author Administrator
 *
 */
public class SeckillExecution {
	private long seckillId;
	//��ɱ���״̬
	private int state;
	//״̬��Ϣ
	private String stateInfo;
	//��ɱ�ɹ���
	private SuccessKilled successKilled;

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}
	/**
	 * ʧ�ܵ���Ϣ
	 * @param seckillId
	 * @param state
	 * @param stateInfo
	 */
	public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = seckillStateEnum.getState();
		this.stateInfo = seckillStateEnum.getStateinfo();
	}
	/**
	 * �ɹ�����Ϣ
	 * @param seckillId
	 * @param state
	 * @param stateInfo
	 * @param successKilled
	 */
	public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = seckillStateEnum.getState();
		this.stateInfo = seckillStateEnum.getStateinfo();
		this.successKilled = successKilled;
	}

	public SeckillExecution() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}
	
}
