package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.mapper.WorkExpInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WorkExperienceInfoDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

    public void create(WorkExperienceInfo info) {
        String sql = """
                    insert into WORK_EXPERIENCE_INFO (RESUME_ID, YEARS, COMPANY_NAME, POSITION, RESPONSIBILITIES)
                    values ( :resume_id, :years, :company_name, :position, :responsibilities )
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("resume_id", info.getResumeId())
                .addValue("years", info.getYears())
                .addValue("company_name", info.getCompanyName())
                .addValue("position", info.getPosition())
                .addValue("responsibilities", info.getResponsibilities());

        namedTemplate.update(sql, dataSource);
    }

    public Optional<WorkExperienceInfo> getById(int workExpInfoId) {
        String sql = """
                select * from WORK_EXPERIENCE_INFO
                where ID = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new WorkExpInfoMapper(), workExpInfoId)
                )
        );
    }

    public List<WorkExperienceInfo> getByResumeId(int resumeId) {
        String sql = """
                select * from WORK_EXPERIENCE_INFO
                where RESUME_ID = ?;
                """;

        return template.query(sql, new WorkExpInfoMapper(), resumeId);
    }

    public void update(WorkExperienceInfo info) {
        String sql = """
                update WORK_EXPERIENCE_INFO
                set YEARS = :years, COMPANY_NAME = :company_name, POSITION = :position, RESPONSIBILITIES = :responsibilities
                where ID = :id;
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("id", info.getId())
                .addValue("years", info.getYears())
                .addValue("company_name", info.getCompanyName())
                .addValue("position", info.getPosition())
                .addValue("responsibilities", info.getResponsibilities());

        namedTemplate.update(sql, dataSource);
    }

    public void delete(int workExpInfoId) {
        String sql = """
                delete from WORK_EXPERIENCE_INFO
                where ID = ?;
                """;

        template.update(sql, workExpInfoId);
    }

}
