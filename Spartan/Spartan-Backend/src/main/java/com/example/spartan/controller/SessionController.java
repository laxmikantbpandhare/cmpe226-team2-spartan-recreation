package com.example.spartan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spartan.entity.Session;
import com.example.spartan.repository.SessionRepository;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	SessionRepository sessionRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;


	@PostMapping("/enroll") 
	public String enrollStudent(@RequestBody Map<String, String> payload) {
		
		System.out.println("enrollment payload = "+payload);
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_ENROLL_STUDENT");
				
		SqlParameterSource paramMap = new MapSqlParameterSource()
				.addValue("sp_sessionid", payload.get(payload.keySet().toArray()[0]))
				.addValue("sp_capacity", payload.get(payload.keySet().toArray()[1]))
				.addValue("sp_studentssn", payload.get(payload.keySet().toArray()[2]));
		
				String CallResult = call.executeFunction(String.class, paramMap);

		System.out.println("Status of saving to stored proc: " + CallResult);

		return CallResult;
	}

	
	@PostMapping("/new")
	public ResponseEntity<String> createNewSession(@RequestBody Session session) {
		System.out.println("New session to be created - \n"+ 
							"name = "+session.getSession_name()+
							" date ="+session.getSession_date());
				
		try {
			sessionRepo.createSession(session);
			System.out.println("SUCCESS");
			return ResponseEntity.ok().body("");
		}
		catch(Exception e) {		
			e.printStackTrace();	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().toString());
		}		
	}
		
	@GetMapping("/instructor/{instructor_ssn}")
	public List<Session> getSessionsForInstructor(@PathVariable String instructor_ssn) {
		
		try {
			List<Session> result = sessionRepo.getSessionByInstructor(instructor_ssn);
			System.out.println(result);
			return result;
		}
		catch(Exception e) {
			return null;
		}
	}


	@PostMapping("/search")
	public List<Session> getSessionsList(@RequestBody Map<String, String> payload) {

		try {
			System.out.println("payload"+payload);
			List<Session> result = sessionRepo.getSessionList(payload);
			System.out.println("result"+result);
			return result;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	@GetMapping("/{id}")
	public Session getSessionByID(@PathVariable String id) {
		return sessionRepo.getSessionByID(id);
	}
	
	@GetMapping("/test")
	public void testAPI() {
		System.out.println("Welcome to Sessions");
	}
}
