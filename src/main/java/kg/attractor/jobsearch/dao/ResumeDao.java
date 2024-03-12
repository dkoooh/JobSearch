package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;

    public List<Resume> getResumesByCategory (int categoryId) {
        String sql = """
                select * from RESUMES
                where CATEGORY_ID = ?
                """;
        return template.query(sql, new ResumeMapper(), categoryId);
    }

    public List<Resume> getResumesByApplicant (int applicantId) {
        String sql = """
                select * from RESUMES
                where applicant_ID = ?
                """;
        return template.query(sql, new ResumeMapper(), applicantId);
    }
}
