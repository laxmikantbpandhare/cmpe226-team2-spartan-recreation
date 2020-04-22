package com.example.spartan.controller;

import com.example.spartan.entity.Student;
import com.example.spartan.entity.User;
import com.example.spartan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

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
    public  Map<String, String> auth(@RequestBody Student person) throws ParseException {

        String user_password = userRepository.getUserPassword(person.getEmail_id());
        HashMap<String, String> map = new HashMap<>();
        Student s= userRepository.getUserDetails(person.getEmail_id());
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
