package org.mycompany;

import java.sql.Date;

public class Employee {
	private String employeeId;
	private String firstName;
	private String lastName;
	private String department;
	private String email;
	private Date birthday;

	// ---------- Getters ---------- //

	public String GetEmployeeId() {
		return this.employeeId;
	}

	public String GetFirstName() {
		return this.firstName;
	}

	public String GetLastName() {
		return this.lastName;
	}

	public String GetDepartment() {
		return this.department;
	}

	public String GetEmail() {
		return this.email;
	}

	
	public Date GetBirthday() {
		return this.birthday;
	}

	// ---------- Setters ---------- //

	public void SetEmployeeId(String newEmployeeId) {
		this.employeeId = newEmployeeId;
	}

	public void SetFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}

	public void SetLastName(String newLastName) {
		this.lastName = newLastName;
	}

	public void SetDepartment(String newDepartment) {
		this.department = newDepartment;
	}

	public void SetEmail(String newEmail) {
		this.email = newEmail;
	}

	public void SetBirthday(Date newBirthday) {
		this.birthday = newBirthday;
	}

	// ---------- Utility ---------- //

	@Override
	public String toString() {
		//return "Employee [Name="+this.lastName+","+this.lastName+", ID="+this.employeeId+", Email="+this.email+"]";
		// return this.firstName;
		return "{\"Name\" : \""+this.lastName+", "+this.firstName+"\", \"Email\" : \""+this.email+"\", \"ID\" : \""+this.employeeId+"\"}";
	}
}
