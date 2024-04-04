package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate template;

    public List<Category> getCategories () {
        String sql = """
                select * from CATEGORIES
                """;

        return template.query(sql, new CategoryMapper());
    }

    public Optional<Category> getCategoryById (int id) {
        String sql = """
                select * from CATEGORIES
                where ID = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(template.query(sql, new CategoryMapper(), id))

        );
    }
}
