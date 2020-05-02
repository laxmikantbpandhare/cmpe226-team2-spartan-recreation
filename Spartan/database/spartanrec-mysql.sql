drop database if exists cmpe226_Spartan;
create database cmpe226_Spartan;
use cmpe226_Spartan;

create table student (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) unique not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	college_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
	-- registered_by varchar(10)
);

create table coach (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) unique not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	joining_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
);

create table front_desk_assistant (
	ssn	varchar(10) primary key, 
	email_id	varchar(50)  unique not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	joining_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
);

create table instructor (
	ssn	varchar(10) primary key, 
	email_id	varchar(50) unique not null, 
	fname	varchar(50) not null, 		
	lname	varchar(50) not null,
	joining_year varchar(15) not null,
	password varchar(80) not null,
    user_role varchar(25)
);

create table user (
	ssn	varchar(10) primary key, 
	email_id varchar(50) not null
);

create table team (
	session_id  varchar(10) primary key, 
	team_tryOutSession varchar(50) unique not null,
    activity_id varchar(10),
    coach_ssn varchar(10),
	year int default 2020
);

-- ALTER TABLE team MODIFY COLUMN session_id INT auto_increment;

create table team_tryouts (
	student_id varchar(10),
	coach_ssn varchar(10),
	session_id varchar(10),
	status char(8) not null check (status = 'approved' or status = 'rejected' or status = 'pending'),
    PRIMARY KEY (student_id,session_id),
    foreign key (student_id) references student(ssn) on delete cascade on update cascade,
	foreign key (coach_ssn) references coach(ssn) on delete set null on update cascade,
    foreign key (session_id) references team(session_id) on delete cascade on update cascade
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
    primary key (student_id,session_id),
    foreign key(student_id) references student(ssn) on delete cascade on update cascade,
	foreign key(session_id) references session(session_id) on delete cascade on update cascade
);

CREATE TABLE student_registration (
  student_ssn varchar(10),
  status boolean DEFAULT NULL,
  registered_by varchar(10) DEFAULT NULL,
  PRIMARY KEY (student_ssn),
  FOREIGN KEY (registered_by) references front_desk_assistant(ssn)
  );
  
-- alter table student add foreign key (registered_by) references front_desk_assistant(ssn) on delete set null on update cascade;
alter table team add foreign key (activity_id) references activity(activity_id) on delete cascade on update cascade;
alter table team add foreign key (coach_ssn) references coach(ssn) on delete set null on update cascade;
alter table session add foreign key (instructor_ssn) references instructor(ssn) on delete set null on update cascade;
alter table session add foreign key (activity_id) references activity(activity_id) on delete set null on update cascade;


