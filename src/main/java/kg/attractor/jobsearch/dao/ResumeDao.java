package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

    public List<Resume> getResumesByCategory(int categoryId) {
        String sql = """
                select * from RESUMES
                where CATEGORY_ID = ?
                """;
        return template.query(sql, new ResumeMapper(), categoryId);
    }

    public List<Resume> getResumesByApplicant(int applicantId) {
        String sql = """
                select * from RESUMES
                where applicant_ID = ?
                """;
        return template.query(sql, new ResumeMapper(), applicantId);
    }

    public Optional<Resume> getResumeById(int resumeId) {
        String sql = """
                select * from resumes
                where id = ?
                """;

        return Optional.ofNullable(DataAccessUtils.singleResult(template.query(sql, new ResumeMapper(), resumeId)));
    }

    public void createResume(Resume resume) {
        String sql = """
                insert into resumes (APPLICANT_ID, NAME, CATEGORY_ID, SALARY, IS_ACTIVE, CREATED_DATE, UPDATE_TIME)
                values ( :applicant_id, :name, :category_id, :salary, :is_active, :created_date, :update_time )
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("applicant_id", resume.getApplicantId())
                .addValue("name", resume.getName())
                .addValue("category_id", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("is_active", resume.getIsActive())
                .addValue("created_date", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));

        namedTemplate.update(sql, dataSource);
    }

    public void deleteResume(int resumeId) {
        String sql = """
                delete from CONTACTS_INFO
                where RESUME_ID = :resumeId;
                delete from EDUCATION_INFO
                where RESUME_ID = :resumeId;
                delete from WORK_EXPERIENCE_INFO
                where RESUME_ID = :resumeId;
                delete from CONTACTS_INFO
                where RESUME_ID = :resumeId;
                delete from MESSAGES
                where RESPONDED_APPLICANT_ID = (select id from RESPONDED_APPLICANTS
                    where RESUME_ID = :resumeId);
                delete from RESPONDED_APPLICANTS
                where RESUME_ID = :resumeId;
                delete from RESUMES
                where id = :resumeId;
                """;

        MapSqlParameterSource dateSource = new MapSqlParameterSource().addValue("resumeId", resumeId);

        namedTemplate.update(sql, dateSource);
    }

    public void updateResume(Resume resume) {
        String sql = """
                update RESUMES
                set name = :name,
                category_id = :category_id,
                salary = :salary,
                IS_ACTIVE = :is_active,
                update_time = :update_time
                where id = :id
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("id", resume.getId())
                .addValue("name", resume.getName())
                .addValue("category_id", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("is_active", resume.getIsActive())
                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));

        namedTemplate.update(sql, dataSource);
    }
}
