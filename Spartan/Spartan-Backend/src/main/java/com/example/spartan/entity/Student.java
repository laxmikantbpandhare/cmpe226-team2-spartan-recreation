package com.example.spartan.entity;

public class Student {

	int sjsu_id;
	String fname; 
	String lname; 
	String college_year; 
	String password; 
	String registered_by;
	
	
	public Student() {}
	
	public Student(int sjsu_id, String fname, String lname, String college_year, String password,
			String registered_by) {
		super();
		this.sjsu_id = sjsu_id;
		this.fname = fname;
		this.lname = lname;
		this.college_year = college_year;
		this.password = password;
		this.registered_by = registered_by;
	}
	public int getSjsu_id() {
		return sjsu_id;
	}
	public void setSjsu_id(int sjsu_id) {
		this.sjsu_id = sjsu_id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getCollege_year() {
		return college_year;
	}
	public void setCollege_year(String college_year) {
		this.college_year = college_year;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegistered_by() {
		return registered_by;
	}
	public void setRegistered_by(String registered_by) {
		this.registered_by = registered_by;
	}
	
	
	
}


