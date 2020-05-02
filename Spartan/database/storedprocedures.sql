drop PROCEDURE if exists sp_create_user;
drop PROCEDURE if exists sp_login_user;
drop PROCEDURE if exists sp_enroll_student;
drop PROCEDURE if exists sp_remove_enrolled_student;
drop PROCEDURE if exists sp_remove_session;
drop PROCEDURE if exists sp_approve_student;
drop PROCEDURE if exists sp_approve_tryOutRequest;
drop PROCEDURE if exists sp_remove_tryoutsession;
drop PROCEDURE if exists sp_load_sampledata;
drop trigger if exists InsertStudentTrigger;
drop trigger if exists InsertInstructorTrigger;
drop trigger if exists InsertCoachTrigger;
drop trigger if exists InsertFDATrigger;
drop trigger if exists deletStudentTrigger;
drop trigger if exists deleteInstructorTrigger;
drop trigger if exists deleteCoachTrigger;
drop trigger if exists deleteFDATrigger;
drop trigger if exists removeEnrolledStudents;
drop trigger if exists removeRegistredStudents;
drop view if exists tryOutSessionDetails;
drop view if exists gettingRequests;

delimiter $$
create 
#definer=`root`@`localhost` 
procedure cmpe226_Spartan.sp_create_user(
in sp_email_id varchar(50),
in sp_ssn varchar(10),
in sp_firstname varchar(50),
in sp_lastname varchar(50),
in sp_year varchar(15),
in sp_password varchar(80),
in sp_role varchar(25),
OUT status_out BOOLEAN
)
begin

DECLARE exit handler for sqlexception
  BEGIN
  SELECT 'Error occured';
  ROLLBACK;
  RESIGNAL;
END;
	
SET status_out = FALSE;

START TRANSACTION;
    
		if (select exists (select 1 from user where ssn = sp_ssn) ) then 
			
            if ( select exists (select 1 from user where ssn = sp_ssn) ) then
					select 'User exists !!';
			end if;
        
        else
		
			if TRIM(sp_role) like '%Student%' then 
				select 'Student Insert!!';
				insert into student (ssn, fname, lname, email_id,college_year, password,user_role) values (sp_ssn,sp_firstname, sp_lastname, sp_email_id,sp_year,sp_password,sp_role);
				SET status_out = TRUE;
				
			elseif sp_role = 'Instructor' then 

				insert into instructor (ssn, fname, lname, email_id, joining_year, password,user_role) values (sp_ssn,sp_firstname, sp_lastname, sp_email_id,sp_year,sp_password,sp_role);
				SET status_out = TRUE;

			elseif sp_role = 'Coach' then 
			
				insert into coach (ssn, fname, lname, email_id, joining_year, password,user_role) values (sp_ssn,sp_firstname, sp_lastname, sp_email_id,sp_year,sp_password,sp_role);
				SET status_out = TRUE;
			else
				insert into front_desk_assistant (ssn, fname, lname, email_id, joining_year, password,user_role) values (sp_ssn,sp_firstname, sp_lastname, sp_email_id,sp_year,sp_password,sp_role);
				SET status_out = TRUE;
		
		end if;
	end if;
    COMMIT;
end$$
delimiter ;

delimiter $$
create trigger InsertStudentTrigger after insert on Student
for each row
	begin
		insert into user(ssn, email_id) values (new.ssn, new.email_id);
	end$$
delimiter ;

delimiter $$
create trigger InsertInstructorTrigger after insert on Instructor
for each row
	begin
		insert into user(ssn, email_id) values (new.ssn, new.email_id);
	end$$
delimiter ;


delimiter $$
create trigger InsertCoachTrigger after insert on Coach
for each row
	begin
		insert into user(ssn, email_id) values (new.ssn, new.email_id);
	end$$
delimiter ;


delimiter $$
create trigger InsertFDATrigger after insert on Front_desk_assistant
for each row
	begin
		insert into user(ssn, email_id) values (new.ssn, new.email_id);
	end$$
delimiter ;


delimiter $$
create trigger deletStudentTrigger before delete on Student
for each row
	begin
		delete from user where ssn = old.ssn;
	end$$
delimiter ;

delimiter $$
create trigger deleteInstructorTrigger before delete on Instructor
for each row
	begin
		delete from user where ssn = old.ssn;
	end$$
delimiter ;


delimiter $$
create trigger deleteCoachTrigger before delete on Coach
for each row
	begin
		delete from user where ssn = old.ssn;
	end$$
delimiter ;


delimiter $$
create trigger deleteFDATrigger before delete on Front_desk_assistant
for each row
	begin
		delete from user where ssn = old.ssn;
	end$$
delimiter ;

delimiter $$
create 
#definer=`root`@`localhost` 
procedure cmpe226_Spartan.sp_login_user(
in sp_email_id varchar(50),
in sp_role varchar(25),
OUT out_password varchar(80)
)
begin

DECLARE exit handler for sqlexception
  BEGIN
  SELECT 'Error occured';
  ROLLBACK;
  RESIGNAL;
END;
	
SET out_password = "";

START TRANSACTION;
    
		if (select not exists (select 1 from user where email_id = sp_email_id) ) then 
			
				if ( select NOT exists (select 1 from user where email_id = sp_email_id) ) then
					select 'User Does Not exists !!';
                    SET out_password = "Not sp_login_userFound";
				end if;
        
        else
		
			if TRIM(sp_role) like '%Student%' then 
				select 'Student Select!!';
				SET out_password = (select password from student where email_id =  sp_email_id);
				-- insert into student (ssn, fname, lname, email_id,college_year, password,user_role) values (sp_ssn,sp_firstname, sp_lastname, sp_email_id,sp_year,sp_password,sp_role);
				-- SET out_password = "Found";
				
			elseif sp_role = 'Instructor' then 

				SET out_password = (select password from instructor where email_id =  sp_email_id);

			elseif sp_role = 'Coach' then 
			
				SET out_password = (select password from coach where email_id =  sp_email_id);
                
			else
            
				SET out_password = (select password from front_desk_assistant where email_id =  sp_email_id);	
                
		end if;
	end if;
    COMMIT;
end$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_enroll_student`(
in sp_sessionid varchar(10),
in sp_capacity int(11),
in sp_studentssn varchar(10),
OUT status_out varchar(20)
)
begin
	declare enrolledCount int default 0;
    declare waitlisted int default 0;
    
    select count(*) from cmpe226_spartan.enrollment where session_id = sp_sessionid into enrolledCount;
    
    if enrolledCount < sp_capacity then
		set enrolledCount = enrolledCount + 1;
		insert into enrollment(student_id,session_id,status,list_order) values (sp_studentssn , sp_sessionid , 'enrolled' , enrolledCount);
        set status_out = concat('Enrolled-',enrolledCount);
	else 
		set waitlisted = enrolledCount - sp_capacity;
        set waitlisted = waitlisted + 1;
		insert into enrollment(student_id,session_id,status,list_order) values (sp_studentssn , sp_sessionid , 'waitlist',waitlisted);
		set status_out = concat('Waitlisted-',waitlisted);
	end if;
end$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_remove_enrolled_student`(
in sp_sessionid varchar(10),
in sp_studentssn varchar(10),
OUT status_out varchar(20)
)
begin

	SET status_out = false;
	if sp_sessionid != "" and sp_studentssn != "" then 
		delete from enrollment as e where e.session_id = sp_sessionid and e.student_id = sp_studentssn;
		SET status_out = true;
    end if;
end$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_remove_session`(
in sp_sessionid varchar(10),
in sp_instructorssn varchar(10),
OUT status_out varchar(20)
)
begin

	SET status_out = false;
	if sp_sessionid != "" and sp_instructorssn != "" then 
		delete from session as s where s.session_id = sp_sessionid and s.instructor_ssn = sp_instructorssn;
		SET status_out = true;
    end if;
end$$
delimiter ;

delimiter $$
create trigger removeEnrolledStudents after delete on session
for each row
	begin
		delete from enrollment as e where e.session_id = old.session_id;
	end$$
delimiter ;

delimiter $$ 
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_approve_student`(
in sp_studentssn varchar(10),
in sp_fdassn varchar(10),
out status_out boolean)
BEGIN

DECLARE exit handler for sqlexception
  BEGIN
  SELECT 'Error occured';
  ROLLBACK;
  RESIGNAL;
END;

UPDATE student_registration SET `status` = '1' WHERE student_ssn = sp_studentssn;
UPDATE student_registration SET `registered_by` = sp_fdassn WHERE student_ssn = sp_studentssn;
set status_out = TRUE;

END$$
delimiter ;


delimiter $$ 
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_approve_tryOutRequest`(
in sp_studentssn varchar(10),
in sp_session_id varchar(10),
in sp_decision varchar(10),
out status_out boolean)
BEGIN

DECLARE exit handler for sqlexception
  BEGIN
  SELECT 'Error occured';
  ROLLBACK;
  RESIGNAL;
END;

UPDATE team_tryouts SET `status` = sp_decision WHERE student_id = sp_studentssn and session_id = sp_session_id;

set status_out = TRUE;

END$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_remove_tryoutsession`(
in sp_sessionid varchar(10),
in sp_coachssn varchar(10),
OUT status_out varchar(20)
)
begin

	SET status_out = false;
    
	if sp_sessionid != "" and sp_coachssn != "" then 
		delete from team as t where t.session_id = sp_sessionid and t.coach_ssn = sp_coachssn;
		SET status_out = true;
    end if;
end$$
delimiter ;


delimiter $$
create trigger removeRegistredStudents after delete on team
for each row
	begin
		delete from team_tryouts as t where t.session_id = old.session_id;
	end$$
delimiter ;


CREATE VIEW tryOutSessionDetails AS
SELECT t.team_tryOutSession, CONCAT (c.fname, c.lname) as coach_name,t.year 
FROM team t,coach c
WHERE t.coach_ssn = c.ssn;


CREATE VIEW gettingRequests AS
SELECT s.ssn, s.fname, s.lname, s.college_year, t.team_tryOutSession, t.coach_ssn
FROM student s,team_tryouts tt, team t
WHERE s.ssn = tt.student_id  
and tt.session_id=t.session_id
and tt.status = 'pending';

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_load_sampledata`(
OUT status_out varchar(20)
)
begin

	DECLARE exit handler for sqlexception
	  BEGIN
	  SELECT 'Error occured';
	  ROLLBACK;
	  RESIGNAL;
	END;
	
	SET status_out = FALSE;
    
    START TRANSACTION;
    
    SELECT 'Deleting all records from Spartan Database ';
    
    delete from team_tryouts;
    delete from team;
    delete from enrollment;
    delete from session;
    delete from activity;
    delete from student_registration;
    delete from coach;
    delete from front_desk_assistant;
    delete from instructor;
    delete from student;
    
    
    SELECT 'Insert sample data into Coach Table';
    
	insert into coach values ('5057','sayalishripad.kulkarni@sjsu.edu','Sayali','Kulkarni','fall 2020','$2a$10$8GDqoqhSserNrrbQYiVfzepMPa093dIq1gwKDlOTxuOJAt5XUK/5a','coach');
	insert into coach values ('7427','shivani.reddy@sjsu.edu','Shivani','Reddy','fall 2020','$2a$10$8GDqoqhSserNrrbQYiVfzepMPa093dIq1gwKDlOTxuOJAt5XUK/5a','coach');

	SELECT 'Insert sample data into Front Desk Assistant Table';
    
	insert into front_desk_assistant values ('8088','farha.kauser@sjsu.edu','Farha','Kauser','fall 2020','$2a$10$8GDqoqhSserNrrbQYiVfzepMPa093dIq1gwKDlOTxuOJAt5XUK/5a','front_desk_assistant');
	insert into front_desk_assistant values ('9088','priyachaitanya.yadav@sjsu.edu','priya','yadav','fall 2020','abcd','front_desk_assistant');
    
    SELECT 'Insert sample data into Instructor Table';
    
    insert into instructor values ('5088','farha.kauser@sjsu.edu','Farha','Kauser','fall 2020','$2a$10$8GDqoqhSserNrrbQYiVfzepMPa093dIq1gwKDlOTxuOJAt5XUK/5a','instructor');
	insert into instructor values ('6088','priyachaitanya.yadav@sjsu.edu','priya','yadav','fall 2020','$2a$10$8GDqoqhSserNrrbQYiVfzepMPa093dIq1gwKDlOTxuOJAt5XUK/5a','instructor');
    
    SELECT 'Insert sample data into Student Table';
    
    insert into student values ('4288','farha.kauser@sjsu.edu','Farha','Kauser','fall 2020','$2a$10$8GDqoqhSserNrrbQYiVfzepMPa093dIq1gwKDlOTxuOJAt5XUK/5a','student');
	insert into student values ('8888','priya.khadge.yadav@sjsu.edu','priya','khadge','fall 2020','$2a$10$8GDqoqhSserNrrbQYiVfzepMPa093dIq1gwKDlOTxuOJAt5XUK/5a','student');
    
    SELECT 'Insert sample data into Student Registration Table';
    
    insert into student_registration (student_ssn,status,registered_by) values ('4288', true,'8088');
    insert into student_registration (student_ssn,status,registered_by) values ('8888', true,'9088');
    
    SELECT 'Insert sample data into Activity Table';
    
    insert into activity values ("1" , "Yoga");
	insert into activity values ("2" , "Zumba");
	insert into activity values ("3" , "Fitness");
	insert into activity values ("4" , "Basketball");
	insert into activity values ("5" , "Football");
	insert into activity values ("6" , "Volleyball");
	insert into activity values ("7" , "Badminton");
    
    SELECT 'Insert sample data into Team Table';
    
    insert into team (session_id, team_tryOutSession, activity_id, coach_ssn) values (1,"Basketball_tryout_session" , 4, '5057' );
	insert into team (session_id, team_tryOutSession, activity_id, coach_ssn) values (2,"Football_tryout_session", 5,  '7427');
    
    SELECT 'Insert sample data into Team TryOuts Table';

	insert into team_tryouts (student_id, coach_ssn, session_id, status) values (4288,5057, 1, "pending" );
	insert into team_tryouts (student_id, coach_ssn, session_id, status) values (8888,7427, 2, "pending" );
	
    SELECT 'Insert sample data into Session Table';
 
	insert into session (session_id, session_name, capacity, section, room_number , start_time ,end_time,activity_id,instructor_ssn,session_date,session_description ) 
					values (1, "Yoga", 20, 2 , "1", '08:00:00','10:00:00',1,'5088', CURDATE()+1,"Advance Yoga Session" );
	
    insert into session (session_id, session_name, capacity, section, room_number, start_time,end_time ,activity_id , instructor_ssn,session_date,session_description )
					values (2, "Zumba",25, 1, "2", '08:00:00','10:00:00',2,'6088', CURDATE()+1, "Zumba for begginers" );
                    
	SELECT 'Insert sample data into Enrollment Table';
    
	insert into enrollment (student_id, session_id, status,list_order) values (4288,1, "enrolled", 1);
	insert into enrollment (student_id, session_id, status,list_order) values (8888, 2, "enrolled", 1 );
	
    COMMIT;
    
    SET status_out = True;
    
end$$
delimiter ;

