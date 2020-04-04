package com.example.spartan.entity;

public class Team {

	
	String team_id;
	String team_name;
	String activity_id;
	String coach_ssn; 
	int year;
	
	
	public Team() {}
	
	public Team(String team_id, String team_name, String activity_id, String coach_ssn, int year) {
		super();
		this.team_id = team_id;
		this.team_name = team_name;
		this.activity_id = activity_id;
		this.coach_ssn = coach_ssn;
		this.year = year;
	}
	
	
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getCoach_ssn() {
		return coach_ssn;
	}
	public void setCoach_ssn(String coach_ssn) {
		this.coach_ssn = coach_ssn;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
}
