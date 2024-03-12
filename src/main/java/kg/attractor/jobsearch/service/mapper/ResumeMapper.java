package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
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
                .build();
        // добавить created и update time, из-за парса выходит ошибка с null
    }
}
