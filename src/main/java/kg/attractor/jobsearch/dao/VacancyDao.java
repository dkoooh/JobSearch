package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.mapper.VacancyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate template;

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
}
