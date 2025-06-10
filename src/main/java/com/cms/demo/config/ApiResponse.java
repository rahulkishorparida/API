package com.cms.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse<T> {
    
	private boolean success;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private T data;
	
	public ApiResponse(boolean success,String message) {
		this.message=message;
		this.success= success;
		
	}
    public ApiResponse(boolean success, String message, T data) {
	this.message= message;
	this.success= success;
	this.data=data;
	
    }

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	
}
