package kg.attractor.jobsearch.service.mapper;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyMapper implements RowMapper<Vacancy> {
    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Vacancy.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .categoryId(rs.getInt("category_id"))
                .salary(rs.getDouble("salary"))
                .expFrom(rs.getInt("exp_from"))
                .expTo(rs.getInt("exp_to"))
                .isActive(rs.getBoolean("is_active"))
                .authorId(rs.getInt("author_id"))
                .build();
        // добавить created и update time, из-за парса выходит ошибка с null
    }
}
