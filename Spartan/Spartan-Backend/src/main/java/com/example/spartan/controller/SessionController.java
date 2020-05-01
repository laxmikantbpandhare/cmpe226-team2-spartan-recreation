package com.example.spartan.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.example.spartan.database.MongoDB;
import com.example.spartan.entity.Session;
import com.example.spartan.mail.SendMail;
import com.example.spartan.repository.SessionRepository;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	SessionRepository sessionRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;



	@PostMapping("/enroll") 
	public String enrollStudent(@RequestBody Map<String, String> payload) throws MessagingException, IOException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException {
		
		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		Session s = getSessionByID(payload.get(payload.keySet().toArray()[0]));
		doc.append("api", "enroll")
		.append("session", s.getSession_name())
		.append("activity",s.getActivity_id())
		.append("owner", s.getInstructor_ssn());
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
	public ResponseEntity<String> createNewSession(@RequestBody Map<String, String> payload) {

		MongoCollection<Document> coll = MongoDB.getinstance().getCollection();
		Document doc = new Document();
		doc.append("api", "newSession")
		.append("session", payload.get(payload.keySet().toArray()[1]))
		.append("activity",payload.get(payload.keySet().toArray()[7]))
		.append("location",payload.get(payload.keySet().toArray()[4]))
		.append("owner", payload.get(payload.keySet().toArray()[8]));
		coll.insertOne(doc);

		System.out.println("New session to be created - \n"+ 
							"name = "+(String)payload.get(payload.keySet().toArray()[1])+
							" date ="+(String)payload.get(payload.keySet().toArray()[10])+
							"email="+(String)payload.get(payload.keySet().toArray()[9]));
				
		try {
			sessionRepo.createSession(payload);
			System.out.println("SUCCESS");

			String receiver = (String)payload.get(payload.keySet().toArray()[9]);
			if(!receiver.equals("")) {
				SendMail y = new SendMail();
				y.sendEmail("You have Created Session in Spartan Recreation", receiver,
						"You have Created Session in Spartan Recreation with name "+ (String)payload.get(payload.keySet().toArray()[1])+"." +"\n\n For more details check your dashboard\n\n " +
								"Thanks and Regards, \n Spartan Recreation Team");
			}

			return ResponseEntity.ok().body("");
		}
		catch(Exception e) {		
			e.printStackTrace();	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().toString());
		}		
	}
		
	@GetMapping("/instructor/{instructor_ssn}")
	public List<Session> getSessionsForInstructor(@PathVariable String instructor_ssn) {

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
		doc.append("api", "search")
		.append("query", payload.get(payload.keySet().toArray()[3]));
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
		return sessionRepo.getSessionByID(id);
	}
	
	@GetMapping("/test")
	public void testAPI() {
		System.out.println("Welcome to Sessions");
	}
}
