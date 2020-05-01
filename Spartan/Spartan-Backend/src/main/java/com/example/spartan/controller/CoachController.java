package com.example.spartan.controller;

import com.example.spartan.entity.Coach;
import com.example.spartan.mail.SendMail;
import com.example.spartan.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/coaches")
public class CoachController {

	@Autowired
	CoachRepository coachRepo;
	
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
	
	/*@GetMapping("/test")
	public void testAPI() {
		System.out.println("Hello");
	}*/

	@CrossOrigin(origins="*")
	@PostMapping("/assessStudentRequest/{studentssn}/{sessionId}/{decision}")
	public boolean approveStudent(@PathVariable("studentssn") String studentssn, @PathVariable("sessionId") String sessionId, @PathVariable("decision") String decision) {

		return coachRepo.assessRequest(studentssn,sessionId,decision);

	}

	@GetMapping("/getAllPendingRequests")
	public List getAllPendingRequests() {
		return coachRepo.getAllPendingRequests();
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
}
