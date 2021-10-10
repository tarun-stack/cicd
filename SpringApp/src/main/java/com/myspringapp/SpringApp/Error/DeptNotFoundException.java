package com.myspringapp.SpringApp.Error;

import java.io.PrintStream;
import java.io.PrintWriter;

public class DeptNotFoundException extends Exception{

	public DeptNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public DeptNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DeptNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DeptNotFoundException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DeptNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	

}
