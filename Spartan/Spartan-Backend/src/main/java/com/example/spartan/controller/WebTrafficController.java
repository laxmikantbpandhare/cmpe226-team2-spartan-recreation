package com.example.spartan.controller;

import com.example.spartan.database.MongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class WebTrafficController {

    @GetMapping("/apidata")
    public String getAllData() {
        MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
        MongoCursor<Document> cursor = coll.find().iterator();
        int search = 0 , enroll = 0 , create = 0;
        try {
            while (cursor.hasNext()) {
                // String d = (String) cursor.next().toString();
                Document d = cursor.next();
                if(d.containsKey("api")) {
                    switch(d.get("api").toString()) {
                        case "search" : search++; break;
                        case "enroll" : enroll++; break;
                        case "create" : create++; break;
                    }
                }
            }
        }
        catch(Exception e) {

        }
        return ""+search+","+enroll+","+create;
    }



    @GetMapping("/activityData")
    public String getActData() {

        MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
        MongoCursor<Document> cursor = coll.find().iterator();
        int yoga = 0 , zumba = 0 , fitness = 0, basketball = 0, football = 0, volleyball = 0,badminton = 0; 

        try {
            while (cursor.hasNext()) {
                // String d = (String) cursor.next().toString();
                Document d = cursor.next();
                if(d.containsKey("activity")) {
                    switch(d.get("activity").toString()) {
                        case "1" : yoga++; break;
                        case "2" : zumba++; break;
                        case "3" : fitness++; break;
                        case "4" : basketball++; break;
                        case "5" : football++; break;
                        case "6" : volleyball++; break;
                        case "7" : badminton++; break;
                    }
                }
            }
        }
        catch(Exception e) {

        }
        System.out.println(""+yoga+","+zumba+","+fitness+","+basketball+","+football+","+volleyball+","+badminton);
        return ""+yoga+","+zumba+","+fitness+","+basketball+","+football+","+volleyball+","+badminton;
    }


    @GetMapping("/getAppLogs")
    public String getAllLogs() {

        MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
        MongoCursor<Document> cursor = coll.find().iterator();
        StringBuilder s = new StringBuilder();
        while(cursor.hasNext()){
            s.append(cursor.next().entrySet()+"$");
        }

        return s.toString();
    }

}


