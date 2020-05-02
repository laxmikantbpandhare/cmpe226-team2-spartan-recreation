package com.example.spartan.controller;

import com.example.spartan.entity.Coach;
import com.example.spartan.entity.Team;
import com.example.spartan.mail.SendMail;
import com.example.spartan.repository.CoachRepository;
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
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/coaches")
public class CoachController {

	@Autowired
	CoachRepository coachRepo;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostMapping("/new")
	public ResponseEntity<String> createNewCoach(@RequestBody Coach coach) {
		System.out.println("New coach employee to be created - \n"+ 
							"name = "+coach.getFname()+" "+coach.getLname()+
							" ssn = "+coach.getSsn());
		
		
		try {
			coachRepo.saveCoach(coach);
			return ResponseEntity.ok().body("");
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause().toString());

		}
		
	}


	@GetMapping("/coach/{instructor_ssn}")
	public List<Team> getSessionsForInstructor(@PathVariable String instructor_ssn) {

		try {
			List<Team> result = coachRepo.getSessionByInstructor(instructor_ssn);
			System.out.println("results here"+result);
			return result;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	/*@GetMapping("/test")
	public void testAPI() {
		System.out.println("Hello");
	}*/

	@CrossOrigin(origins="*")
	@PostMapping("/assessStudentRequest/{studentssn}/{tryOutSessionName}/{decision}")
	public boolean approveStudent(@PathVariable("studentssn") String studentssn, @PathVariable("tryOutSessionName") String tryOutSessionName, @PathVariable("decision") String decision) {

		String session_id = coachRepo.getSessionIdByName(tryOutSessionName);
		return coachRepo.assessRequest(studentssn,session_id,decision);

	}

	@GetMapping("/getAllPendingRequests")
	public List getAllPendingRequests(@PathVariable String coachssn) {
		return coachRepo.getAllPendingRequests(coachssn);
	}



	@PostMapping("/newTryOutSession")
	public ResponseEntity<String> createNewSession(@RequestBody Map<String, String> payload) {
		System.out.println("New try session to be created - \n"+
				"session name = "+(String)payload.get(payload.keySet().toArray()[1])+
				"email="+(String)payload.get(payload.keySet().toArray()[4]));

		try {
			coachRepo.createTryOutSession(payload);

			System.out.println("SUCCESS");

			String receiver = (String)payload.get(payload.keySet().toArray()[4]);
			if(!receiver.equals("")) {
				SendMail y = new SendMail();
				y.sendEmail("You have Created TryOut Session in Spartan Recreation", receiver,
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


	@PostMapping("/removes/session")
	public String removeTryOutSession(@RequestBody Map<String, String> payload) throws MessagingException, IOException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException {

		System.out.println("Try out session payload = "+payload);
		List result = coachRepo.getRegisteredStudentsForTryOut(payload.get(payload.keySet().toArray()[0]));

		System.out.println("session payload result = "+result);

		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_REMOVE_TRYOUTSESSION");

		SqlParameterSource paramMap = new MapSqlParameterSource()
				.addValue("sp_sessionid", payload.get(payload.keySet().toArray()[0]))
				.addValue("sp_coachssn", payload.get(payload.keySet().toArray()[1]));

		String CallResult = call.executeFunction(String.class, paramMap);

		System.out.println("Status remove of saving to stored proc: " + CallResult);
		String receiver = (String)payload.get(payload.keySet().toArray()[2]);
		if(!receiver.equals("")) {
			SendMail y = new SendMail();
			y.sendEmail("You have removed your try out session from Spartan Recreation", receiver,
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
						"You have removed from registred try out Session in Spartan Recreation." +"\n\n For more details check your dashboard\n\n " +
								"Thanks and Regards, \n Spartan Recreation Team");
			}
		}



		return CallResult;
	}
}
