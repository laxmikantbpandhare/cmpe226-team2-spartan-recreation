package com.example.spartan.entity;

public class Team {

	
	public String sessionId;
	public String team_tryOutSession;
	public String activity_id;
	public String coach_ssn;
	public int year;
	
	
	public Team() {}
	
	public Team(String sessionId, String team_tryOutSession, String activity_id, String coach_ssn, int year) {
		super();
		this.sessionId = sessionId;
		this.team_tryOutSession = team_tryOutSession;
		this.activity_id = activity_id;
		this.coach_ssn = coach_ssn;
		this.year = year;
	}
	
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String team_id) {
		this.sessionId = team_id;
	}
	public String getTeam_tryOutSession() {
		return team_tryOutSession;
	}
	public void setTeam_tryOutSession(String team_name) {
		this.team_tryOutSession = team_name;
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
