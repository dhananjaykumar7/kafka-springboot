package com.example.demo.exception;

public class RetryableExceptions extends RuntimeException {
	
	public RetryableExceptions(String message) {
		super(message);
		
	}

}
