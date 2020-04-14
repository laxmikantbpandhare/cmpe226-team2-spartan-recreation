package com.example.spartan.repository;

import com.example.spartan.entity.Student;
import com.example.spartan.entity.User;
import com.example.spartan.entity.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getUser(){
        return  jdbcTemplate.query("select firstname,lastname,city , country , phoneno,emailid,password from user",new UserRowMapper());
    }

    public Boolean saveUser(Student user){

        String query="insert into Student values(?,?,?,?,?,?,?)";
        return jdbcTemplate.execute(query,new PreparedStatementCallback<Boolean>(){
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {

                ps.setString(1,user.getSsn());
                ps.setString(2,user.getEmail_id());
                ps.setString(3,user.getFname());
                ps.setString(4,user.getLname());
                ps.setString(5,user.getCollege_year());
                ps.setString(6,user.getPassword());
                ps.setString(7,user.getUser_role());
                return ps.execute();

            }
        });

    }

    //Priya
    public String getUserpPassword(String Email_id) {

        String query = "SELECT password FROM Student WHERE email_id = ?";
        Object[] inputs = new Object[] {Email_id};
        String password = jdbcTemplate.queryForObject(query, inputs, String.class);

        return password;
    }
}
