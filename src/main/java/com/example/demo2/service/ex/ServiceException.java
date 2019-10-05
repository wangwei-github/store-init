package com.example.demo2.service.ex;
/**
 * 自定义业务层异常父类，用于自定义的其他异常类的解耦，继承runtimeException
 * @author soft01
 *
 */
public class ServiceException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	
}
