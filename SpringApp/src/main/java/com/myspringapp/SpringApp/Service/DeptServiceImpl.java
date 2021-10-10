package com.myspringapp.SpringApp.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myspringapp.SpringApp.Entity.Department;
import com.myspringapp.SpringApp.Error.DeptNotFoundException;
import com.myspringapp.SpringApp.Repository.DeptRepo;

@Service
public class DeptServiceImpl implements DeptService{

	@Autowired
	private DeptRepo depRepo;
	
	@Override
	public Department saveDept(Department dept) {
		return depRepo.save(dept);
	}
	@Override
	public List<Department> fetchDeptList(){
		return depRepo.findAll();
	}
	@Override
	public Department fetchDeptById(Long depID) throws DeptNotFoundException{
		Optional<Department> dep = depRepo.findById(depID);
		if(!dep.isPresent())
			throw new DeptNotFoundException("No dept found for this ID");
		else
			return dep.get();
	}
	@Override
	public void deleteDeptById(Long depID) {
		depRepo.deleteById(depID);
	}
	@Override
	public Department updateDeptById(Long depID,Department dept) {
		Department dep = depRepo.findById(depID).get();
		if(Objects.nonNull(dept.getDeptName()) && !"".equalsIgnoreCase(dept.getDeptName()))
		dep.setDeptName(dept.getDeptName());
		if(Objects.nonNull(dept.getDeptCode()) && !"".equalsIgnoreCase(dept.getDeptCode()))
			dep.setDeptName(dept.getDeptCode());
		if(Objects.nonNull(dept.getDeptAdd()) && !"".equalsIgnoreCase(dept.getDeptAdd()))
			dep.setDeptName(dept.getDeptAdd());
		return depRepo.save(dep);
	}
	@Override
	public Department fetchDeptByName(String depname) {
		return depRepo.findByDeptNameIgnoreCase(depname);
	}
	
}
