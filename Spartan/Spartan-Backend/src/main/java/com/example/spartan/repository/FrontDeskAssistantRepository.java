package com.example.spartan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.spartan.entity.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


@Repository
public class FrontDeskAssistantRepository {
	
	
    @Autowired
    JdbcTemplate jdbcTemplate;

	public boolean approveStudent(String studentssn , String fdassn) {
        
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_APPROVE_STUDENT");
        
                SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("sp_studentssn" , studentssn)
                .addValue("sp_fdassn", fdassn); 

        Boolean CallResult = call.executeFunction(Boolean.class, paramMap);
        return CallResult;
	}


	public List<Student> getAllPendingStudents() {

        String query = "select fname , lname , ssn, college_year "+
        "from student join student_registration "+ 
        "on student.ssn = student_registration.student_ssn "+
        "and student_registration.status = 0";
		
		return jdbcTemplate.query(query, new ResultSetExtractor<List<Student>>() {

			@Override
			public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<Student> resultList = new ArrayList<Student>();
				while(rs.next()) {
                    Student s = new Student();
                    s.setFname(rs.getString(1));
                    s.setLname(rs.getString(2));
                    s.setSsn(rs.getString(3));
                    s.setCollege_year(rs.getString(4));
					resultList.add(s);
				}
				
				return resultList;
			}
			
		});
	}

    

	
     
}
    