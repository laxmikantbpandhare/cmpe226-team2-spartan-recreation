package com.example.spartan.entity;

import java.sql.Date;

public class FrontDeskAssistant {
	
	String ssn;
    String fname;
    String lname;
    String password; 
    Date joining_date;
    
    
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
	
	public FrontDeskAssistant(String ssn, String fname, String lname, String password, Date joining_date) {
		super();
		this.ssn = ssn;
		this.fname = fname;
		this.lname = lname;
		this.password = password;
		this.joining_date = joining_date;
	}  
   	
	public FrontDeskAssistant() {
		
	}
}
