package com.myspringapp.SpringApp.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class Department {

	public Department() {
		// TODO Auto-generated constructor stub
	}
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long deptId;
private String deptCode;
@NotBlank(message = "Field is blank.Please add dept")
/*@Length(max=10,min=1)
@Size(max=5,min=1)
@Email
@PositiveOrZero*/
private String deptName;
private String deptAdd;
public Long getDeptId() {
	return deptId;
}
public void setDeptId(Long deptId) {
	this.deptId = deptId;
}
public Department(Long deptId, String deptCode, String deptName, String deptAdd) {
	super();
	this.deptId = deptId;
	this.deptCode = deptCode;
	this.deptName = deptName;
	this.deptAdd = deptAdd;
}
@Override
public String toString() {
	return "Department [deptId=" + deptId + ", deptCode=" + deptCode + ", deptName=" + deptName + ", deptAdd=" + deptAdd
			+ "]";
}
public String getDeptCode() {
	return deptCode;
}
public void setDeptCode(String deptCode) {
	this.deptCode = deptCode;
}
public String getDeptName() {
	return deptName;
}
public void setDeptName(String deptName) {
	this.deptName = deptName;
}
public String getDeptAdd() {
	return deptAdd;
}
public void setDeptAdd(String deptAdd) {
	this.deptAdd = deptAdd;
}
}
