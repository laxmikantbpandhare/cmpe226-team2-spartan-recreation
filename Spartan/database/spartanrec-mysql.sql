drop database if exists cmpe226_Spartan;
create database cmpe226_Spartan;
use cmpe226_Spartan;

create table user (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) not null
);

create table student (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	college_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
	-- registered_by varchar(10)
);

create table coach (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	joining_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
);

create table front_desk_assistant (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	joining_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
);

create table instructor (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	joining_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
);

create table team (
	team_id varchar(10) primary key, 
	team_name varchar(50) not null,
    activity_id varchar(10),
    coach_ssn varchar(10),
	year int default 2020
);

create table activity (
	activity_id varchar(10) primary key,
	activity_name varchar(50) not null
);

create table session (
	session_id varchar(10) primary key, 
	session_name varchar(50) not null, 
	capacity int, 
	section varchar(50),
	room_number int,
	start_time time not null, 
	end_time time not null,
    activity_id varchar(10),
    instructor_ssn varchar(10),
	session_date Date,
	session_description varchar(10000)
);

create table enrollment (
	student_id	varchar(10),
	session_id varchar(10),
	status char(8) not null check (status = 'enrolled' or status = 'waitlist'),
	list_order int not null,
    foreign key(student_id) references student(ssn) on delete cascade on update cascade,
	foreign key(session_id) references session(session_id) on delete cascade on update cascade
);

create table team_tryouts (
	student_id varchar(10),
	coach_ssn varchar(10),
	team_id varchar(10) unique,
	status char(8) not null check (status = 'approved' or status = 'rejected'),
    foreign key (student_id) references student(ssn) on delete cascade on update cascade,
	foreign key (coach_ssn) references coach(ssn) on delete set null on update cascade,
    foreign key (team_id) references team(team_id) on delete cascade on update cascade
);

-- alter table student add foreign key (registered_by) references front_desk_assistant(ssn) on delete set null on update cascade;
alter table team add foreign key (activity_id) references activity(activity_id) on delete cascade on update cascade;
alter table team add foreign key (coach_ssn) references coach(ssn) on delete set null on update cascade;
alter table session add foreign key (instructor_ssn) references instructor(ssn) on delete set null on update cascade;
alter table session add foreign key (activity_id) references activity(activity_id) on delete set null on update cascade;


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

