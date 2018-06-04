package com.uton.carsokApi.event.constants;

public class OperateResult {
	private boolean success;

	private String message;

	private Object data;
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object obj) {
		this.data = obj;
	}
	public OperateResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public OperateResult(boolean success, String message,Object data) {
		this.success = success;
		this.message = message;
		this.data=data;
	}
	public OperateResult(){
		
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

}
