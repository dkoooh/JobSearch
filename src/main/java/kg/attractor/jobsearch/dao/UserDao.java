package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.service.mapper.UserMapper;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;

    public List<User> getUsersByName (String name) {
        String sql = """
                select * from users
                where name = ?;
                """;

        return template.query(sql, new UserMapper(), name);
    }

    public Optional<User> getUserByEmail (String email) {
        String sql = """
                select * from users
                where email = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(template.query(sql, new UserMapper(), email)));
    }

    public List<User> getUsersByPhoneNumber (String phoneNumber) {
        String sql = """
                select * from users
                where phone_number = ?;
                """;

        return template.query(sql, new UserMapper(), phoneNumber);
    }

    public List<User> getApplicantsByVacancy (int vacancyId) {
        String sql = """
                select * from users where id in (
                    select APPLICANT_ID from RESUMES
                    where id in (
                        select resume_id from RESPONDED_APPLICANTS
                        where vacancy_id = ?
                    )
                )
                """;

        return template.query(sql, new UserMapper(), vacancyId);
    }
}
