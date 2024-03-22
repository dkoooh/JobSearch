package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactTypeDao {
    private final JdbcTemplate template;

    public Boolean isTypeExists (int typeId) {
        String sql = """
                select * from CONTACT_TYPES
                where ID = ?;
                """;

        return !template.query(sql, new BeanPropertyRowMapper<>(ContactType.class), typeId).isEmpty();
    }
}
