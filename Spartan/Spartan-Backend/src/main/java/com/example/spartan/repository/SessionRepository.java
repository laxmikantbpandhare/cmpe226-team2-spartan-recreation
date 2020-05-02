package com.example.spartan.repository;

import com.example.spartan.entity.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SessionRepository {

	
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public Boolean createSession(@RequestBody Map<String, String> payload) throws Exception {
    	String query="insert into session (session_name, capacity, section, room_number , start_time ,end_time,activity_id,instructor_ssn,session_date,session_description ) " +
				"values(?,?,?,?,?,?,?,?,?,?)";

    	System.out.println("query"+query);

		//String session_id = (String)payload.get(payload.keySet().toArray()[0]);
		String session_name = (String)payload.get(payload.keySet().toArray()[0]);
		int capacity = Integer.parseInt(payload.get(payload.keySet().toArray()[1]));
		String section = (String)payload.get(payload.keySet().toArray()[2]);
		int room_no = Integer.parseInt(payload.get(payload.keySet().toArray()[3]));
		String start_time = (String)payload.get(payload.keySet().toArray()[4]);
		String end_time = (String)payload.get(payload.keySet().toArray()[5]);
		String activity_id = (String)payload.get(payload.keySet().toArray()[6]);
		String instructor_ssn = (String)payload.get(payload.keySet().toArray()[7]);
//		String date = (String)payload.get(payload.keySet().toArray()[10]);
		String description = (String)payload.get(payload.keySet().toArray()[10]);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse((String)payload.get(payload.keySet().toArray()[9]));

		java.sql.Date date1 = convertJavaDateToSqlDate(date);
    	return jdbcTemplate.execute(query , new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) 
					throws SQLException, DataAccessException {

				//ps.setString(1, session_id);
				ps.setString(1, session_name);
				ps.setInt(2 , capacity );
				ps.setString(3, section);
				ps.setInt(4, room_no);
				ps.setString(5 , start_time);
				ps.setString(6, end_time);
				ps.setString(7, activity_id);
				ps.setString(8, instructor_ssn);
				ps.setDate(9, (Date) date1);
				ps.setString(10,description);
                           
                return ps.executeUpdate() > 0;
							
			}
		});
    }


	public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	public List<Session> getSessionByInstructor(String instructor_ssn) {
		
		String query = "select * from session where instructor_ssn ='"+instructor_ssn+"'";
		
		return jdbcTemplate.query(query, new ResultSetExtractor<List<Session>>() {

			@Override
			public List<Session> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<Session> resultList = new ArrayList<Session>();
				while(rs.next()) {
					Session s = new Session();
					s.setSession_id(rs.getString(1));
					s.setSession_name(rs.getString(2));
					s.setCapacity(rs.getInt(3));
					s.setSection(rs.getString(4));
					s.setRoom_number(rs.getInt(5));
					s.setStart_time(rs.getString(6));
					s.setEnd_time(rs.getString(7));
					s.setActivity_id(rs.getString(8));
					s.setInstructor_ssn(instructor_ssn);
					s.setSession_date(rs.getDate(10));
					s.setSession_description(rs.getString(11));
					resultList.add(s);
				}
				
				return resultList;
			}
			
		});
	}

	public List getEnrolledSessionByStudents(String student_ssn) {

		List studentsessionList = new ArrayList();

		String sql = "select s.session_name, s.section , s.room_number, s.start_time, s.end_time, s.session_date, s.session_description, e.list_order, s.session_id 	" +
					 "from enrollment as e inner join session as s on e.session_id = s.session_id " +
				     "where e.student_id = '"+student_ssn+"'";

		return jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
			@Override
			public List extractData(ResultSet rs) throws SQLException, DataAccessException {

				while (rs.next()) {

					List t = new ArrayList();
					t.add(rs.getString(1));
					t.add(rs.getString(2));
					t.add(rs.getInt(3));
					t.add(rs.getString(4));
					t.add(rs.getString(5));
					t.add(rs.getDate(6));
					t.add(rs.getString(7));
					t.add(rs.getInt(8));
					t.add(rs.getString(9));

					studentsessionList.add(t);
				}
				return studentsessionList;
			}
		});
	}


	public List getEnrolledStudentsForSession(String session_id) {

		List studentsessionList = new ArrayList();
		String sql = "select s.email_id, s.fname , s.lname, s.college_year, e.status, e.list_order " +
					 "from enrollment as e inner join student as s on e.student_id = s.ssn " +
					 "where e.session_id = '"+session_id+"'";

		return jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
			@Override
			public List extractData(ResultSet rs) throws SQLException, DataAccessException {

				while (rs.next()) {

					List t = new ArrayList();
					t.add(rs.getString(1));
					t.add(rs.getString(2));
					t.add(rs.getString(3));
					t.add(rs.getString(4));
					t.add(rs.getString(5));
					t.add(rs.getInt(6));

					studentsessionList.add(t);
				}
				return studentsessionList;
			}
		});
	}



	public List<Session> getSessionList(Map<String, String> payload) throws ParseException {

//		try {
				String city1 = (String)payload.get(payload.keySet().toArray()[0]);
				String description = (String)payload.get(payload.keySet().toArray()[3]);
				DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

				String sdate = payload.get(payload.keySet().toArray()[1]);
				String edate = payload.get(payload.keySet().toArray()[2]);
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date start_date = sdf1.parse(sdate);
				java.util.Date end_date = sdf1.parse(edate);

		java.sql.Date start_date1 = convertJavaDateToSqlDate(start_date);
		java.sql.Date end_date1 = convertJavaDateToSqlDate(end_date);

		System.out.println("start_date1="+start_date1);
		System.out.println("end_date1="+end_date1);
		System.out.println("description="+description);
		String query;

		if(!description.equals("")){
			query = "select * from session as s where (s.session_name like '%"+city1+"%' AND s.session_date > '"+start_date1+"' AND s.session_date < '"+end_date1+"') AND s.session_description like '%"+description+"%'";
		}else {
			query = "select * from session as s where s.session_name like '%" + city1 + "%' AND s.session_date > '"+start_date1+"' AND s.session_date < '"+end_date1+"'";
		}

		System.out.println("query="+query);
		return jdbcTemplate.query(query, new ResultSetExtractor<List<Session>>() {

			@Override
			public List<Session> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<Session> resultList = new ArrayList<Session>();

				while(rs.next()) {
					Session s = new Session();
					s.setSession_id(rs.getString(1));
					s.setSession_name(rs.getString(2));
					s.setCapacity(rs.getInt(3));
					s.setSection(rs.getString(4));
					s.setRoom_number(rs.getInt(5));
					s.setStart_time(rs.getString(6));
					s.setEnd_time(rs.getString(7));
					s.setActivity_id(rs.getString(8));
					s.setInstructor_ssn(rs.getString(9));
					s.setSession_date(rs.getDate(10));
					s.setSession_description(rs.getString(11));
					resultList.add(s);
				
				}

				return resultList;
			}
		});
	}
		
				
				

	public Session getSessionByID(String id) {
		
		System.out.println("ID = "+id);
		String sql = "select * from session where session_id = '"+id+"'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Session>() {

			@Override
			public Session extractData(ResultSet rs) throws SQLException, DataAccessException {

				Session s = new Session();
				while (rs.next()) {
					s.setSession_id(rs.getString(1));
					s.setSession_name(rs.getString(2));
					s.setCapacity(rs.getInt(3));
					s.setSection(rs.getString(4));
					s.setRoom_number(rs.getInt(5));
					s.setStart_time(rs.getString(6));
					s.setEnd_time(rs.getString(7));
					s.setActivity_id(rs.getString(8));
					s.setInstructor_ssn(rs.getString(9));
					s.setSession_date(rs.getDate(10));
					
				}
				return s;
			}
		});
	}
}


			
