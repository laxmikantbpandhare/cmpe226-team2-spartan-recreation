package com.example.spartan.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
public class MongoDB {

    private static MongoDB instance;

    private MongoCollection<Document> coll;
    
    private MongoDB() {
      
        MongoClientURI uri = new MongoClientURI("mongodb://cmpe226user:cmpe226user@websitetraffic-shard-00-00-qsuch.mongodb.net:27017,websitetraffic-shard-00-01-qsuch.mongodb.net:27017,websitetraffic-shard-00-02-qsuch.mongodb.net:27017/test?ssl=true&replicaSet=WebsiteTraffic-shard-0&authSource=admin&retryWrites=true&w=majority");

        MongoClient mClient = new MongoClient(uri);
        MongoDatabase db = mClient.getDatabase("maindb");
        coll = db.getCollection("testColl");
        
    }

    public MongoCollection<Document> getCollection() {
      return coll;
    }

    public static MongoDB getinstance() {
        if(instance == null) {
          instance = new MongoDB();
        }
        return instance;
    }

}

