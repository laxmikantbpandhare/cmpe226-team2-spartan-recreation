package com.example.spartan.controller;

import com.example.spartan.database.MongoDB;
import com.example.spartan.entity.User;
import com.example.spartan.repository.UserRepository;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
public class  UserController {

//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    String secretKey = "spartan";

    @Autowired
    UserRepository userRepository;


    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.getUser();
    }

    @PostMapping("/persons")
    public Boolean createUser(@RequestBody Map<String, String> payload) {

        boolean status = userRepository.saveUser(payload);
        System.out.println("status"+status);
        return status;
        }

        
    @CrossOrigin(origins="*")
    @PostMapping("/authenticate")
    public  Map<String, String> auth(@RequestBody Map<String, String> payload) throws ParseException {
        
        MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST /authenticate")
		.append("email", (String)payload.get(payload.keySet().toArray()[0] ));
		coll.insertOne(doc);
        
        System.out.println("payload"+payload);
        String email_id = (String)payload.get(payload.keySet().toArray()[0]);
        String password = (String)payload.get(payload.keySet().toArray()[1]);
        String role = (String)payload.get(payload.keySet().toArray()[2]);

        HashMap<String, String> map = new HashMap<>();
        String s= userRepository.getUserDetails(email_id,role);
        map.put("email_id", email_id);
        // SSN ius required to store so that one can idetify the Current Logged in User
        String ssn = userRepository.getUserSSN(email_id);
        map.put("ssn", ssn);
        map.put("role", role);

        if(role.equals("Student")){
            Boolean result = userRepository.verifyRegistration(ssn);
//            System.out.println("Student Registration = "+result);
            if(!result) {
                map.put("valid", "unregistered");
                return map;
            }
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        System.out.println("passwoed"+s);
        System.out.println("hasdhed"+hashedPassword);
        //i modified below
        boolean isPasswordMatch = encoder.matches(password,s);
        if (isPasswordMatch){
            map.put("valid", "valid");
            return map;
        }
        else {
            map.put("valid", "invalid");
            return map;
        }
    }

    @PostMapping("/loadSampleData")
    public String loadSampleData() {

        MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST /loadSampleData");
        coll.insertOne(doc);
        
        return userRepository.loadSampleData();

    }

}
