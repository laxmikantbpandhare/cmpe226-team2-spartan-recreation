package com.example.spartan.controller;

import com.example.spartan.repository.FrontDeskAssistantRepository;
import com.mongodb.client.MongoCollection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.spartan.database.MongoDB;
import com.example.spartan.entity.Student;

import org.bson.Document;
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

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST /approveStudent/{studentssn}/{fdassn}")
		.append("payload", studentssn+"/"+fdassn );
		coll.insertOne(doc);

		return fdaRepo.approveStudent(studentssn , fdassn);

	}

	@GetMapping("/allPendingStudents") 
	public List<Student> getAllPendingStudents() {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "GET allPendingStudents");
		coll.insertOne(doc);

		return fdaRepo.getAllPendingStudents();
	} 
	
}
