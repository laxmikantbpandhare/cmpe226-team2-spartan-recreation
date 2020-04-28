package com.example.spartan.controller;


import com.example.spartan.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/teamTryOut")
public class TeamController {

    @Autowired
    TeamRepository teamRepo;

    @GetMapping("/isregister")
    public boolean getRegistrationStatus(@RequestBody Map<String, String> payload) {
        try {
            String id = payload.get(payload.keySet().toArray()[0]);
            boolean status = teamRepo.getStudentStatus(id);
            System.out.println(status);
            return status;
        }
        catch(Exception e) {
            return false;
        }
    }

    @GetMapping("/teamDetails")
    public List getTeamDetails() {
        System.out.println("entered here");
        try {
            List result = teamRepo.getTeamDetails();

            //System.out.println(team.teamName)
            System.out.println("result"+result);
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }
}
