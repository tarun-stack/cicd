package com.myspringapp.SpringApp.Error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.myspringapp.SpringApp.Entity.ErrorMssg;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	public RestResponseEntityExceptionHandler() {
		// TODO Auto-generated constructor stub
	} 
	
@ExceptionHandler(DeptNotFoundException.class)
public ResponseEntity<ErrorMssg> deptNotFoundException (DeptNotFoundException dexp,WebRequest req) {
	ErrorMssg mssg = new ErrorMssg(HttpStatus.NOT_FOUND, dexp.getMessage());
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mssg);
}
}
