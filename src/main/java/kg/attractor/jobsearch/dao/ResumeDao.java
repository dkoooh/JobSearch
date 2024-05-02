package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;
//import kg.attractor.jobsearch.service.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

//    public List<Resume> getResumes () {
//        String sql = """
//                select * from RESUMES;
//                """;
//
//        return template.query(sql, new ResumeMapper());
//    }

//    public Optional<Resume> getResumeById(int resumeId) {
//        String sql = """
//                select * from RESUMES
//                where ID = ?
//                """;
//
//        return Optional.ofNullable(DataAccessUtils.singleResult(template.query(sql, new ResumeMapper(), resumeId)));
//    }
//
//    public List<Resume> getResumesByCategory(int categoryId) {
//        String sql = """
//                select * from RESUMES
//                where CATEGORY_ID = ?
//                """;
//        return template.query(sql, new ResumeMapper(), categoryId);
//    }

//    public List<Resume> getResumesByApplicant(int applicantId) {
//        String sql = """
//                select * from RESUMES
//                where APPLICANT_ID = ?
//                """;
//        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), applicantId);
//    }

//    public int create(Resume resume) {
//        String sql = """
//                insert into RESUMES (APPLICANT_ID, NAME, CATEGORY_ID, SALARY, IS_ACTIVE, CREATED_DATE, UPDATE_TIME)
//                values ( :applicant_id, :name, :category_id, :salary, :is_active, :created_date, :update_time )
//                """;
//
//        SqlParameterSource dataSource = new MapSqlParameterSource()
//                .addValue("applicant_id", resume.getApplicantId())
//                .addValue("name", resume.getName())
//                .addValue("category_id", resume.getCategoryId())
//                .addValue("salary", resume.getSalary())
//                .addValue("is_active", resume.getIsActive())
//                .addValue("created_date", Timestamp.valueOf(LocalDateTime.now()))
//                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));
//
//
//        KeyHolder kh = new GeneratedKeyHolder();
//        namedTemplate.update(sql, dataSource, kh);
//        return Objects.requireNonNull(kh.getKey()).intValue();
//
//    }

//    public void updateResume(Resume resume) {
//        String sql = """
//                update RESUMES
//                set NAME = :name,
//                CATEGORY_ID = :category_id,
//                SALARY = :salary,
//                IS_ACTIVE = :is_active,
//                UPDATE_TIME = :update_time
//                where ID = :id and APPLICANT_ID = :applicant_id
//                """;
//
//        SqlParameterSource dataSource = new MapSqlParameterSource()
//                .addValue("id", resume.getId())
//                .addValue("applicant_id", resume.getApplicantId())
//                .addValue("name", resume.getName())
//                .addValue("category_id", resume.getCategoryId())
//                .addValue("salary", resume.getSalary())
//                .addValue("is_active", resume.getIsActive())
//                .addValue("update_time", Timestamp.valueOf(LocalDateTime.now()));
//
//        namedTemplate.update(sql, dataSource);
//    }

//    public void deleteResume(int resumeId) {
//        String sql = """
//                delete from RESUMES
//                where ID = :resumeId;
//                """;
//
//        template.update(sql, resumeId);
//    }
}
