package com.example.spartan.repository;

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
        return  jdbcTemplate.query("select id,firstname,lastname,city , country , phoneno,emailid from user",new UserRowMapper());
    }

    public Boolean saveUser(User user){

        String query="insert into user values(NULL ,?,?,?,?,?,?)";
        return jdbcTemplate.execute(query,new PreparedStatementCallback<Boolean>(){
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {

                ps.setString(1,user.getFirstname());
                ps.setString(2,user.getLastname());
                ps.setString(3,user.getCity());
                ps.setString(4,user.getCountry());
                ps.setString(5,user.getPhoneno());
                ps.setString(6,user.getEmailid());

                return ps.execute();

            }
        });

    }

}
