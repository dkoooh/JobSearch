package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.RespondedApplicant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponseMapper implements RowMapper<RespondedApplicant> {
    @Override
    public RespondedApplicant mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RespondedApplicant.builder()
                .id(rs.getInt("id"))
                .resumeId(rs.getInt("resume_id"))
                .vacancyId(rs.getInt("vacancy_id"))
                .isConfirmed(rs.getBoolean("confirmation"))
                .build();
    }
}
