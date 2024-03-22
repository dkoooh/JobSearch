package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkExpInfoMapper implements RowMapper<WorkExperienceInfo> {
    @Override
    public WorkExperienceInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return WorkExperienceInfo.builder()
                .id(rs.getInt("id"))
                .resumeId(rs.getInt("resume_id"))
                .years(rs.getInt("years"))
                .companyName(rs.getString("company_name"))
                .position(rs.getString("position"))
                .responsibilities(rs.getString("responsibilities"))
                .build();
    }
}
