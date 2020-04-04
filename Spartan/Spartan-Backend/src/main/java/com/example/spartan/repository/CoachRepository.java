package com.example.spartan.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.example.spartan.entity.Coach;

@Repository
public class CoachRepository {
	
	
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public Boolean saveCoach(Coach c) throws Exception {
    	String query="insert into coach values(?,?,?,?,?)";
    	
    	return jdbcTemplate.execute(query , new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) 
					throws SQLException, DataAccessException {
				
				ps.setString(1,c.getSsn());
                ps.setString(2,c.getFname());
                ps.setString(3,c.getLname());
                ps.setString(4,c.getPassword());
                ps.setString(5,c.getJoining_date().toString());
                
                
                return ps.executeUpdate() > 0;
							
			}
		});
    }

}
