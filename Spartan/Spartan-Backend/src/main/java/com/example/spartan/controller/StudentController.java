package com.example.spartan.controller;

import com.example.spartan.entity.User;
import com.example.spartan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
//@RequestMapping("/user")
public class StudentController {

//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    String secretKey = "spartan";

    @Autowired
    UserRepository userRepository;


//    @GetMapping
//    public List<User> getAllUsers(){
//        return userRepository.getUser();
//    }


//    @PostMapping("/persons")
//    public Boolean createStudent(@RequestBody User person) {
//
//
//        System.out.println("email" + (person.getEmailid()));
//        System.out.println("city" + (person.getCity()));
//        System.out.println("firstname" + (person.getFirstname()));
//        System.out.println("lastname" + (person.getLastname()));
//        System.out.println("phoneno" + (person.getPhoneno()));
//
//        return userRepository.saveUser(person);
//
//    }
}
