package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResumeMapper implements RowMapper<Resume> {
    public Resume mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Resume.builder()
                .id(rs.getInt("id"))
                .applicantId(rs.getInt("applicant_id"))
                .name(rs.getString("name"))
                .categoryId(rs.getInt("category_id"))
                .salary(rs.getDouble("salary"))
                .isActive(rs.getBoolean("is_active"))
                .createdDate(rs.getTimestamp("created_date").toLocalDateTime())
                .updateTime(rs.getTimestamp("update_time").toLocalDateTime())
                .build();
    }
}
