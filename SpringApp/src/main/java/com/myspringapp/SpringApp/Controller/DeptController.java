package com.myspringapp.SpringApp.Controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myspringapp.SpringApp.Entity.Department;
import com.myspringapp.SpringApp.Error.DeptNotFoundException;
import com.myspringapp.SpringApp.Service.DeptService;


@RestController
public class DeptController {
	@Autowired
	private DeptService deptservice;
	
	private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DeptController.class);

	@PostMapping("/departments")  //Req body means JSON
     public Department saveDept(@Valid@RequestBody Department dept) {
		//i dont want to take control. Ask spring thru autowire
		//types of dependency injection.property,constructor,setter
		//DeptService deptservice = (DeptService)new DeptServiceImpl();
		LOGGER.info("Inside save dept function");
		return deptservice.saveDept(dept);
		
	}
    
	@GetMapping("/departments")
	public List<Department> fetchDeptList(){
		return deptservice.fetchDeptList();
	}
	@GetMapping("/departments/{id}") //path var
	public Department fetchDeptById(@PathVariable("id")Long depID)throws DeptNotFoundException {
		return deptservice.fetchDeptById(depID);
	}
	@DeleteMapping("/departments/{id}")
	public String deleteDeptById(@PathVariable("id")Long depID) {
		 deptservice.deleteDeptById(depID);
		 return"deleted successfully";
	}
	@PutMapping("/departments/{id}")
	public Department updateDeptById(@PathVariable("id")Long depID,@RequestBody Department dept) {
		return deptservice.updateDeptById(depID,dept);
		 
	}
	@GetMapping("/departments/name/{name}") //path var
	public Department fetchDeptByName(@PathVariable("name")String DName) {
		return deptservice.fetchDeptByName(DName);
}
}
