package com.quickguru.exception;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.experimental.Accessors;
 
@Accessors(chain = true)
@XmlRootElement(name = "error")
public class ErrorResponse extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public ErrorResponse(String message, List<String> details) {
    	super();
        this.message = message;
        this.details = details;
    }
    
    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }
 
    private final String message;
    private final List<String> details;
    
    @Override
	public String getMessage() {
		return message;
	}
	public List<String> getDetails() {
		return details;
	}
}

