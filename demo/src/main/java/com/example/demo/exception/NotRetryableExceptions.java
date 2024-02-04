package com.example.demo.exception;

public class NotRetryableExceptions extends RuntimeException {
	
	public NotRetryableExceptions(String message) {
		super(message);
	}


}
