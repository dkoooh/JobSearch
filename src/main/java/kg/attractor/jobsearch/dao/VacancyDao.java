package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.mapper.ResumeMapper;
import kg.attractor.jobsearch.service.mapper.VacancyMapper;
import liquibase.sql.Sql;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

    public List<Vacancy> getVacanciesByUser (int userId) {
        String sql = """
                select * from vacancies
                where id in (
                    select vacancy_id from RESPONDED_APPLICANTS
                    where RESUME_ID in (
                        select id from RESUMES
                        where APPLICANT_ID = ?
                    )
                );
                """;
        return template.query(sql, new VacancyMapper(), userId);
    }

    public List<Vacancy> getVacancies() {
        String sql = """
                select * from vacancies;
                """;
        return template.query(sql, new VacancyMapper());
    }

    public List<Vacancy> getVacanciesByCategory(int categoryId) {
        String sql = """
                select * from vacancies
                where category_id = ?
                """;
        return template.query(sql, new VacancyMapper(), categoryId);
    }

    public void createVacancy (Vacancy vacancy) {
        String sql = """
                insert into VACANCIES (NAME, DESCRIPTION, CATEGORY_ID, SALARY, EXP_FROM, EXP_TO, IS_ACTIVE, AUTHOR_ID,
                    CREATED_DATE, UPDATE_TIME)
                values ( :name, :description, :category_id, :salary, :exp_from, :exp_to, :is_active, :author_id,
                    :created_date, :update_time );
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("name", vacancy.getName())
                .addValue("description", vacancy.getDescription())
                .addValue("category_id", vacancy.getCategoryId())
                .addValue("salary", vacancy.getSalary())
                .addValue("exp_from", vacancy.getExpFrom())
                .addValue("exp_to", vacancy.getExpTo())
                .addValue("is_active", vacancy.getIsActive())
                .addValue("author_id", vacancy.getAuthorId())
                .addValue("created_date", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));
        namedTemplate.update(sql, dataSource);
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
                .addValue("category_id", vacancy.getCategoryId())
                .addValue("salary", vacancy.getSalary())
                .addValue("exp_from", vacancy.getExpFrom())
                .addValue("exp_to", vacancy.getExpTo())
                .addValue("is_active", vacancy.getIsActive())
                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));
        namedTemplate.update(sql, dataSource);
    }

    public void deleteVacancy (int vacancyId) {
        String sql = """
        delete from MESSAGES
        where RESPONDED_APPLICANT_ID = (select id from RESPONDED_APPLICANTS where VACANCY_ID = :vacancyId);
        delete from RESPONDED_APPLICANTS
        where VACANCY_ID = :vacancyId;
        delete from VACANCIES
        where ID = :vacancyId
        """;

        MapSqlParameterSource dateSource = new MapSqlParameterSource().addValue("vacancyId", vacancyId);
        namedTemplate.update(sql, dateSource);
    }

    public Optional<Vacancy> getVacancyById(int vacancyId) {
        String sql = """
                select * from VACANCIES
                where ID = ?
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new VacancyMapper(), vacancyId)
                )
        );
    }
}
