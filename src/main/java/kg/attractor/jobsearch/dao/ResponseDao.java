package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.service.mapper.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}
