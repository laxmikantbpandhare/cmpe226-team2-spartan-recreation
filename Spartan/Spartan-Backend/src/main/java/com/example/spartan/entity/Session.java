package com.example.spartan.entity;

import java.sql.Date;

public class Session {

	String session_id; 
	String session_name; 
	int capacity;
	String section; 
	int room_number; 
	Date start_time; 
	Date end_time; 
	String activity_id;
	String instructor_ssn;
	
	public Session() {
		
	}
	
	
	public Session(String session_id, String session_name, int capacity, String section, int room_number,
			Date start_time, Date end_time, String activity_id, String instructor_ssn) {
		super();
		this.session_id = session_id;
		this.session_name = session_name;
		this.capacity = capacity;
		this.section = section;
		this.room_number = room_number;
		this.start_time = start_time;
		this.end_time = end_time;
		this.activity_id = activity_id;
		this.instructor_ssn = instructor_ssn;
	}


	public String getSession_id() {
		return session_id;
	}


	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}


	public String getSession_name() {
		return session_name;
	}


	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public String getSection() {
		return section;
	}


	public void setSection(String section) {
		this.section = section;
	}


	public int getRoom_number() {
		return room_number;
	}


	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}


	public Date getStart_time() {
		return start_time;
	}


	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}


	public Date getEnd_time() {
		return end_time;
	}


	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}


	public String getActivity_id() {
		return activity_id;
	}


	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}


	public String getInstructor_ssn() {
		return instructor_ssn;
	}


	public void setInstructor_ssn(String instructor_ssn) {
		this.instructor_ssn = instructor_ssn;
	}
	
	
	
	
	
	
		
}
