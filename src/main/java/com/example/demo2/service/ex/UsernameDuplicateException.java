package com.example.demo2.service.ex;

import org.springframework.stereotype.Component;

/**
 * 用于处理用户名占用异常
 * @author soft01
 *
 */
public class UsernameDuplicateException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public UsernameDuplicateException() {
		super();
	}

	public UsernameDuplicateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameDuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameDuplicateException(String message) {
		super(message);
	}

	public UsernameDuplicateException(Throwable cause) {
		super(cause);
	}
	
	

}
