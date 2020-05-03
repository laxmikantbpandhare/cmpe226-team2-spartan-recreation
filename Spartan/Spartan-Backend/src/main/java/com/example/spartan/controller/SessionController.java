package com.example.spartan.controller;

import com.example.spartan.database.MongoDB;
import com.example.spartan.entity.Session;
import com.example.spartan.mail.SendMail;
import com.example.spartan.repository.InstructorRepository;
import com.example.spartan.repository.SessionRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	SessionRepository sessionRepo;

	@Autowired
	InstructorRepository instrRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostMapping("/testLog")
	public void testLog(@RequestBody Map<String, String> payload) {
		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "testLog")
		.append("payload", payload );
		coll.insertOne(doc);	
	}


	@PostMapping("/enroll") 
	public String enrollStudent(@RequestBody Map<String, String> payload) throws MessagingException, IOException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException {
		
		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST sessions/enroll")
		.append("payload", payload);
		coll.insertOne(doc);

		System.out.println("Session_id = "+payload.get(payload.keySet().toArray()[0]));
		System.out.println("enrollment payload = "+payload);
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_ENROLL_STUDENT");
				
		SqlParameterSource paramMap = new MapSqlParameterSource()
				.addValue("sp_sessionid", payload.get(payload.keySet().toArray()[0]))
				.addValue("sp_capacity", payload.get(payload.keySet().toArray()[1]))
				.addValue("sp_studentssn", payload.get(payload.keySet().toArray()[2]));
	
				String CallResult = "";
				try {
					CallResult = call.executeFunction(String.class, paramMap);
					System.out.println("RESULT - "+CallResult);
					String receiver = (String)payload.get(payload.keySet().toArray()[3]);
					if(!receiver.equals("")) {
						SendMail y = new SendMail();
						y.sendEmail("You have enrolled fro Session in Spartan Recreation", receiver,
								"You have enrolled fro Session in Spartan Recreation." +"\n\n For more details check your dashboard\n\n " +
										"Thanks and Regards, \n Spartan Recreation Team");
					}
				}
				catch(Exception e){
					if(e.getLocalizedMessage().toLowerCase().contains("duplicate")) {
						CallResult = "You have already signed up for this session";
					}
					
				}  
				
		System.out.println("Status of saving to stored proc: " + CallResult);

		return CallResult;
	}

	@PostMapping("/removes/enroll")
	public String removeEnrolledStudent(@RequestBody Map<String, String> payload) throws MessagingException, IOException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException {


		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST sessions/removes/enroll")
		.append("payload", payload);
		coll.insertOne(doc);
		
		System.out.println("enrollment payload = "+payload);
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
					.withProcedureName("SP_REMOVE_ENROLLED_STUDENT");

		SqlParameterSource paramMap = new MapSqlParameterSource()
				.addValue("sp_sessionid", payload.get(payload.keySet().toArray()[0]))
				.addValue("sp_studentssn", payload.get(payload.keySet().toArray()[1]));

		String CallResult = call.executeFunction(String.class, paramMap);

		System.out.println("Status remove of saving to stored proc: " + CallResult);
		String receiver = (String)payload.get(payload.keySet().toArray()[2]);
		if(!receiver.equals("")) {
			SendMail y = new SendMail();
			y.sendEmail("You have cancelled your Enrollment for the Session in Spartan Recreation", receiver,
					"You have cancelled your Enrollment in Spartan Recreation for the session." +"\n\n For more details check your dashboard\n\n " +
							"Thanks and Regards, \n Spartan Recreation Team");
		}



		return CallResult;
	}


	@PostMapping("/removes/session")
	public String removeSession(@RequestBody Map<String, String> payload) throws MessagingException, IOException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST sessions/removes/session")
		.append("payload", payload);
		coll.insertOne(doc);

		System.out.println("session payload = "+payload);
		List result = sessionRepo.getEnrolledStudentsForSession(payload.get(payload.keySet().toArray()[0]));
		System.out.println("session payload result = "+result);
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_REMOVE_SESSION");

		SqlParameterSource paramMap = new MapSqlParameterSource()
				.addValue("sp_sessionid", payload.get(payload.keySet().toArray()[0]))
				.addValue("sp_instructorssn", payload.get(payload.keySet().toArray()[1]));

		String CallResult = call.executeFunction(String.class, paramMap);

		System.out.println("Status remove of saving to stored proc: " + CallResult);
		String receiver = (String)payload.get(payload.keySet().toArray()[2]);
		if(!receiver.equals("")) {
			SendMail y = new SendMail();
			y.sendEmail("You have removed your Session from Spartan Recreation", receiver,
					"You have removed your Session in Spartan Recreation." +"\n\n For more details check your dashboard\n\n " +
							"Thanks and Regards, \n Spartan Recreation Team");
		}

		for(int i=0;i<result.size();i++){
			List list = (List) result.get(i);
			System.out.println("result = "+ result.get(i));
			System.out.println("mail = "+ list.get(0));
			String receiver1 = (String) list.get(0);
			if(!receiver1.equals("")) {
				SendMail y = new SendMail();
				y.sendEmail("You have removed from enrolled Session", receiver1,
						"You have removed from enrolled Session in Spartan Recreation." +"\n\n For more details check your dashboard\n\n " +
								"Thanks and Regards, \n Spartan Recreation Team");
			}
		}



		return CallResult;
	}

	
	@PostMapping("/new")
	public boolean createNewSession(@RequestBody Map<String, String> payload) {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST session/new")
		.append("payload", payload);
		coll.insertOne(doc);

				
		try {
			boolean b = sessionRepo.createSession(payload);
			System.out.println("SUCCESS");

			String receiver = (String)payload.get(payload.keySet().toArray()[8]);
			if(!receiver.equals("")) {
				SendMail y = new SendMail();
				y.sendEmail("You have Created Session in Spartan Recreation", receiver,
						"You have Created Session in Spartan Recreation with name "+ (String)payload.get(payload.keySet().toArray()[0])+"." +"\n\n For more details check your dashboard\n\n " +
								"Thanks and Regards, \n Spartan Recreation Team");
			}

			if(b)
				return true;
			
			return false;
		}
		catch(Exception e) {		
			e.printStackTrace();	
			return false;
		}		
	}
		
	@GetMapping("/instructor/{instructor_ssn}")
	public List<Session> getSessionsForInstructor(@PathVariable String instructor_ssn) {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "GET sessions/instructor/{instructor_ssn}")
		.append("payload", instructor_ssn);
		coll.insertOne(doc);
		
		try {
			List<Session> result = sessionRepo.getSessionByInstructor(instructor_ssn);
			System.out.println(result);
			return result;
		}
		catch(Exception e) {
			return null;
		}
	}

	@GetMapping("/sessions/{student_ssn}")
	public List getEnrolledSessionsForStudent(@PathVariable String student_ssn) {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "GET sessions/instructor/{student_ssn}")
		.append("payload", student_ssn);
		coll.insertOne(doc);
		
		try {
			List result = sessionRepo.getEnrolledSessionByStudents(student_ssn);
			System.out.println(result);
			return result;
		}
		catch(Exception e) {
			return null;
		}
	}

	@GetMapping("/enrolled/{session_id}")
	public List getEnrolledStudentsForSession(@PathVariable String session_id) {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "GET sessions/enrolled/{session_id}")
		.append("payload", session_id);
		coll.insertOne(doc);

		try {
			List result = sessionRepo.getEnrolledStudentsForSession(session_id);
			System.out.println("Result=="+result);
			return result;
		}
		catch(Exception e) {
			return null;
		}
	}


	@PostMapping("/search")
	public List<Session> getSessionsList(@RequestBody Map<String, String> payload) {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "POST sessions/search")
		.append("payload", payload);
		coll.insertOne(doc);
		
		try {
			System.out.println("payload"+payload);
			List<Session> result = sessionRepo.getSessionList(payload);
			System.out.println("result"+result);
			return result;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	@GetMapping("/{id}")
	public Session getSessionByID(@PathVariable String id) {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("timestamp" , new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
		.append("api", "GET sessions/{id}")
		.append("payload", id);
		coll.insertOne(doc);
		return sessionRepo.getSessionByID(id);
	}
	
	@GetMapping("/test")
	public void testAPI() {
		System.out.println("Welcome to Sessions");
	}
}
