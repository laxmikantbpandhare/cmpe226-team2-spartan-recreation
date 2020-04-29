package com.example.spartan.controller;

import com.example.spartan.repository.FrontDeskAssistantRepository;
import java.util.List;

import com.example.spartan.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
public class FrontDeskAssistantController {

	@Autowired
	FrontDeskAssistantRepository fdaRepo;

	@CrossOrigin(origins="*")
	@PostMapping("/approveStudent/{studentssn}/{fdassn}")
	public boolean approveStudent(@PathVariable("studentssn") String studentssn , @PathVariable("fdassn") String fdassn) {
		
		return fdaRepo.approveStudent(studentssn , fdassn);

	}

	@GetMapping("/allPendingStudents") 
	public List<Student> getAllPendingStudents() {
		return fdaRepo.getAllPendingStudents();
	} 
	
}
