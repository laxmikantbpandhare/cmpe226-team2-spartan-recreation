package com.example.spartan.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setCity(rs.getString("city"));
        user.setCountry(rs.getString("country"));
        user.setPhoneno(rs.getString("phoneno"));
        user.setEmailid(rs.getString("emailid"));

        return user;
    }

}