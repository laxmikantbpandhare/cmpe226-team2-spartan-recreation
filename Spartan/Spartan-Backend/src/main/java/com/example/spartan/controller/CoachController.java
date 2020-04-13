package com.example.spartan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spartan.entity.Coach;
import com.example.spartan.repository.CoachRepository;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/coaches")
public class CoachController {

	@Autowired
	CoachRepository coachRepo;
	
	@PostMapping("/new")
	public ResponseEntity<String> createNewCoach(@RequestBody Coach coach) {
		System.out.println("New coach employee to be created - \n"+ 
							"name = "+coach.getFname()+" "+coach.getLname()+
							" ssn = "+coach.getSsn());
		
		
		try {
			coachRepo.saveCoach(coach);
			return ResponseEntity.ok().body("");
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().toString());

		}
		
	}
	
	@GetMapping("/test")
	public void testAPI() {
		System.out.println("Hello");
	}
	
}
