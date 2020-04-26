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
		insert into enrollment(student_ssn,session_id,status,list_order) values (sp_studentssn , sp_sessionid , 'enrolled' , enrolledCount);
        set status_out = concat('Enrolled-',enrolledCount);
	else 
		set waitlisted = enrolledCount - sp_capacity;
        set waitlisted = waitlisted + 1;
		insert into enrollment(student_ssn,session_id,status,list_order) values (sp_studentssn , sp_sessionid , 'waitlist',waitlisted);
		set status_out = concat('Waitlisted-',waitlisted);
	end if;
end