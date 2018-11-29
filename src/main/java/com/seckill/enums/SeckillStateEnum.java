package com.seckill.enums;
/**
 * ��ɱ״̬ö��
 * @author Administrator
 *
 */
public enum SeckillStateEnum {
	SUCCESS(1,"��ɱ�ɹ�"),
	END(0,"��ɱ����"),
	REPEAT_KILL(-1,"�ظ���ɱ"),
	INNER_ERROR(-2,"ϵͳ�쳣"),
	DATA_REWRITE(-3,"���ݴ۸�");
	private int state;
	private String stateinfo;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateinfo() {
		return stateinfo;
	}
	public void setStateinfo(String stateinfo) {
		this.stateinfo = stateinfo;
	}
	private SeckillStateEnum(int state, String stateinfo) {
		this.state = state;
		this.stateinfo = stateinfo;
	}
	
	public static SeckillStateEnum stateOf(int index) {
		for(SeckillStateEnum state : values()) {
			if(state.getState() == index) {
				return state;
			}
		}
		return null;
		
	}
	
	
}
