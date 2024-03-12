package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .age(rs.getInt("age"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .avatar(rs.getString("avatar"))
                .accountType(rs.getString("account_type"))
                .password(rs.getString("password"))
                .build();
    }
}