package com.myspringapp.SpringApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.myspringapp.SpringApp.Entity.Department;
import com.myspringapp.SpringApp.Repository.DeptRepo;
import com.myspringapp.SpringApp.Service.DeptService;
import lombok.Builder;
	
@SpringBootTest
public class DeptServiceTest {
	@MockBean
	private DeptRepo deprepo;
    @Autowired
	private DeptService depService;
    
	@BeforeEach
	void setup() {
		//Department dep = Department
				         
	}
	@Test
	public void whenValidDepNameThenDepFound() {
		String depName= "IT-AI";
		Department found = depService.fetchDeptByName(depName);
		assertEquals(depName, found.getDeptName());
	}
}
