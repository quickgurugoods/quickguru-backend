package com.quickguru.exception;

public class QuickGuruException extends Exception {
	
    private static final long serialVersionUID = 1L;
    
    public QuickGuruException(String message) {
    	super(message);
	}
    
    public QuickGuruException(Throwable throwable) {
        super(throwable);
    }

    public QuickGuruException(String message, Throwable throwable) {
    	super(message, throwable);
    }
}