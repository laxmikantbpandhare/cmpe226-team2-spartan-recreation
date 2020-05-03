package com.example.spartan.repository;

import com.example.spartan.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TeamRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean getStudentStatus(String student_id,String team_id) {

        boolean result = false;

        String sql = "select IFNULL(max(student_id),0) from team_tryouts where student_id = '"+student_id+"' and session_id = '"+team_id+"'"  ;
        System.out.println("sql");
        System.out.println(sql);

        int status =  jdbcTemplate.queryForObject(sql, Integer.class);

        if (status > 0) {
            result = true;
        }

        return result;

    }

    public Team getTeamByName(String team_tryOutSession){

        String sql = "select t.session_id, t.coach_ssn from team t where t.team_tryOutSession = '"+team_tryOutSession+"'";

        return jdbcTemplate.query(sql, new ResultSetExtractor<Team>() {

            @Override
            public Team extractData(ResultSet rs) throws SQLException, DataAccessException {

                Team t = new Team();
                while (rs.next()) {
                    t.setSessionId(rs.getString(1));
                    t.setCoach_ssn(rs.getString(2));
                }
                return t;
            }
        });
    }

    public List getTeamDetails() {

        List teamList = new ArrayList();

        //String sql = "select * from Team";
        String sql = "select team_tryOutSession,coach_name,year from tryOutSessionDetails;";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {

                    List t = new ArrayList();
                    //Team t = new Team();
                    //System.out.println(rs.getType());
                    //System.out.println("here i am");
                    // Team t1 = (Team)rs;
                    //System.out.println(rs.getString(1));
                    //System.out.println(rs.getString(2));
                    //System.out.println(rs.getString(3));
                    t.add(rs.getString(1));
                    t.add(rs.getString(2));
                    t.add(rs.getInt(3));

                    teamList.add(t);
                }
                //System.out.println(teamList.size());

                return teamList;
            }
        });

    }

    public List getActivity() {

        List teamList = new ArrayList();

        String sql = "select activity_name,activity_id from activity";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {

                    List t = new ArrayList();
                    t.add(rs.getString(1));
                    t.add(rs.getString(2));

                    teamList.add(t);
                }

                return teamList;
            }
        });

    }

    public void teamTryOutRegister(@RequestBody Map<String, String> payload) {

        //insert into team_tryouts table {student_id , coach_ssn, team_id, status (pending)}

        System.out.println("Inserting to student registrations!");

        System.out.println(payload.get(payload.keySet().toArray()[0]));
        System.out.println(payload.get(payload.keySet().toArray()[1]));
        System.out.println(payload.get(payload.keySet().toArray()[2]));
        System.out.println(payload.get(payload.keySet().toArray()[3]));
        System.out.println(payload.get(payload.keySet().toArray()[4]));

        jdbcTemplate.update("insert into team_tryouts(student_id,coach_ssn,session_id,status) values(?,?,?,?)",
                payload.get(payload.keySet().toArray()[0]), payload.get(payload.keySet().toArray()[4]), payload.get(payload.keySet().toArray()[3]),
                "Pending");

    }



}
