package com.seckill.exception;
/**
 * 秒杀重复异常(运行期一场)
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
