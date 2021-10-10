package com.myspringapp.SpringApp.Entity;

import org.springframework.http.HttpStatus;

public class ErrorMssg {

	private HttpStatus status;
	private String mssg;
	public HttpStatus getStatus() {
		return status;
	}
	public ErrorMssg(HttpStatus status, String mssg) {
		super();
		this.status = status;
		this.mssg = mssg;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMssg() {
		return mssg;
	}
	public void setMssg(String mssg) {
		this.mssg = mssg;
	}

}
