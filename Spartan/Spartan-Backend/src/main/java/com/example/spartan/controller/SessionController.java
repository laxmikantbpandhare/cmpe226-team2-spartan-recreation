package com.example.spartan.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.example.spartan.entity.Enrollment;
import com.example.spartan.mail.SendMail;
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

import com.example.spartan.entity.Session;
import com.example.spartan.repository.SessionRepository;

import javax.mail.MessagingException;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	SessionRepository sessionRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;


	@PostMapping("/enroll") 
	public String enrollStudent(@RequestBody Map<String, String> payload) {
		
		System.out.println("enrollment payload = "+payload);
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_ENROLL_STUDENT");
				
		SqlParameterSource paramMap = new MapSqlParameterSource()
				.addValue("sp_sessionid", payload.get(payload.keySet().toArray()[0]))
				.addValue("sp_capacity", payload.get(payload.keySet().toArray()[1]))
				.addValue("sp_studentssn", payload.get(payload.keySet().toArray()[2]));
		
				String CallResult = call.executeFunction(String.class, paramMap);

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

	
	@PostMapping("/new")
	public ResponseEntity<String> createNewSession(@RequestBody Map<String, String> payload) {
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
