package kg.attractor.jobsearch.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponseDao {
    private final JdbcTemplate template;


    public List<Map<String, Object>> fetchAllGroups (Integer userId) {
        String sql = """
                select * from RESPONDED_APPLICANTS
                                where vacancy_id in (select id from VACANCIES where AUTHOR_ID = ?)
                                or RESUME_ID in (select id from RESUMES where APPLICANT_ID = ?);
                """;

        return template.queryForList(sql, userId, userId);
    }
}
