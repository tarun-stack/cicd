package com.myspringapp.SpringApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myspringapp.SpringApp.Entity.Department;

@Repository
public interface DeptRepo extends JpaRepository<Department, Long>{
public Department findByDeptName (String deptName);
public Department findByDeptNameIgnoreCase(String deptName);
}
