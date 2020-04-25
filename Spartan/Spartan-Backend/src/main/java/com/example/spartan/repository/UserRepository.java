package com.example.spartan.repository;

import com.example.spartan.entity.Student;
import com.example.spartan.entity.User;
import com.example.spartan.entity.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getUser(){
        return  jdbcTemplate.query("select email_id,ssn,password,user_role from user",new UserRowMapper());
    }

    public boolean saveUser(Map<String, String> payload){

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_CREATE_USER");

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("sp_email_id", payload.get(payload.keySet().toArray()[0]))
                .addValue("sp_ssn", payload.get(payload.keySet().toArray()[1]))
                .addValue("sp_firstname", payload.get(payload.keySet().toArray()[3]))
                .addValue("sp_lastname", payload.get(payload.keySet().toArray()[2]))
                .addValue("sp_year", payload.get(payload.keySet().toArray()[4]))
                .addValue("sp_password", payload.get(payload.keySet().toArray()[5]))
                .addValue("sp_role", payload.get(payload.keySet().toArray()[6]));

        Boolean CallResult = call.executeFunction(Boolean.class, paramMap);

        System.out.println("Status of saving to stored proc: " + CallResult);

        return CallResult;
    }

    public Student getUserDetails(String email_id) throws ParseException {


        String sql = "select * from Student as s where s.email_id = '"+email_id+"'";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Student>() {

            @Override
            public Student extractData(ResultSet rs) throws SQLException, DataAccessException {

                Student s = new Student();
                while(rs.next()) {
                    s.setSsn(rs.getString(1));
                    s.setUser_role(rs.getString(2));
                    s.setLname(rs.getString(3));
                    s.setFname(rs.getString(4));
                    s.setEmail_id(rs.getString(5));
                    s.setCollege_year(rs.getString(6));
                    s.setPassword(rs.getString(7));
                }
                return s;
            }

        });
    }


    public String getUserPassword(String Email_id) {

        String query = "SELECT password FROM Student WHERE email_id = ?";
        Object[] inputs = new Object[] {Email_id};
        String password = jdbcTemplate.queryForObject(query, inputs, String.class);

        return password;
    }
}
