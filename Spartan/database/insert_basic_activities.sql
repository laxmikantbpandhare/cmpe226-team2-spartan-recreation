
use cmpe226_spartan;

insert into activity values ("1" , "Yoga");
insert into activity values ("2" , "Zumba");
insert into activity values ("3" , "Fitness");
insert into activity values ("4" , "Basketball");
insert into activity values ("5" , "Football");
insert into activity values ("6" , "Volleyball");
insert into activity values ("7" , "Badminton");

commit;
select * from coach;

insert into team (team_id, team_name, activity_id, coach_ssn) values (1,"Basketball" , 4, 5057 );
insert into team (team_id, team_name, activity_id, coach_ssn) values (2,"Football", 5,  7427);
insert into team (team_id, team_name, activity_id, coach_ssn) values (3, "Volleyball", 6, 4288);
insert into team (team_id, team_name, activity_id, coach_ssn) values (4,"Badminton", 7, 6067);

commit;