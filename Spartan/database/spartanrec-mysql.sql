drop database if exists spartanrec;
create database spartanrec;
use spartanrec;

create table student (
	sjsu_id	int primary key, 
	fname	varchar(20) not null, 		
	lname	varchar(20) not null,
	college_year varchar(10) not null,
	password varchar(80) not null,
    registered_by varchar(10)
);

create table coach (
	ssn varchar(10) primary key,
	fname	varchar(20) not null, 		
	lname	varchar(20) not null,
	password varchar(80) not null,
	joining_date date not null
);

create table front_desk_assistant (
	ssn varchar(10) primary key,
	fname	varchar(20) not null, 		
	lname	varchar(20) not null,
	password varchar(80) not null,
	joining_date date not null
);

create table instructor (
	ssn varchar(10) primary key,
	fname	varchar(20) not null, 		
	lname	varchar(20) not null,
	password varchar(80) not null,
	joining_date date not null
);

create table team (
	team_id varchar(10) primary key, 
	team_name varchar(20) not null,
    activity_id varchar(10),
    coach_ssn varchar(10),
	year int default 2020
);

create table activity (
	activity_id varchar(10) primary key,
	activity_name varchar(10) not null
);

create table session (
	session_id varchar(10) primary key, 
	session_name varchar(10) not null, 
	capacity int, 
	section varchar(10),
	room_number int,
	start_time time not null, 
	end_time time not null,
    activity_id varchar(10),
    instructor_ssn varchar(10)
);

create table enrollment (
	student_id	int,
	session_id varchar(10),
	status char(8) not null check (status = 'enrolled' or status = 'waitlist'),
	list_order int not null,
    foreign key(student_id) references student(sjsu_id) on delete cascade on update cascade,
	foreign key(session_id) references session(session_id) on delete cascade on update cascade
);

create table team_tryouts (
	student_id int,
	coach_ssn varchar(10),
	team_id varchar(10) unique,
	status char(8) not null check (status = 'approved' or status = 'rejected'),
    foreign key (student_id) references student(sjsu_id) on delete cascade on update cascade,
	foreign key (coach_ssn) references coach(ssn) on delete set null on update cascade,
    foreign key (team_id) references team(team_id) on delete cascade on update cascade
);

alter table student add foreign key (registered_by) references front_desk_assistant(ssn) on delete set null on update cascade;
alter table team add foreign key (activity_id) references activity(activity_id) on delete cascade on update cascade;
alter table team add foreign key (coach_ssn) references coach(ssn) on delete set null on update cascade;
alter table session add foreign key (instructor_ssn) references instructor(ssn) on delete set null on update cascade;
alter table session add foreign key (activity_id) references activity(activity_id) on delete set null on update cascade;

