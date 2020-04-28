package com.example.spartan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean getStudentStatus(String student_id) {

        boolean result = false;

        System.out.println("ID = "+student_id);

        String sql = "select * from session where session_id = '"+student_id+"'";

        int status =   jdbcTemplate.queryForObject(sql, Integer.class);

        if (status > 0) {
            result = true;
        }

        return result;

    }


    public List getTeamDetails() {

        List teamList = new ArrayList();

        //String sql = "select * from Team";
        String sql = "select t.team_name, c.fname ,t.year from team t inner join coach c on t.coach_ssn = c.ssn";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {

                    List t = new ArrayList();
                    //Team t = new Team();
                    System.out.println(rs.getType());
                    System.out.println("here i am");
                    // Team t1 = (Team)rs;
                    System.out.println(rs.getString(1));
                    System.out.println(rs.getString(2));
                    System.out.println(rs.getString(3));
                    t.add(rs.getString(1));
                    t.add(rs.getString(2));
                    t.add(rs.getInt(3));

                    teamList.add(t);
                }
                System.out.println(teamList.size());

                return teamList;
            }
        });

    }



}
