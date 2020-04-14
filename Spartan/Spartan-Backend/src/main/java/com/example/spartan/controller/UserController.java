package com.example.spartan.controller;

import com.example.spartan.entity.Student;
import com.example.spartan.entity.User;
import com.example.spartan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
//@RequestMapping("/user")
public class  UserController {

//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    String secretKey = "spartan";

    @Autowired
    UserRepository userRepository;

//    @GetMapping("/test")
//    public String test(){
//        return "testing";
//    }

   @GetMapping
    public List<User> getAllUsers(){
        return userRepository.getUser();
    }


    @PostMapping("/persons")
    public Boolean createStudent(@RequestBody Student person) {

        System.out.println("email" + (person.getEmail_id()));
        System.out.println("city" + (person.getCollege_year()));
        System.out.println("firstname" + (person.getFname()));
        System.out.println("lastname" + (person.getLname()));

        return userRepository.saveUser(person);

        }

//priya

@CrossOrigin(origins="*")
    @PostMapping("/authenticate")
    public boolean auth(@RequestBody Student person) {

        System.out.println("email" + (person.getEmail_id()));
        System.out.println("passwrd" + (person.getPassword()));
        String user_password = userRepository.getUserpPassword(person.getEmail_id());
        System.out.println("DB passwrd" + (user_password));

        if (person.getPassword().equals(user_password))
            return true;
        else return false;
    }


}
