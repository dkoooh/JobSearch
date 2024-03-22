package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.service.mapper.EducationInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

    public void create(EducationInfo eduInfo) {
        String sql = """
                insert into EDUCATION_INFO (RESUME_ID, INSTITUTION, PROGRAM, START_DATE, END_DATE, DEGREE)
                 VALUES ( :resume_id, :institution, :program, :start_date, :end_date, :degree )
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("resume_id", eduInfo.getResumeId())
                .addValue("institution", eduInfo.getInstitution())
                .addValue("program", eduInfo.getProgram())
                .addValue("start_date", eduInfo.getStartDate())
                .addValue("end_date", eduInfo.getEndDate())
                .addValue("degree", eduInfo.getDegree());
        namedTemplate.update(sql, dataSource);
    }

    public Optional<EducationInfo> getById(int id) {
        String sql = """
                select * from EDUCATION_INFO
                where ID = ?
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new EducationInfoMapper(), id)
                )
        );
    }

    public void update(EducationInfo educationInfo) {
        String sql = """
                update EDUCATION_INFO
                set INSTITUTION = :institution, PROGRAM = :program, START_DATE = :start_date, END_DATE = :end_date, DEGREE = :degree
                where ID = :id;
                """;

        SqlParameterSource dataSource = new MapSqlParameterSource()
                .addValue("id", educationInfo.getId())
                .addValue("institution", educationInfo.getInstitution())
                .addValue("program", educationInfo.getProgram())
                .addValue("start_date", educationInfo.getStartDate())
                .addValue("end_date", educationInfo.getEndDate())
                .addValue("degree", educationInfo.getDegree());

        namedTemplate.update(sql, dataSource);
    }

    public void delete(int id) {
        String sql = """
                delete from EDUCATION_INFO
                where ID = ?
                """;

        template.update(sql, id);
    }
}
