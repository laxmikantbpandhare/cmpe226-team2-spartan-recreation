package com.example.spartan.entity;

public class Activity {
	
	String activity_id;
	String activity_name;
	
	public Activity( ) {}
	
	public Activity(String activity_id, String activity_name) {
		super();
		this.activity_id = activity_id;
		this.activity_name = activity_name;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	
	
	
	

}
