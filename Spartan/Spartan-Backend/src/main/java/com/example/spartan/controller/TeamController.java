package com.example.spartan.controller;

import com.example.spartan.database.MongoDB;
import com.example.spartan.entity.Team;
import com.example.spartan.mail.SendMail;
import com.example.spartan.repository.TeamRepository;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/teamTryOut")
public class TeamController {

    @Autowired
    TeamRepository teamRepo;

    @PostMapping("/isregister")
    public boolean getRegistrationStatus(@RequestBody Map<String, String> payload) throws MessagingException, IOException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException {
            //String id = payload.get(payload.keySet().toArray()[0]);

            MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
            Document doc = new Document();
            doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
            .append("api", "GET teamTryOut/isregister/")
            .append("payload", payload );
            coll.insertOne(doc);

            System.out.println("entered here");

            Team t = teamRepo.getTeamByName(payload.get(payload.keySet().toArray()[2]));

            payload.put("team_id",t.sessionId);
            payload.put("coach_ssn",t.coach_ssn);

            boolean status = teamRepo.getStudentStatus(payload.get(payload.keySet().toArray()[0]),payload.get(payload.keySet().toArray()[4]));

            String receiver = (String)payload.get(payload.keySet().toArray()[1]);

            if (status) {
                if (!receiver.equals("")) {
                    SendMail y = new SendMail();
                    y.sendEmail("Team try out session registration", receiver,
                            "You have already registered for the try out session of team " + (String) payload.get(payload.keySet().toArray()[2]) + "." + "\n\n For more details check your dashboard\n\n " +
                                    "Thanks and Regards, \n Spartan Recreation Team");
                }
            }
            else {

            teamRepo.teamTryOutRegister(payload);

                if (!receiver.equals("")) {
                    SendMail y = new SendMail();
                    y.sendEmail("Team try out session registration", receiver,
                            "You have registered for the try out session of team " + (String) payload.get(payload.keySet().toArray()[2]) + "." + "\n\n For more details check your dashboard\n\n " +
                                    "Thanks and Regards, \n Spartan Recreation Team");
                }

            }

            //System.out.println(status);
            return true;
    }

    @GetMapping("/teamDetails")
    public List getTeamDetails() {

        MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
        Document doc = new Document();
        doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
        .append("api", "GET teamTryOut/teamDetails/");
        coll.insertOne(doc);

        //System.out.println("entered here");
        try {
            List result = teamRepo.getTeamDetails();
            //System.out.println("result"+result);
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }


    @GetMapping("/getactivity")
    public List getActivity() {

        MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
        Document doc = new Document();
        doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
        .append("api", "GET teamTryOut/getactivity/");
        coll.insertOne(doc);
        
        System.out.println("entered here");
        try {
            List result = teamRepo.getActivity();

//            System.out.println("result"+result);
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }
}
