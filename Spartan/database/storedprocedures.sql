drop PROCEDURE if exists sp_create_user;
drop PROCEDURE if exists sp_login_user;
drop PROCEDURE if exists sp_enroll_student;
drop PROCEDURE if exists sp_remove_enrolled_student;
drop PROCEDURE if exists sp_remove_session;
drop PROCEDURE if exists sp_approve_student;
drop trigger if exists InsertStudentTrigger;
drop trigger if exists InsertInstructorTrigger;
drop trigger if exists InsertCoachTrigger;
drop trigger if exists InsertFDATrigger;
drop trigger if exists removeEnrolledStudents;


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

UPDATE team_tryouts SET `status` = sp_decision WHERE student_ssn = sp_studentssn and session_id = sp_session_id;

set status_out = TRUE;

END$$
delimiter ;
