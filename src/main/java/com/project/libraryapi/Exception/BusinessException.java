package com.project.libraryapi.Exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	
	public BusinessException(String m) {
		super(m);
	}

}
