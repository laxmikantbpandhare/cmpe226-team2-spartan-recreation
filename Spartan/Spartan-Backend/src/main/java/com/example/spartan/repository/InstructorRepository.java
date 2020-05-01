package com.example.spartan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.example.spartan.entity.Instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;


@Repository
public class InstructorRepository {
	
	
    @Autowired
    JdbcTemplate jdbcTemplate;

	public String getInstructorPassword(String Email_id) {
		String query = "SELECT password FROM Instructor WHERE email_id = ?";
        Object[] inputs = new Object[] {Email_id};
        String password = jdbcTemplate.queryForObject(query, inputs, String.class);

        return password;
    }
    

    public Instructor getUserDetails(String email_id) throws ParseException {


        String sql = "select * from Instructor as s where s.email_id = '"+email_id+"'";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Instructor>() {

            @Override
            public Instructor extractData(ResultSet rs) throws SQLException, DataAccessException {

                Instructor instructor = new Instructor();
                while(rs.next()) {
                    instructor.setSsn(rs.getString(1));
                    instructor.setUser_role(rs.getString(2));
                    instructor.setLname(rs.getString(3));
                    instructor.setFname(rs.getString(4));
                    instructor.setEmail_id(rs.getString(5));
                    instructor.setPassword(rs.getString(7));
                }
                return instructor;
            }

        });
    }


	public String getInstructorName(String ssn) {
		String query = "SELECT fname , lname FROM Instructor WHERE ssn = "+ssn;
        return jdbcTemplate.query(query, new ResultSetExtractor<String>() {

            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {

               StringBuilder name = new StringBuilder();
                while(rs.next()) {
                    name.append(rs.getString(1)+" ");
                    name.append(rs.getString(2));
                }
                return name.toString();
            }

        });
	}

     
}
    