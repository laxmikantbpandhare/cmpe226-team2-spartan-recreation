package com.example.spartan.repository;

import com.example.spartan.entity.Coach;
import com.example.spartan.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public boolean assessRequest(String studentssn, String sessionId, String decision ) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_APPROVE_TRYOUTREQUEST");

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("sp_studentssn" , studentssn)
                .addValue("sp_session_id" , sessionId)
                .addValue("sp_decision" , decision)
                ;

        Boolean CallResult = call.executeFunction(Boolean.class, paramMap);
        return CallResult;
    }


    public List getAllPendingRequests() {

        String query = "select s.ssn, s.fname, s.lname, s.college_year, t.team_tryOutSession "+
                "from student s ,team_tryouts tt, team t "+
                "where s.ssn = tt.student_id and tt.status = 'pending'"+
                "and  tt.session_id=t.session_id";

        return jdbcTemplate.query(query, new ResultSetExtractor<List>() {

            @Override
            public List extractData(ResultSet rs) throws SQLException, DataAccessException {

                List resultList = new ArrayList<Student>();

                while(rs.next()) {
                    List s = new ArrayList();
                    s.add(rs.getString(1));
                    s.add(rs.getString(2));
                    s.add(rs.getString(3));
                    s.add(rs.getString(4));
                    resultList.add(s);
                }

                return resultList;
            }

        });
    }


    public Boolean createTryOutSession(@RequestBody Map<String, String> payload) throws Exception {

        String query="insert into team (session_id,team_tryOutSession,activity_id,coach_ssn) values(?,?,?,?)";

        String session_id = (String)payload.get(payload.keySet().toArray()[0]);
        String team_tryOutSession = (String)payload.get(payload.keySet().toArray()[1]);
        String activity_id = (String)payload.get(payload.keySet().toArray()[2]);
        String coach_ssn = (String)payload.get(payload.keySet().toArray()[3]);

        return jdbcTemplate.execute(query , new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {

                ps.setString(1, session_id);
                ps.setString(2, team_tryOutSession);
                ps.setString(3, activity_id);
                ps.setString(4, coach_ssn);

                return ps.executeUpdate() > 0;

            }
        });
    }

}
