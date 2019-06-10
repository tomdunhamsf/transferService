package com.revolut.takehome.service;

public final class TransferResult{
	private Boolean success;
	private String reason;
	public TransferResult(Boolean success,String reason) {
		this.success=success;
		this.reason=reason;
	}
	public Boolean getSuccess() {
		return success;
	}
	public String getReason() {
		return reason;
	}
	
}