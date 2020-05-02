package com.example.spartan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.spartan.database.MongoDB;
import com.example.spartan.entity.Instructor;
import com.example.spartan.repository.InstructorRepository;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST /authenticateInstructor")
		.append("payload", person );
		coll.insertOne(doc);
		
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


	@GetMapping("/getInstructorName/{ssn}")
	public  String getName(@PathVariable String ssn)  {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "GET /getInstructorName/{ssn}")
		.append("payload", ssn );
		coll.insertOne(doc);

		System.out.println("SSN received - "+ssn);
		return instrRepo.getInstructorName(ssn);
	}	
}
