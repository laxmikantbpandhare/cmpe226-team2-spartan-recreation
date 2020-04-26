package com.example.spartan.entity;

import java.sql.Date;

public class Instructor {
	
	String email_id;
	String ssn;
    String fname;
    String lname;
    String password; 
	Date joining_date;
	String user_role;

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	
	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
     
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getJoining_date() {
		return joining_date;
	}
	public void setJoining_date(Date joining_date) {
		this.joining_date = joining_date;
	}
	
	public Instructor(String ssn, String fname, String lname, String password, Date joining_date) {
		super();
		this.ssn = ssn;
		this.fname = fname;
		this.lname = lname;
		this.password = password;
		this.joining_date = joining_date;
	}  
   	
	public Instructor() {
		
	}
}
