package com.example.spartan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping("/new")
	public ResponseEntity<String> createNewSession(@RequestBody Session session) {
		System.out.println("New session to be created - \n"+ 
							"name = "+session.getSession_name());
				
		try {
			sessionRepo.createSession(session);
			return ResponseEntity.ok().body("");
		}
		catch(Exception e) {			
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
	
	@GetMapping("/test")
	public void testAPI() {
		System.out.println("Welcome to Sessions");
	}
}
