package com.myspringapp.SpringApp.Service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.myspringapp.SpringApp.Entity.Department;
import com.myspringapp.SpringApp.Error.DeptNotFoundException;

@Service
public interface DeptService {
public Department saveDept(Department dept);
public List<Department> fetchDeptList();
public Department fetchDeptById(Long depID) throws DeptNotFoundException;
public void deleteDeptById(Long depID);
public Department updateDeptById(Long depID,Department dept);
public Department fetchDeptByName(String depName);
}
