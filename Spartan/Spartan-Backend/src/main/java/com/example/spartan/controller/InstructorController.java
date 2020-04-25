package com.example.spartan.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.example.spartan.entity.Instructor;
import com.example.spartan.repository.InstructorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
public class InstructorController {

	@Autowired
	InstructorRepository instrRepo;

	@CrossOrigin(origins="*")
	@PostMapping("/authenticateInstructor")
	public  Map<String, String> auth(@RequestBody Instructor person) throws ParseException {

		String user_password = instrRepo.getInstructorPassword(person.getEmail_id());
		HashMap<String, String> map = new HashMap<>();
		Instructor s= instrRepo.getUserDetails(person.getEmail_id());
		map.put("email_id", person.getEmail_id());
		map.put("ssn", s.getSsn());
		if (person.getPassword().equals(user_password)){
			map.put("valid", "valid");
			return map;
		}
		else {
			map.put("valid", "invalid");
			return map;
		}
	}
	
	
}
