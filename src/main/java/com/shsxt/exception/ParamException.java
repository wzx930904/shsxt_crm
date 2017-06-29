package com.shsxt.exception;

public class ParamException extends RuntimeException {

	private int errorCode;

	public ParamException() {
		super();
	}

	public ParamException(String message) {
		super(message);
	}
	
	public ParamException(Integer errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
