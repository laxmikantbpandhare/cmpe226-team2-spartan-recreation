package com.example.spartan.entity;

public class Enrollment {

	int student_id;
	String session_id;
	String status;
	int list_order;
	
	public Enrollment() {}
	
	public Enrollment(int student_id, String session_id, String status, int list_order) {
		super();
		this.student_id = student_id;
		this.session_id = session_id;
		this.status = status;
		this.list_order = list_order;
	}
	
	
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getList_order() {
		return list_order;
	}
	public void setList_order(int list_order) {
		this.list_order = list_order;
	}
	
	
	
}