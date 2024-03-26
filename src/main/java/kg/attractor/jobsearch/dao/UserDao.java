package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.service.mapper.UserMapper;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

    public Optional<User> getUserByEmail(String email) {
        String sql = """
                select * from USERS
                where EMAIL = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(template.query(sql, new UserMapper(), email)));
    }

    public List<User> getUsersByName(String name) {
        String sql = """
                select * from USERS
                where NAME = ?;
                """;

        return template.query(sql, new UserMapper(), name);
    }

    public List<User> getUsersByPhoneNumber(String phoneNumber) {
        String sql = """
                select * from USERS
                where PHONE_NUMBER = ?;
                """;

        return template.query(sql, new UserMapper(), phoneNumber);
    }

    public List<User> getApplicantsByVacancy(int vacancyId) {
        String sql = """
                select * from USERS where ID in (
                    select APPLICANT_ID from RESUMES
                    where ID in (
                        select RESUME_ID from RESPONDED_APPLICANTS
                        where VACANCY_ID = ?
                    )
                )
                """;

        return template.query(sql, new UserMapper(), vacancyId);
    }

    public Boolean isUserExists (String email) {
        String sql = """
                
                    select * from USERS
                    where EMAIL = ?
                
                """;

        return !template.query(sql, new UserMapper(), email).isEmpty();
    }

    public void createUser (User user) {
        String sql = """
                insert into USERS (NAME, SURNAME, AGE, EMAIL, PASSWORD, PHONE_NUMBER, AVATAR, ACCOUNT_TYPE, ENABLED)
                values ( :name, :surname, :age, :email, :password, :phone_number, :avatar, :account_type, true);
                """;

        MapSqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("age", user.getAge())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("phone_number", user.getPhoneNumber())
                .addValue("avatar", user.getAvatar())
                .addValue("account_type", user.getAccountType());

        namedTemplate.update(sql, dataSource);
    }

    public void updateUser(User user) {
        String sql = """
                update USERS
                set NAME = :name, SURNAME = :surname, AGE = :age, PASSWORD = :password,
                 PHONE_NUMBER = :phoneNumber, AVATAR = :avatar
                 where id = :id
                """;

        MapSqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("age", user.getAge())
                .addValue("password", user.getPassword())
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("avatar", user.getAvatar());

        namedTemplate.update(sql, dataSource);
    }

    public void uploadUserAvatar(String userEmail, String fileName) {
        String sql = """
            update USERS
            set AVATAR = ?
            where EMAIL = ?;
            """;

        template.update(sql, fileName, userEmail);
    }
}
