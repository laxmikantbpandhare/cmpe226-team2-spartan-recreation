package com.example.spartan.controller;

import com.example.spartan.entity.Student;
import com.example.spartan.entity.User;
import com.example.spartan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.spartan.entity.Student;
import com.example.spartan.entity.User;
import com.example.spartan.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
public class  UserController {

//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    String secretKey = "spartan";

    @Autowired
    UserRepository userRepository;


    @GetMapping
    public List<User> getAllUsers(){
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

        /*
        if(role = student)
        check if status = true in student_registration
        else
        return invalid
        */

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        boolean isPasswordMatch = encoder.matches(password, s);
        if (isPasswordMatch){
            map.put("valid", "valid");
            return map;
        }
        else {
            map.put("valid", "invalid");
            return map;
        }
    }
    

}
