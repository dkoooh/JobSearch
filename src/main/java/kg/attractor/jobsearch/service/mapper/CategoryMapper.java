package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Category.builder()
                .name(rs.getString("name"))
                .parentId(rs.getInt("parent_id"))
                .id(rs.getInt("id"))
                .build();
    }
}
