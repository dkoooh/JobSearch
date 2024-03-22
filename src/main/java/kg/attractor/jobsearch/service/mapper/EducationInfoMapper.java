package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.EducationInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EducationInfoMapper implements RowMapper<EducationInfo> {
    @Override
    public EducationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return EducationInfo.builder()
                .id(rs.getInt("id"))
                .resumeId(rs.getInt("resume_id"))
                .institution(rs.getString("institution"))
                .program(rs.getString("program"))
                .startDate(rs.getDate("start_date").toLocalDate())
                .endDate(rs.getDate("end_date").toLocalDate())
                .degree(rs.getString("degree"))
                .build();
    }
}
