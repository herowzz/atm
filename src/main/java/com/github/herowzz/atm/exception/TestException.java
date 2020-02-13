package com.github.herowzz.atm.exception;

/**
 * 业务封装RunTime异常类
 * 
 * @author wangzz
 * 
 */
public class TestException extends RuntimeException {

	private static final long serialVersionUID = 5439268283599342126L;

	private int code = 0;

	public TestException() {
		super();
	}

	public TestException(String message) {
		super(message);
	}

	public TestException(String message, int code) {
		super(message);
		this.code = code;
	}

	public TestException(Throwable cause) {
		super(cause);
	}

	public TestException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getCode() {
		return code;
	}
}
