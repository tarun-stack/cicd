package com.myspringapp.SpringApp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RonController {

	public RonController() {
		// TODO Auto-generated constructor stub
	}
//@RequestMapping(value ="/",method = RequestMethod.GET)
@GetMapping("/")
public String Helloworld() {
return "Hello!!Welcome to my world hahaha";
}
}
