package com.seckill.exception;
/**
 * ��ɱ�ظ��쳣(������һ��)
 * @author Administrator
 *
 */
public class RepeatKillException extends RuntimeException{

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
