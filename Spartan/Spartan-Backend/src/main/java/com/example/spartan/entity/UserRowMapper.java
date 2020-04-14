package com.example.spartan.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setSsn(rs.getString("ssn"));
        user.setEmail_id(rs.getString("email_id"));
        user.setFname(rs.getString("firstname"));
        user.setLname(rs.getString("lastname"));
        user.setCollege_year(rs.getString("collegeyear"));
        user.setPassword(rs.getString("password"));
        user.setUser_role(rs.getString("user_role"));
        return user;
    }

}