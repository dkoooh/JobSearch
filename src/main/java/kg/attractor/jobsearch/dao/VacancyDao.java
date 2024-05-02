package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

//    public List<Vacancy> getVacancies() {
//        String sql = """
//                select * from VACANCIES;
//                """;
//        return template.query(sql, new VacancyMapper());
//    }
//
//    public List<Vacancy> getActiveVacancies () {
//        String sql = """
//                select * from VACANCIES
//                where IS_ACTIVE = true;
//                """;
//
//        return template.query(sql, new VacancyMapper());
//    }
//
//    public Optional<Vacancy> getVacancyById(int vacancyId) {
//        String sql = """
//                select * from VACANCIES
//                where ID = ?
//                """;
//
//        return Optional.ofNullable(
//                DataAccessUtils.singleResult(
//                        template.query(sql, new VacancyMapper(), vacancyId)
//                )
//        );
//    }

//    public List<Vacancy> getVacanciesByApplicant(int userId) {
//        String sql = """
//                select * from VACANCIES
//                where ID in (
//                    select VACANCY_ID from RESPONDED_APPLICANTS
//                    where RESUME_ID in (
//                        select ID from RESUMES
//                        where APPLICANT_ID = ?
//                    )
//                );
//                """;
//        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
//    }

//    public List<Vacancy> getVacanciesByEmployer(String email) {
//        String sql = """
//                select * from VACANCIES
//                where AUTHOR_ID in (
//                    select ID from USERS
//                    where EMAIL = ?
//                );
//                """;
//
//        return template.query(sql, new VacancyMapper(), email);
//    }
//
//    public List<Vacancy> getVacanciesByCategory(int categoryId) {
//        String sql = """
//                select * from vacancies
//                where CATEGORY_ID = ?
//                """;
//        return template.query(sql, new VacancyMapper(), categoryId);
//    }

    /*public Number createVacancy (Vacancy vacancy) {
        String sql = """
                insert into VACANCIES (NAME, DESCRIPTION, CATEGORY_ID, SALARY, EXP_FROM, EXP_TO, IS_ACTIVE, AUTHOR_ID,
                    CREATED_DATE, UPDATE_TIME)
                values ( :name, :description, :category_id, :salary, :exp_from, :exp_to, :is_active, :author_id,
                    :created_date, :update_time );
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("name", vacancy.getName())
                .addValue("description", vacancy.getDescription())
                .addValue("category_id", vacancy.getCategory())
                .addValue("salary", vacancy.getSalary())
                .addValue("exp_from", vacancy.getExpFrom())
                .addValue("exp_to", vacancy.getExpTo())
                .addValue("is_active", vacancy.getIsActive())
                .addValue("author_id", vacancy.getAuthor())
                .addValue("created_date", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));
        namedTemplate.update(sql, dataSource);

        return new GeneratedKeyHolder().getKey();
    }

    public void updateVacancy (Vacancy vacancy) {
        String sql = """
                update VACANCIES
                set NAME = :name, DESCRIPTION = :description, CATEGORY_ID = :category_id, SALARY = :salary,
                    EXP_FROM = :exp_from, EXP_TO = :exp_to, IS_ACTIVE = :is_active, UPDATE_TIME = :update_time
                where ID = :id
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("id", vacancy.getId())
                .addValue("name", vacancy.getName())
                .addValue("description", vacancy.getDescription())
                .addValue("category_id", vacancy.getCategory())
                .addValue("salary", vacancy.getSalary())
                .addValue("exp_from", vacancy.getExpFrom())
                .addValue("exp_to", vacancy.getExpTo())
                .addValue("is_active", vacancy.getIsActive())
                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));
        namedTemplate.update(sql, dataSource);
    }

    public void deleteVacancy (int vacancyId) {
        String sql = """
        delete from VACANCIES
        where ID = :vacancyId
        """;

        MapSqlParameterSource dateSource = new MapSqlParameterSource().addValue("vacancyId", vacancyId);
        namedTemplate.update(sql, dateSource);
    }*/
}
