package com.example.spartan.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.example.spartan.entity.Coach;
import com.example.spartan.entity.Session;

@Repository
public class SessionRepository {

	
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public Boolean createSession(Session s) throws Exception {
    	String query="insert into session values(?,?,?,?,?,?,?,?,?,?)";
    	
    	return jdbcTemplate.execute(query , new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) 
					throws SQLException, DataAccessException {
				
				ps.setString(1, s.getSession_id());
				ps.setString(2, s.getSession_name());
				ps.setInt(3 , s.getCapacity() );
				ps.setString(4, s.getSection());
				ps.setInt(5, s.getRoom_number());
				ps.setString(6 , s.getStart_time());
				ps.setString(7, s.getEnd_time());
				ps.setString(8, s.getActivity_id());
				ps.setString(9, s.getInstructor_ssn());
				ps.setString(10,s.getSession_date().toString());
                           
                return ps.executeUpdate() > 0;
							
			}
		});
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
					resultList.add(s);
				}
				
				return resultList;
			}
			
		});
		
					
		
	}

}
