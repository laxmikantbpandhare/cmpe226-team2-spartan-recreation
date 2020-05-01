package com.example.spartan.repository;

import com.example.spartan.entity.Coach;
import com.example.spartan.entity.Team;
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

        String query = "select ssn, fname, lname, college_year, team_tryOutSession from gettingRequests;";

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

    public List getRegisteredStudentsForTryOut(String session_id) {

        List studentsessionList = new ArrayList();

        String sql = "select s.email_id, s.fname , s.lname, s.college_year, tt.status " +
                "from team_tryouts as tt inner join student as s on tt.student_id = s.ssn " +
                "where tt.session_id = '"+session_id+"'";

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

                    studentsessionList.add(t);
                }
                return studentsessionList;
            }
        });
    }


    public List<Team> getSessionByInstructor(String instructor_ssn) {

        String query = "select * from team where coach_ssn ='"+instructor_ssn+"'";

        return jdbcTemplate.query(query, new ResultSetExtractor<List<Team>>() {

            @Override
            public List<Team> extractData(ResultSet rs) throws SQLException, DataAccessException {

                List<Team> resultList = new ArrayList<>();
                while(rs.next()) {
                    Team s = new Team();
                    s.setSessionId(rs.getString(1));
                    s.setTeam_tryOutSession(rs.getString(2));
                    s.setActivity_id(rs.getString(3));
                    s.setCoach_ssn(rs.getString(4));
                    s.setYear(rs.getInt(5));
                    resultList.add(s);
                }

                return resultList;
            }

        });
    }

}
