package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.service.mapper.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResponseDao {
    private final JdbcTemplate template;

    public Optional<RespondedApplicant> getResponseByVacancy(int vacancyId, int applicantId) {

        String sql = """
        select * from RESPONDED_APPLICANTS
        where VACANCY_ID = ?
        and RESUME_ID in (select id from RESUMES where APPLICANT_ID = ?)
        """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(template.query(sql, new ResponseMapper(), vacancyId, applicantId))
        );
    }

    public Optional<RespondedApplicant> getById(Integer id) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query("select * from responded_applicants where id = ?", new BeanPropertyRowMapper<>(RespondedApplicant.class), id)
                )
        );
    }

    public List<Map<String, Object>> fetchAllGroups (Integer userId) {
        String sql = """
                select * from RESPONDED_APPLICANTS
                                where vacancy_id in (select id from VACANCIES where AUTHOR_ID = ?)
                                or RESUME_ID in (select id from RESUMES where APPLICANT_ID = ?);
                """;

        return template.queryForList(sql, userId, userId);
    }
}
